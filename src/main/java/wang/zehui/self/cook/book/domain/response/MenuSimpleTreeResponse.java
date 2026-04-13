package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.MenuTypeEnum;

import java.util.List;

/**
 * @Author wangzehui
 * @Date 2026/4/10 15:34
 */
@Data
public class MenuSimpleTreeResponse {

    @Schema(description = "菜单ID")
    private String id;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "功能点关联菜单ID")
    private String contextMenuId;

    @Schema(description = "父级菜单ID")
    private String parentId;

    @SchemaEnum(description = "菜单类型", value = MenuTypeEnum.class)
    private Integer menuType;

    @Schema(description = "子菜单")
    private List<MenuSimpleTreeResponse> children;
}
