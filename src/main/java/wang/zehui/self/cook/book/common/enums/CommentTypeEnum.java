package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2026/7/16 14:14
 */
@Getter
@AllArgsConstructor
public enum CommentTypeEnum implements BaseEnum {

    COMMENT(0, "评论"),

    REPLY(1, "回复"),

    ;

    private final Integer value;

    private final String description;

}
