package wang.zehui.self.cook.book.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import wang.zehui.self.cook.book.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (User)表控制层
 *
 * @author wangzehui
 * @since 2025-11-06 11:19:18
 */
@Tag(name = "")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

}

