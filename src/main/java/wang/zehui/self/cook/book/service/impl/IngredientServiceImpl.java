package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.dao.IngredientDao;
import wang.zehui.self.cook.book.domain.entity.Ingredient;
import wang.zehui.self.cook.book.domain.request.IngredientRequest;
import wang.zehui.self.cook.book.domain.request.IngredientSearchRequest;
import wang.zehui.self.cook.book.domain.response.IngredientListResponse;
import wang.zehui.self.cook.book.service.IIngredientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 食材表(Ingredient)表服务实现类
 *
 * @author wangzehui
 * @since 2026-06-24 10:51:24
 */
@Service
public class IngredientServiceImpl extends ServiceImpl<IngredientDao, Ingredient> implements IIngredientService {

    @Override
    @Transactional(rollbackFor = { Exception.class, Error.class, BusinessException.class })
    public List<Ingredient> addOrUpdateIngredientBatch(List<IngredientRequest> requests) {
        if (CollectionUtils.isEmpty(requests)) {
            return new ArrayList<>();
        }

        List<Ingredient> ingredients = requests.stream()
                .map(request -> {
                    Ingredient ingredient = new Ingredient();
                    BeanUtils.copyProperties(request, ingredient);
                    return ingredient;
                }).collect(Collectors.toList());

        this.saveOrUpdateBatch(ingredients);

        // 返回id
        return ingredients;
    }

    @Override
    public Boolean deleteIngredient(String ingredientId) {
        if (StringUtils.isBlank(ingredientId)) {
            return true;
        }

        return this.removeById(ingredientId);
    }

    @Override
    public PageResult<IngredientListResponse> getIngredientPageList(IngredientSearchRequest request) {
        Page<Ingredient> page = new Page<>(request.getPageNum(), request.getPageSize());

        LambdaQueryWrapper<Ingredient> queryWrapper = Wrappers.<Ingredient>lambdaQuery()
                .eq(!Objects.isNull(request.getType()), Ingredient::getType, request.getType())
                .like(!StringUtils.isBlank(request.getIngredientName()), Ingredient::getIngredientName, request.getIngredientName());

        this.page(page, queryWrapper);

        return PageResult.of(page, PageResult.easyBeanCopyFunction(IngredientListResponse::new));
    }

}

