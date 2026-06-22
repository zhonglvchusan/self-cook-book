package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.common.enums.ErrorCodeEnum;
import wang.zehui.self.cook.book.common.enums.OperationTypeEnum;
import wang.zehui.self.cook.book.dao.RestaurantDishDao;
import wang.zehui.self.cook.book.domain.entity.RestaurantDish;
import wang.zehui.self.cook.book.domain.request.RestaurantDishRequest;
import wang.zehui.self.cook.book.domain.request.RestaurantDishSearchRequest;
import wang.zehui.self.cook.book.domain.response.RestaurantDishListResponse;
import wang.zehui.self.cook.book.service.IRestaurantCategoryService;
import wang.zehui.self.cook.book.service.IRestaurantDishService;
import org.springframework.stereotype.Service;
import wang.zehui.self.cook.book.service.IRestaurantService;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 菜品分类菜品表(RestaurantDish)表服务实现类
 *
 * @author wangzehui
 * @since 2026-06-22 14:24:43
 */
@Service
public class RestaurantDishServiceImpl extends ServiceImpl<RestaurantDishDao, RestaurantDish> implements IRestaurantDishService {

    @Resource
    private IRestaurantService restaurantService;

    @Resource
    private IRestaurantCategoryService restaurantCategoryService;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class, BusinessException.class})
    public Boolean addOrUpdateDish(RestaurantDishRequest request) {
        restaurantService.checkRestaurant(request.getRestaurantId());
        restaurantCategoryService.checkCategory(request.getRestaurantId(), request.getRestaurantCategoryId());

        RestaurantDish dish;
        if (!StringUtils.isBlank(request.getId())) {
            dish = this.getById(request.getId());
            if (Objects.isNull(dish)) {
                throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
            }
            if (dish.getDeleted()) {
                throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
            }
        } else {
            dish = new RestaurantDish();
        }

        BeanUtils.copyProperties(request, dish, "id");
        this.saveOrUpdate(dish);

        // 菜品保存完毕后，增加餐厅餐品数量
        restaurantService.updateRestaurantDishNumber(dish.getRestaurantId(), OperationTypeEnum.PLUS.getValue());

        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class, BusinessException.class})
    public Boolean deleteDish(String id) {
        RestaurantDish dish = this.getById(id);
        if (Objects.isNull(dish)) {
            return true;
        }

        restaurantService.checkRestaurant(dish.getRestaurantId());
        restaurantCategoryService.checkCategory(dish.getRestaurantId(), dish.getRestaurantCategoryId());

        this.removeById(id);

        // 更新菜品数量
        restaurantService.updateRestaurantDishNumber(dish.getRestaurantId(), OperationTypeEnum.SUBTRACT.getValue());

        return true;
    }

    @Override
    public PageResult<RestaurantDishListResponse> getDishPageList(RestaurantDishSearchRequest request) {
        Page<RestaurantDish> page = new Page<>(request.getPageNum(), request.getPageSize());

        if (StringUtils.isBlank(request.getRestaurantCategoryId())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
        }

        LambdaQueryWrapper<RestaurantDish> queryWrapper = Wrappers.<RestaurantDish>lambdaQuery()
                .eq(RestaurantDish::getRestaurantCategoryId, request.getRestaurantCategoryId())
                .like(!StringUtils.isBlank(request.getDishName()), RestaurantDish::getDishName, request.getDishName());

        this.page(page, queryWrapper);

        return PageResult.of(page, PageResult.easyBeanCopyFunction(RestaurantDishListResponse::new));
    }
}

