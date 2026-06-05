package wang.zehui.self.cook.book.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import wang.zehui.self.cook.book.service.IRestaurantService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 餐厅信息表(Restaurant)表控制层
 *
 * @author wangzehui
 * @since 2026-06-03 14:58:48
 */
@Tag(name = "")
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Resource
    private IRestaurantService restaurantService;

}

