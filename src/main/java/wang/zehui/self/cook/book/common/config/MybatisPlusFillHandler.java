package wang.zehui.self.cook.book.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import wang.zehui.self.cook.book.common.utils.RequestUtil;

/**
 * @Author wangzehui
 * @Date 2026/4/9 14:59
 */
@Component
@Slf4j
public class MybatisPlusFillHandler implements MetaObjectHandler {

    private static final String CREATE_USER_ID = "createUserId";

    private static final String UPDATE_USER_ID = "updateUserId";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter(CREATE_USER_ID)) {
            this.fillStrategy(metaObject, CREATE_USER_ID, RequestUtil.getUserId());
        }
        if (metaObject.hasSetter(UPDATE_USER_ID)) {
            this.fillStrategy(metaObject, UPDATE_USER_ID, RequestUtil.getUserId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATE_USER_ID)) {
            this.fillStrategy(metaObject, UPDATE_USER_ID, RequestUtil.getUserId());
        }
    }
}
