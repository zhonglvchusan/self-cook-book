package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.CheckEnum;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.MenuTypeEnum;

/**
 * @Author wangzehui
 * @Date 2026/4/9 11:15
 */
@Data
public class MenuRequest {

    @Schema(description = "菜单ID")
    private String id;

    @Schema(description = "菜单名称")
    private String menuName;

    @SchemaEnum(description = "菜单类型", value = MenuTypeEnum.class)
    @CheckEnum(value = MenuTypeEnum.class, message = "菜单类型错误")
    private Integer menuType;

    @Schema(description = "父菜单ID")
    private String parentId;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "是否为外链")
    private Boolean frameFlag;

    @Schema(description = "外链地址")
    private String frameUrl;

    @Schema(description = "是否缓存")
    private Boolean cacheFlag;

    @Schema(description = "显示状态")
    private Boolean visibleFlag;

    @Schema(description = "禁用状态")
    private Boolean disabledFlag;

    @Schema(description = "后端权限字符串")
    private String apiPerms;

    @Schema(description = "权限类型")
    private Boolean permsType;

    @Schema(description = "前端权限字符串")
    private String webPerms;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "功能点关联菜单ID")
    private String contextMenuId;

}
