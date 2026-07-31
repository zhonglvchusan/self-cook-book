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
import wang.zehui.self.cook.book.common.enums.ErrorCodeEnum;
import wang.zehui.self.cook.book.common.enums.LaunchTypeEnum;
import wang.zehui.self.cook.book.common.enums.OperationTypeEnum;
import wang.zehui.self.cook.book.common.utils.ConvertUtil;
import wang.zehui.self.cook.book.common.utils.RequestUtil;
import wang.zehui.self.cook.book.dao.RestaurantDishDao;
import wang.zehui.self.cook.book.domain.entity.Restaurant;
import wang.zehui.self.cook.book.domain.entity.RestaurantCategory;
import wang.zehui.self.cook.book.domain.entity.RestaurantDish;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.AdminDishSearchRequest;
import wang.zehui.self.cook.book.domain.request.RestaurantDishRequest;
import wang.zehui.self.cook.book.domain.request.RestaurantDishSearchRequest;
import wang.zehui.self.cook.book.domain.response.AdminDishListResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantDishInfoResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantDishListResponse;
import wang.zehui.self.cook.book.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;

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

    @Resource
    private IDishStepService dishStepService;

    @Resource
    private IDishIngredientService dishIngredientService;

    @Resource
    private IUserService userService;

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

        // 基础信息处理完毕后，处理菜品食材
        // 设置菜品食材
        request.getDishIngredientRequests().forEach(dishIngredient -> dishIngredient.setDishId(dish.getId()));
        dishIngredientService.addOrUpdateDishIngredientBatch(request.getDishIngredientRequests());

        // 基础信息处理完毕后，处理菜品步骤
        // 先设置菜品id
        request.getDishStepRequests().forEach(dishStep -> dishStep.setDishId(dish.getId()));
        dishStepService.addOrUpdateDishStep(request.getDishStepRequests());

        // 菜品保存完毕后，增加餐厅餐品数量
        if (StringUtils.isBlank(request.getId())) {
            restaurantService.updateRestaurantDishNumber(dish.getRestaurantId(), OperationTypeEnum.PLUS.getValue());
        }

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
                .eq(RestaurantDish::getLaunchFlag, LaunchTypeEnum.NORMAL.getValue())
                .eq(!StringUtils.isBlank(request.getRestaurantCategoryId()), RestaurantDish::getRestaurantCategoryId, request.getRestaurantCategoryId())
                .like(!StringUtils.isBlank(request.getDishName()), RestaurantDish::getDishName, request.getDishName());

        this.page(page, queryWrapper);

        return PageResult.of(page, PageResult.easyBeanCopyFunction(RestaurantDishListResponse::new));
    }

    @Override
    public RestaurantDishInfoResponse getDishInfo(String dishId) {
        RestaurantDish dish = this.getById(dishId);

        if (Objects.isNull(dish)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
        }

        RestaurantDishInfoResponse response = new RestaurantDishInfoResponse();
        BeanUtils.copyProperties(dish, response);

        // 获取菜品食材
        response.setDishIngredients(dishIngredientService.getDishIngredients(dishId));

        // 获取菜品步骤
        response.setDishSteps(dishStepService.getDishSteps(dishId));

        return response;
    }

    @Override
    public Map<String, RestaurantDish> getDishMapByIds(List<String> dishIds) {
        if (CollectionUtils.isEmpty(dishIds)) {
            return Collections.emptyMap();
        }

        List<RestaurantDish> restaurantDishes = this.listByIds(dishIds);

        return ConvertUtil.convertMap(restaurantDishes, RestaurantDish::getId, Function.identity());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class, BusinessException.class})
    public Boolean updateDishLaunchStatus(String dishId, Integer launchType) {
        if (launchType == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
        }

        RestaurantDish dish = this.getById(dishId);
        // 上架
        if (Objects.equals(LaunchTypeEnum.NORMAL.getValue(), launchType)) {
            if (Objects.equals(dish.getLaunchFlag(), LaunchTypeEnum.ADMIN_TAKE_DOWN.getValue()) && !RequestUtil.getUserRequest().getIsAdmin()) {
                throw new BusinessException(ErrorCodeEnum.LAUNCH_TYPE_ERROR);
            }
            restaurantService.updateRestaurantDishNumber(dish.getRestaurantId(), OperationTypeEnum.PLUS.getValue());
        } else {
            restaurantService.updateRestaurantDishNumber(dish.getRestaurantId(), OperationTypeEnum.SUBTRACT.getValue());
        }

        return this.update(Wrappers.<RestaurantDish>lambdaUpdate()
                .eq(RestaurantDish::getId, dishId)
                .set(RestaurantDish::getLaunchFlag, launchType));
    }

    @Override
    public PageResult<AdminDishListResponse> getAdminDishPageList(AdminDishSearchRequest request) {
        Page<RestaurantDish> page = new Page<>(request.getPageNum(), request.getPageSize());

        LambdaQueryWrapper<RestaurantDish> queryWrapper = Wrappers.<RestaurantDish>lambdaQuery()
                .eq(!StringUtils.isBlank(request.getId()), RestaurantDish::getId, request.getId())
                .eq(!Objects.isNull(request.getLaunchFlag()), RestaurantDish::getLaunchFlag, request.getLaunchFlag())
                .eq(!StringUtils.isBlank(request.getRestaurantId()), RestaurantDish::getRestaurantId, request.getRestaurantId());

        this.page(page, queryWrapper);

        List<RestaurantDish> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResult.of(page);
        }

        List<String> restaurantIds = ConvertUtil.convertList(records, RestaurantDish::getRestaurantId);
        Map<String, Restaurant> restaurantMap = restaurantService.getRestaurantMap(new HashSet<>(restaurantIds));
        List<String> categoryIds = ConvertUtil.convertList(records, RestaurantDish::getRestaurantCategoryId);
        Map<String, RestaurantCategory> categoryMap = restaurantCategoryService.getCategoryMap(new HashSet<>(categoryIds));
        List<String> createUserIds = ConvertUtil.convertList(records, RestaurantDish::getCreateUserId);
        Map<String, User> createUserMap = userService.getUserMap(createUserIds);

        return PageResult.of(page, dish -> {
            AdminDishListResponse response = new AdminDishListResponse();
            BeanUtils.copyProperties(dish, response);
            response.setRestaurantName(restaurantMap.get(dish.getRestaurantId()).getRestaurantName());
            response.setRestaurantCategoryName(categoryMap.get(dish.getRestaurantCategoryId()).getCategoryName());
            response.setCreateUserName(createUserMap.get(dish.getCreateUserId()).getNickname());
            return response;
        });
    }
}

