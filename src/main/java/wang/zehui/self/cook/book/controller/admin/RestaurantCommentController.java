package wang.zehui.self.cook.book.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import wang.zehui.self.cook.book.service.IRestaurantCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 餐厅评论表(RestaurantComment)表控制层
 *
 * @author wangzehui
 * @since 2026-07-16 11:49:21
 */
@Tag(name = "")
@RestController
@RequestMapping("/restaurant/comment")
public class RestaurantCommentController {

    @Resource
    private IRestaurantCommentService restaurantCommentService;

}

