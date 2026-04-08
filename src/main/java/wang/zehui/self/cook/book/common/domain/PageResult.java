package wang.zehui.self.cook.book.common.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Author wangzehui
 * @Date 2026/4/8 11:18
 */
@Data
public class PageResult<T> {

    @Schema(description = "当前页码")
    private Long pageNum;

    @Schema(description = "每页数量")
    private Long pageSize;

    @Schema(description = "总记录数")
    private Long total;

    @Schema(description = "总页数")
    private Long pages;

    @Schema(description = "结果集")
    private List<T> list;

    /**
     * @Description: 转换分页结果
     * @param page 数据库分页对象
     * @param function 返回List转换函数
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<T>
     * @Author: wangzehui
     * @Date: 2026/4/8 12:06
     */
    public static <T, E> PageResult<T> of(Page<E> page, Function<E, T> function) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());
        pageResult.setList(page.getRecords().stream()
                .map(function)
                .collect(Collectors.toList()));

        return pageResult;
    }

    /**
     * @Description: 简单的BeanCopy函数，
     *  适用于分页对象转换时，直接使用BeanUtils.copyProperties即可以完成全部字段赋值的情况
     *  若存在自定义逻辑，在使用of转换时，自定义function函数
     * @param target 目标对象类型
     * @Return: java.util.function.Function<E,T>
     * @Author: wangzehui
     * @Date: 2026/4/8 12:13
     */
    public static <T, E> Function<E, T> easyBeanCopyFunction(Supplier<T> target) {
        return (source) -> {
            T t = target.get();
            BeanUtils.copyProperties(source, t);
            return t;
        };
    }
}
