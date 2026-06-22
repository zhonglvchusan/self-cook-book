package wang.zehui.self.cook.book.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import wang.zehui.self.cook.book.service.IRestaurantDishService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜品分类菜品表(RestaurantDish)表控制层
 *
 * @author wangzehui
 * @since 2026-06-22 14:24:42
 */
@Tag(name = "餐厅下菜品管理相关接口")
@RestController
@RequestMapping("/restaurant/dish")
public class RestaurantDishController {

    @Resource
    private IRestaurantDishService restaurantDishService;

}

