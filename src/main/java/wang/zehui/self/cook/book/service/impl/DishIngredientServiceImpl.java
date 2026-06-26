package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import wang.zehui.self.cook.book.dao.DishIngredientDao;
import wang.zehui.self.cook.book.domain.entity.DishIngredient;
import wang.zehui.self.cook.book.domain.request.DishIngredientRequest;
import wang.zehui.self.cook.book.domain.request.IngredientRequest;
import wang.zehui.self.cook.book.domain.response.DishIngredientResponse;
import wang.zehui.self.cook.book.service.IDishIngredientService;
import org.springframework.stereotype.Service;
import wang.zehui.self.cook.book.service.IIngredientService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
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

    private final static String regex = "\\d+|.+";
    private final static Pattern pattern = Pattern.compile(regex);

    @Override
    public Boolean addOrUpdateDishIngredientBatch(List<DishIngredientRequest> dishIngredientRequests) {
        if (CollectionUtils.isEmpty(dishIngredientRequests)) {
            return true;
        }

        List<IngredientRequest> ingredientRequests = dishIngredientRequests.stream()
                .map(dishIngredientRequest -> {
                    IngredientRequest ingredientRequest = new IngredientRequest();

                    Matcher matcher = pattern.matcher(dishIngredientRequest.getSpec());
                    if (matcher.find()) {
                        ingredientRequest.setIngredientName(matcher.group(0));
                        ingredientRequest.setIngredientUnit(matcher.group(1));
                    }
                    return ingredientRequest;
                }).collect(Collectors.toList());
        return null;
    }

    @Override
    public List<DishIngredientResponse> getDishIngredients(String dishId) {
        return Collections.emptyList();
    }
}

