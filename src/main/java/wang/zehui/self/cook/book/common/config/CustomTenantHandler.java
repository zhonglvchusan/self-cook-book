package wang.zehui.self.cook.book.common.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.utils.RequestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangzehui
 * @Date 2026/4/2 12:09
 */
@Component
@Slf4j
public class CustomTenantHandler implements TenantLineHandler {

    private final List<String> ignoreTableNames = new ArrayList<String>(){{
        add("t_user");
    }};

    @Override
    public Expression getTenantId() {
        String userId = RequestUtil.getUserId();
        if (StringUtils.isBlank(userId)) {
            log.error("获取租户id时，租户id为空");
            throw new BusinessException("用户未登录");
        }
        return new StringValue(userId);
    }

    @Override
    public String getTenantIdColumn() {
        return TenantLineHandler.super.getTenantIdColumn();
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return ignoreTableNames.contains(tableName);
    }

    @Override
    public boolean ignoreInsert(List<Column> columns, String tenantIdColumn) {
        return TenantLineHandler.super.ignoreInsert(columns, tenantIdColumn);
    }
}
