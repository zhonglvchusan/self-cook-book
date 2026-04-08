package wang.zehui.self.cook.book.common.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author wangzehui
 * @Date 2026/4/8 15:16
 */
public class ConvertUtil {

    /**
     * @Description: 获取list中指定字段list
     * @param list 源list
     * @param convert 转换方法
     * @Return: java.util.List<R> 指定字段list
     * @Author: wangzehui
     * @Date: 2026/4/8 15:28
     */
    public static <T, R> List<R> convertList(List<T> list, Function<T, R> convert) {
        Set<R> set = new HashSet<>();
        if (!CollectionUtils.isEmpty(list)) {
            set = list.stream()
                    .filter(Objects::nonNull)
                    .map(convert)
                    .collect(Collectors.toSet());
        }
        return new ArrayList<>(set);
    }

    /**
     * @Description: 将对应的list转为需要的map
     * @param list 源list
     * @param keyConvert key转换方法
     * @param valueConvert value转换方法
     * @Return: java.util.Map<R,R1>
     * @Author: wangzehui
     * @Date: 2026/4/8 15:34
     */
    public static <T, R, R1> Map<R, R1> convertMap(List<T> list, Function<T, R> keyConvert, Function<T, R1> valueConvert) {
        Map<R, R1> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (T t : list) {
                map.put(keyConvert.apply(t), valueConvert.apply(t));
            }
        }
        return map;
    }
}
