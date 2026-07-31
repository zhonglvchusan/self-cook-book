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
import wang.zehui.self.cook.book.common.enums.UserAdminFlagEnum;
import wang.zehui.self.cook.book.common.utils.ConvertUtil;
import wang.zehui.self.cook.book.common.utils.RequestUtil;
import wang.zehui.self.cook.book.dao.RestaurantDao;
import wang.zehui.self.cook.book.domain.entity.Restaurant;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.RestaurantRequest;
import wang.zehui.self.cook.book.domain.request.RestaurantSearchRequest;
import wang.zehui.self.cook.book.domain.response.RestaurantListResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantResponse;
import wang.zehui.self.cook.book.service.IRestaurantService;
import org.springframework.stereotype.Service;
import wang.zehui.self.cook.book.service.IUserService;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;

/**
 * 餐厅信息表(Restaurant)表服务实现类
 *
 * @author wangzehui
 * @since 2026-06-03 14:58:48
 */
@Service
public class RestaurantServiceImpl extends ServiceImpl<RestaurantDao, Restaurant> implements IRestaurantService {

    @Resource
    private IUserService userService;

    @Resource
    private RestaurantDao restaurantDao;

    @Override
    public RestaurantResponse getRestaurantInfo(String restaurantId) {
        Restaurant restaurant;

        String userId = RequestUtil.getUserId();
        ErrorCodeEnum errorCodeEnum;
        // 没传id，从我的餐厅进来的
        if (StringUtils.isBlank(restaurantId)) {
            errorCodeEnum = ErrorCodeEnum.USER_RESTAURANT_NOT_EXIST;
            restaurant = this.getRestaurantByUserId(userId);
        } else {
            errorCodeEnum = ErrorCodeEnum.RESTAURANT_NOT_EXIST;
            restaurant = this.getById(restaurantId);
        }

        if (Objects.isNull(restaurant)) {
            throw new BusinessException(errorCodeEnum);
        }

        if (restaurant.getDeleted()) {
            throw new BusinessException(errorCodeEnum);
        }

        RestaurantResponse response = new RestaurantResponse();
        BeanUtils.copyProperties(restaurant, response);

        // 管理员判断
        response.setAdminFlag(Objects.equals(userId, restaurant.getRestaurantUserId()));
        return response;
    }

    @Override
    public Restaurant getRestaurantByUserId(String userId) {
        return this.getOne(Wrappers.<Restaurant>lambdaQuery()
                .eq(Restaurant::getRestaurantUserId, userId));
    }

    @Override
    public Boolean addOrUpdateRestaurant(RestaurantRequest request) {
        Restaurant restaurant;
        if (!StringUtils.isBlank(request.getId())) {
            restaurant = this.getById(request.getId());
            if (Objects.isNull(restaurant)) {
                throw new BusinessException(ErrorCodeEnum.RESTAURANT_NOT_EXIST);
            }

            if (!Objects.equals(restaurant.getRestaurantUserId(), RequestUtil.getUserId())) {
                throw new BusinessException(ErrorCodeEnum.RESTAURANT_NOT_PERMISSION);
            }
        } else {
            restaurant = new Restaurant();
            restaurant.setRestaurantUserId(RequestUtil.getUserId());
        }

        BeanUtils.copyProperties(request, restaurant);
        return this.saveOrUpdate(restaurant);
    }

    @Override
    public Boolean deleteRestaurant(String restaurantId) {
        Restaurant restaurant = this.getById(restaurantId);
        if (Objects.isNull(restaurant)) {
            return true;
        }

        return this.removeById(restaurant);
    }

    @Override
    public PageResult<RestaurantListResponse> getRestaurantPageList(RestaurantSearchRequest request) {
        Page<Restaurant> page = new Page<>(request.getPageNum(), request.getPageSize());

        LambdaQueryWrapper<Restaurant> queryWrapper = Wrappers.<Restaurant>lambdaQuery()
                .eq(!StringUtils.isBlank(request.getId()), Restaurant::getId, request.getId())
                .like(!StringUtils.isBlank(request.getRestaurantName()), Restaurant::getRestaurantName, request.getRestaurantName())
                .like(!StringUtils.isBlank(request.getRestaurantDescription()), Restaurant::getRestaurantDescription, request.getRestaurantDescription());

        if (Objects.equals(UserAdminFlagEnum.USER.getCode(), RequestUtil.getUserRequest().getAdminFlag())) {
            queryWrapper.eq(Restaurant::getRestaurantExternalFlag, true);
        }

        this.page(page, queryWrapper);

        List<Restaurant> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResult.of(page, PageResult.easyBeanCopyFunction(RestaurantListResponse::new));
        }
        List<String> userIds = ConvertUtil.convertList(records, Restaurant::getRestaurantUserId);
        Map<String, User> userMap = userService.getUserMap(userIds);

        return PageResult.of(page, restaurant -> {
            RestaurantListResponse response = new RestaurantListResponse();
            BeanUtils.copyProperties(restaurant, response);
            response.setRestaurantUserName(userMap.get(restaurant.getRestaurantUserId()).getNickname());
            return response;
        });
    }

    @Override
    public void checkRestaurant(String restaurantId) {
        Restaurant restaurant = this.getById(restaurantId);
        if (Objects.isNull(restaurant)) {
            throw new BusinessException(ErrorCodeEnum.RESTAURANT_NOT_EXIST);
        }
        if (restaurant.getDeleted()) {
            throw new BusinessException(ErrorCodeEnum.RESTAURANT_NOT_EXIST);
        }
        if (!RequestUtil.getUserRequest().getIsAdmin() && !restaurant.getRestaurantUserId().equals(RequestUtil.getUserId())) {
            throw new BusinessException(ErrorCodeEnum.RESTAURANT_NOT_PERMISSION);
        }
    }

    @Override
    public void updateRestaurantDishNumber(String restaurantId, Integer operation) {
        restaurantDao.updateDishNumber(restaurantId, operation);
    }

    @Override
    public Map<String, Restaurant> getRestaurantMap(Set<String> restaurantIds) {
        if (CollectionUtils.isEmpty(restaurantIds)) {
            return Collections.emptyMap();
        }

        List<Restaurant> restaurants = this.listByIds(restaurantIds);
        return ConvertUtil.convertMap(restaurants, Restaurant::getId, Function.identity());
    }
}

