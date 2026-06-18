package wang.zehui.self.cook.book.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import wang.zehui.self.cook.book.service.IRestaurantCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 餐厅分类表(RestaurantCategory)表控制层
 *
 * @author wangzehui
 * @since 2026-06-15 14:15:01
 */
@Tag(name = "")
@RestController
@RequestMapping("/restaurant/category")
public class RestaurantCategoryController {

    @Resource
    private IRestaurantCategoryService restaurantCategoryService;

}

