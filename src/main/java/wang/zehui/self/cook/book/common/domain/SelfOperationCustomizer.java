package wang.zehui.self.cook.book.common.domain;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import io.swagger.v3.oas.models.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author wangzehui
 * @Date 2025/11/5 16:15
 */
public class SelfOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        // 权限
        List<String> notes = new ArrayList<>(this.getPermission(handlerMethod));
        operation.setDescription(StringUtils.join("<br/>", notes));

        return operation;
    }

    /**
     * @Description: 获取方法上注解的权限
     * @param handlerMethod 方法
     * @Return: java.util.List<java.lang.String>
     * @Author: wangzehui
     * @Date: 2025/11/5 16:25
     */
    private List<String> getPermission(HandlerMethod handlerMethod) {
        List<String> values = new ArrayList<>();

        StringBuilder permissionStringBuilder = new StringBuilder();
        SaCheckPermission classPermissions = handlerMethod.getBeanType().getAnnotation(SaCheckPermission.class);
        if (!Objects.isNull(classPermissions)) {
            permissionStringBuilder.append("<font style=\"color:red\" class=\"light-red\"");
            permissionStringBuilder.append("类: ").append(this.getAnnotationNote(classPermissions.value(), classPermissions.mode()));
            permissionStringBuilder.append("</font></br>");
        }

        SaCheckPermission methodAnnotation = handlerMethod.getMethodAnnotation(SaCheckPermission.class);
        if (!Objects.isNull(methodAnnotation)) {
            permissionStringBuilder.append("<font style=\"color:red\" class=\"light-red\"");
            permissionStringBuilder.append("方法: ").append(this.getAnnotationNote(methodAnnotation.value(), methodAnnotation.mode()));
            permissionStringBuilder.append("</font></br>");
        }

        if (permissionStringBuilder.length() > 0) {
            permissionStringBuilder.insert(0, "<font style=\"color:red\" class=\"light-red\">权限校验: </font></br>");
            values.add(permissionStringBuilder.toString());
        }

        StringBuilder roleStringBuilder = new StringBuilder();
        SaCheckRole classCheckRole = handlerMethod.getBeanType().getAnnotation(SaCheckRole.class);
        if (!Objects.isNull(classCheckRole)) {
            roleStringBuilder.append("<font style=\"color:red\" class=\"light-red\"");
            roleStringBuilder.append("类: ").append(this.getAnnotationNote(classCheckRole.value(), classCheckRole.mode()));
            roleStringBuilder.append("</font></br>");
        }

        SaCheckRole methodCheckRole = handlerMethod.getMethodAnnotation(SaCheckRole.class);
        if (!Objects.isNull(methodCheckRole)) {
            roleStringBuilder.append("<font style=\"color:red\" class=\"light-red\"");
            roleStringBuilder.append("方法: ").append(this.getAnnotationNote(methodCheckRole.value(), methodCheckRole.mode()));
            roleStringBuilder.append("</font></br>");
        }

        if (roleStringBuilder.length() > 0) {
            roleStringBuilder.insert(0, "<font style=\"color:red\" class=\"light-red\">角色校验: </font></br>");
            values.add(roleStringBuilder.toString());
        }

        return values;
    }

    private String getAnnotationNote(String[] values, SaMode mode) {
        if (mode.equals(SaMode.AND)) {
            return String.join(" 且 ", values);
        } else {
            return String.join(" 或 ", values);
        }
    }
}
