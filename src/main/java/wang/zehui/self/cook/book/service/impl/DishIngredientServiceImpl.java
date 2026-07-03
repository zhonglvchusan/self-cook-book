package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.enums.IngredientTypeEnum;
import wang.zehui.self.cook.book.common.utils.ConvertUtil;
import wang.zehui.self.cook.book.dao.DishIngredientDao;
import wang.zehui.self.cook.book.domain.entity.DishIngredient;
import wang.zehui.self.cook.book.domain.entity.Ingredient;
import wang.zehui.self.cook.book.domain.request.DishIngredientRequest;
import wang.zehui.self.cook.book.domain.request.IngredientRequest;
import wang.zehui.self.cook.book.domain.response.DishIngredientResponse;
import wang.zehui.self.cook.book.service.IDishIngredientService;
import org.springframework.stereotype.Service;
import wang.zehui.self.cook.book.service.IIngredientService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 菜品食材表(DishIngredient)表服务实现类
 *
 * @author wangzehui
 * @since 2026-06-24 11:54:34
 */
@Service
public class DishIngredientServiceImpl extends ServiceImpl<DishIngredientDao, DishIngredient> implements IDishIngredientService {

    @Resource
    private IIngredientService ingredientService;

    private final static String regex = "(^\\d+)(.+)$|^(.+)$";
    private final static Pattern pattern = Pattern.compile(regex);

    @Override
    @Transactional(rollbackFor = { Exception.class, Error.class, BusinessException.class })
    public Boolean addOrUpdateDishIngredientBatch(List<DishIngredientRequest> dishIngredientRequests) {
        if (CollectionUtils.isEmpty(dishIngredientRequests)) {
            return true;
        }

        this.removeByDishId(dishIngredientRequests.get(0).getDishId());

        // 构建食材保存请求对象
        List<IngredientRequest> ingredientRequests = dishIngredientRequests.stream()
                .map(dishIngredientRequest -> {
                    IngredientRequest ingredientRequest = new IngredientRequest();

                    ingredientRequest.setIngredientName(dishIngredientRequest.getIngredientName());
                    Matcher matcher = pattern.matcher(dishIngredientRequest.getSpec());
                    if (matcher.find()) {
                        String unit = matcher.group(2);
                        if (StringUtils.isBlank(unit)) {
                            unit = "个";
                        }
                        ingredientRequest.setIngredientUnit(unit);
                    }
                    ingredientRequest.setIngredientImageUrl(dishIngredientRequest.getIngredientImageUrl());
                    ingredientRequest.setType(IngredientTypeEnum.USER_INGREDIENT.getValue());
                    return ingredientRequest;
                }).collect(Collectors.toList());

        // 先根据食材名称查询是否存在该食材
        List<String> ingredientNames = ConvertUtil.convertList(ingredientRequests, IngredientRequest::getIngredientName);
        // 存在直接获取id
        Map<String, String> ingredientIdsMap = ingredientService.getIdMapByIngredientNames(new HashSet<>(ingredientNames));
        // 不存在进行保存，获取id后构建菜品食材对象
        ingredientRequests = ingredientRequests.stream()
                .filter(ingredientRequest -> !ingredientIdsMap.containsKey(ingredientRequest.getIngredientName()))
                .collect(Collectors.toList());
        List<Ingredient> ingredients = ingredientService.addOrUpdateIngredientBatch(ingredientRequests);
        ingredients.forEach(ingredient -> ingredientIdsMap.put(ingredient.getIngredientName(), ingredient.getId()));

        List<DishIngredient> dishIngredients = dishIngredientRequests.stream()
                .map(dishIngredientRequest -> {
                    DishIngredient dishIngredient = new DishIngredient();
                    BeanUtils.copyProperties(dishIngredientRequest, dishIngredient);
                    dishIngredient.setIngredientId(ingredientIdsMap.get(dishIngredientRequest.getIngredientName()));
                    Matcher matcher = pattern.matcher(dishIngredientRequest.getSpec());
                    if (matcher.find()) {
                        if (StringUtils.isNumeric(matcher.group(1))) {
                            dishIngredient.setAmount(new BigDecimal(matcher.group(1)));
                            dishIngredient.setUnit(StringUtils.isBlank(matcher.group(2)) ? null : matcher.group(2));
                        } else if (!StringUtils.isBlank(matcher.group(1))) {
                            dishIngredient.setUnit(matcher.group(1));
                        } else {
                            dishIngredient.setUnit(StringUtils.isBlank(matcher.group(3)) ? null : matcher.group(3));
                        }
                    }
                    return dishIngredient;
                }).collect(Collectors.toList());

        return this.saveOrUpdateBatch(dishIngredients);
    }

    @Override
    public List<DishIngredientResponse> getDishIngredients(String dishId) {
        List<DishIngredient> dishIngredients = this.list(Wrappers.<DishIngredient>lambdaQuery()
                .eq(DishIngredient::getDishId, dishId));

        List<String> ingredientIds = ConvertUtil.convertList(dishIngredients, DishIngredient::getIngredientId);
        Map<String, Ingredient> ingredientMapByIds = ingredientService.getIngredientMapByIds(new HashSet<>(ingredientIds));

        return dishIngredients.stream()
                .map(dishIngredient -> {
                    DishIngredientResponse dishIngredientResponse = new DishIngredientResponse();
                    BeanUtils.copyProperties(dishIngredient, dishIngredientResponse);
                    Ingredient ingredient = ingredientMapByIds.get(dishIngredient.getIngredientId());
                    dishIngredientResponse.setIngredientName(ingredient.getIngredientName());
                    dishIngredientResponse.setIngredientImageUrl(ingredient.getIngredientImageUrl());
                    dishIngredientResponse.setType(ingredient.getType());
                    if (Objects.isNull(dishIngredient.getAmount())) {
                        dishIngredientResponse.setSpec(dishIngredient.getUnit());
                    } else {
                        dishIngredientResponse.setSpec(dishIngredient.getAmount().stripTrailingZeros().toPlainString() + dishIngredient.getUnit());
                    }

                    return dishIngredientResponse;
                }).collect(Collectors.toList());
    }

    /**
     * @Description: 通过菜品id删除菜品食材
     * @param dishId 菜品id
     * @Return: void
     * @Author: wangzehui
     * @Date: 2026/7/2 10:41
     */
    private void removeByDishId(String dishId) {
        this.remove(Wrappers.<DishIngredient>lambdaQuery()
                .eq(DishIngredient::getDishId, dishId));
    }
}

