package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.common.enums.ErrorCodeEnum;
import wang.zehui.self.cook.book.common.utils.ConvertUtil;
import wang.zehui.self.cook.book.common.utils.RequestUtil;
import wang.zehui.self.cook.book.dao.RestaurantCategoryDao;
import wang.zehui.self.cook.book.domain.entity.Restaurant;
import wang.zehui.self.cook.book.domain.entity.RestaurantCategory;
import wang.zehui.self.cook.book.domain.request.RestaurantCategoryRequest;
import wang.zehui.self.cook.book.domain.response.RestaurantCategoryListResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantCategorySearchRequest;
import wang.zehui.self.cook.book.service.IRestaurantCategoryService;
import org.springframework.stereotype.Service;
import wang.zehui.self.cook.book.service.IRestaurantService;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;

/**
 * 餐厅分类表(RestaurantCategory)表服务实现类
 *
 * @author wangzehui
 * @since 2026-06-15 14:15:01
 */
@Service
public class RestaurantCategoryServiceImpl extends ServiceImpl<RestaurantCategoryDao, RestaurantCategory> implements IRestaurantCategoryService {

    @Resource
    private IRestaurantService restaurantService;

    @Override
    public Boolean addOrUpdateCategory(RestaurantCategoryRequest request) {
        restaurantService.checkRestaurant(request.getRestaurantId());

        RestaurantCategory category;
        if (!StringUtils.isBlank(request.getId())) {
            category = this.getById(request.getId());
            if (Objects.isNull(category)) {
                throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
            }
            if (category.getDeleted()) {
                throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
            }
        } else {
            category = new RestaurantCategory();
        }

        BeanUtils.copyProperties(request, category, "id");
        this.saveOrUpdate(category);
        return true;
    }

    @Override
    public PageResult<RestaurantCategoryListResponse> getCategoryPageList(RestaurantCategorySearchRequest request) {
        Page<RestaurantCategory> page = new Page<>(request.getPageNum(), request.getPageSize());

        LambdaQueryWrapper<RestaurantCategory> queryWrapper = Wrappers.<RestaurantCategory>lambdaQuery()
                .eq(!StringUtils.isBlank(request.getId()), RestaurantCategory::getId, request.getId())
                .eq(!StringUtils.isBlank(request.getRestaurantId()), RestaurantCategory::getRestaurantId, request.getRestaurantId())
                .orderByAsc(RestaurantCategory::getSort);

        this.page(page, queryWrapper);

        return PageResult.of(page, PageResult.easyBeanCopyFunction(RestaurantCategoryListResponse::new));
    }

    @Override
    public Boolean deleteCategory(String id) {
        RestaurantCategory category = this.getById(id);
        if (Objects.isNull(category)) {
            return true;
        }

        restaurantService.checkRestaurant(category.getRestaurantId());
        return this.removeById(id);
    }

    @Override
    public void checkCategory(String restaurantId, String restaurantCategoryId) {
        RestaurantCategory restaurantCategory = this.getById(restaurantCategoryId);
        if (Objects.isNull(restaurantCategory)) {
            throw new BusinessException(ErrorCodeEnum.CATEGORY_NOT_EXIST);
        }
        if (restaurantCategory.getDeleted()) {
            throw new BusinessException(ErrorCodeEnum.CATEGORY_NOT_EXIST);
        }
        if (!restaurantCategory.getRestaurantId().equals(restaurantId)) {
            throw new BusinessException(ErrorCodeEnum.CATEGORY_NOT_EXIST);
        }
    }

    @Override
    public Map<String, RestaurantCategory> getCategoryMap(Set<String> restaurantCategoryIds) {
        if (CollectionUtils.isEmpty(restaurantCategoryIds)) {
            return Collections.emptyMap();
        }

        List<RestaurantCategory> restaurantCategories = this.listByIds(restaurantCategoryIds);
        return ConvertUtil.convertMap(restaurantCategories, RestaurantCategory::getId, Function.identity());
    }
}

