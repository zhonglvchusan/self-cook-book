package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2026/7/29 11:28
 */
@Getter
@AllArgsConstructor
public enum LikeTypeEnum implements BaseEnum {

    COMMENT_LIKE(0, "评论点赞"),

    ;

    private final Integer value;

    private final String description;
}
