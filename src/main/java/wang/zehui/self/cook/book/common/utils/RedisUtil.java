package wang.zehui.self.cook.book.common.utils;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author wangzehui
 * @Date 2025/11/10 14:49
 */
@Component
@Slf4j
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @Description: 设置key过期时间
     * @param key 键
     * @param time 时间(秒)
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/10 15:49
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("expire error", e);
            return false;
        }
    }

    /**
     * @Description: 获取key过期时间
     * @param key 键
     * @Return: long 时间(秒) 返回 0 代表为永久有效
     * @Author: wangzehui
     * @Date: 2025/11/10 15:50
     */
    public long getExpireTime(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * @Description: 判断是否存在key
     * @param key 键
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/10 15:53
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("hasKey error", e);
            return false;
        }
    }

    /**
     * @Description: 删除指定key
     * @param key 键（一个或多个）
     * @Return: void
     * @Author: wangzehui
     * @Date: 2025/11/10 15:58
     */
    public void del(String... key) {
        if (!Objects.isNull(key) && key.length == 1) {
            redisTemplate.delete(key[0]);
        } else {
            redisTemplate.delete(Arrays.asList(key));
        }
    }

    /**
     * @Description: 获取指定key的值
     * @param key 键
     * @Return: java.lang.Object
     * @Author: wangzehui
     * @Date: 2025/11/10 16:03
     */
    public Object get(String key) {
        return StringUtils.isBlank(key) ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * @Description: 获取指定key的值，转换成指定对象
     * @param key 键
     * @param clazz 要转换的对象
     * @Return: T 转换后的对象
     * @Author: wangzehui
     * @Date: 2025/11/11 15:30
     */
    public <T> T get(String key, Class<T> clazz) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Object value = this.get(key);
        return JSON.parseObject(String.valueOf(value), clazz);
    }

    /**
     * @Description: 普通缓存放入
     * @param key
     * @param value
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/10 16:05
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("set no expire error", e);
            return false;
        }
    }

    /**
     * @Description: 缓存key，并设置过期时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) 若time > 0 则设置过期时间,否则将不设置过期时间
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/10 16:13
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                this.set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("set expire error", e);
            return false;
        }
    }

    /**
     * @Description: 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     * @Return: long
     * @Author: wangzehui
     * @Date: 2025/11/10 16:35
     */
    public long increment(String key, long delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * @Description: 递减
     * @param key 键
     * @param delta 要减少几(大于0)
     * @Return: long
     * @Author: wangzehui
     * @Date: 2025/11/10 16:36
     */
    public long decrement(String key, long delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * @Description: 获取list缓存的内容
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置 0 到 -1 代表所有值
     * @Return: java.util.List<java.lang.Object>
     * @Author: wangzehui
     * @Date: 2025/11/10 17:12
     */
    public List<Object> listGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("listGet error", e);
            return null;
        }
    }

    /**
     * @Description: 获取list缓存的内容，转换成指定对象
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置 0 到 -1 代表所有值
     * @param clazz 要转换的对象
     * @Return: java.util.List<T>
     * @Author: wangzehui
     * @Date: 2025/11/11 15:33
     */
    public <T> List<T> listGet(String key, long start, long end, Class<T> clazz) {
        try {
            List<Object> list = this.listGet(key, start, end);
            return list.stream()
                    .map(item -> JSON.parseObject(String.valueOf(item), clazz))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("listGet error", e);
            return null;
        }
    }

    /**
     * @Description: 获取缓存的list的长度
     * @param key 键
     * @Return: long
     * @Author: wangzehui
     * @Date: 2025/11/10 17:14
     */
    public long listGetListSize(String key) {
        try {
            Long size = redisTemplate.opsForList().size(key);
            return Objects.isNull(size) ? 0 : size;
        } catch (Exception e) {
            log.error("listGetSize error", e);
            return 0;
        }
    }

    /**
     * @Description: 获取list指定索引位置的值
     * @param key 键
     * @param index 索引 index >=0 时，0: 表头，1：第二个元素，依此类推；index < 0 时，-1: 表尾，-2：倒数第二个元素，依此类推
     * @Return: java.lang.Object
     * @Author: wangzehui
     * @Date: 2025/11/10 17:16
     */
    public Object listGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("listGetIndex error", e);
            return null;
        }
    }

    /**
     * @Description: 获取list指定索引位置的值，转换成指定对象
     * @param key 键
     * @param index 索引 index >=0 时，0: 表头，1：第二个元素，依此类推；index < 0 时，-1: 表尾，-2：倒数第二个元素，依此类推
     * @param clazz 要转换的对象
     * @Return: T
     * @Author: wangzehui
     * @Date: 2025/11/11 15:35
     */
    public <T> T listGetIndex(String key, long index, Class<T> clazz) {
        try {
            Object value = this.listGetIndex(key, index);
            return JSON.parseObject(String.valueOf(index), clazz);
        } catch (Exception e) {
            log.error("listGetIndex error", e);
            return null;
        }
    }

    /**
     * @Description: 缓存数据至list中，从表尾添加
     * @param key 键
     * @param value 数据
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/10 17:19
     */
    public boolean listSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("listSet error", e);
            return false;
        }
    }

    /**
     * @Description: 将要缓存的数据放入list中，并设置过期时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/10 17:51
     */
    public boolean listSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("listSet time error", e);
            return false;
        }
    }

    /**
     * @Description: 将list放入缓存中
     * @param key 键
     * @param value 列表
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/10 17:54
     */
    public boolean listSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("listSet list error", e);
            return false;
        }
    }

    /**
     * @Description: 将list放入缓存中，并设置过期时间
     * @param key 键
     * @param value 列表
     * @param time 时间(秒)
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/10 17:56
     */
    public boolean listSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("listSet list time error", e);
            return false;
        }
    }

    /**
     * @Description: 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/10 17:57
     */
    public boolean listUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("listUpdateIndex error", e);
            return false;
        }
    }

    /**
     * @Description: 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @Return: long
     * @Author: wangzehui
     * @Date: 2025/11/10 17:59
     */
    public long listRemove(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error("listRemove error", e);
            return 0;
        }
    }

    /**
     * @Description: 根据key获取Set中的所有值
     * @param key 键
     * @Return: java.util.Set<java.lang.Object>
     * @Author: wangzehui
     * @Date: 2025/11/10 18:00
     */
    public Set<Object> setGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("setGet error", e);
            return null;
        }
    }

    /**
     * @Description: 根据key获取Set中的所有值，转换成指定对象
     * @param key 键
     * @param clazz 要转换的对象
     * @Return: java.util.Set<T>
     * @Author: wangzehui
     * @Date: 2025/11/11 15:37
     */
    public <T> Set<T> setGet(String key, Class<T> clazz) {
        try {
            Set<Object> set = this.setGet(key);
            return set
                    .stream()
                    .map(item -> JSON.parseObject(String.valueOf(item), clazz))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("setGet error", e);
            return null;
        }
    }

    /**
     * @Description: 判断value是否存在set中
     * @param key 键
     * @param value 值
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/11 9:47
     */
    public boolean setHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("setHasKey error", e);
            return false;
        }
    }

    /**
     * @Description: 将数据放入set缓存中
     * @param key 键
     * @param values 值
     * @Return: long
     * @Author: wangzehui
     * @Date: 2025/11/11 9:49
     */
    public long setSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("setSet error", e);
            return 0;
        }
    }

    /**
     * @Description: 将set数据放入缓存中，并设置过期时间
     * @param key 键
     * @param time 时间(秒)
     * @param values 值
     * @Return: long
     * @Author: wangzehui
     * @Date: 2025/11/11 9:52
     */
    public long setSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                this.expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("setSetAndTime error", e);
            return 0;
        }
    }

    /**
     * @Description: 获取set缓存的长度
     * @param key 键
     * @Return: long
     * @Author: wangzehui
     * @Date: 2025/11/11 9:53
     */
    public long setGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("setGetSetSize error", e);
            return 0;
        }
    }

    /**
     * @Description: 删除set中值为value的
     * @param key 键
     * @param values 值
     * @Return: long
     * @Author: wangzehui
     * @Date: 2025/11/11 9:54
     */
    public long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("setRemove error", e);
            return 0;
        }
    }

    /**
     * @Description: 获取map中的值
     * @param key 键
     * @param item 键
     * @Return: java.lang.Object
     * @Author: wangzehui
     * @Date: 2025/11/11 9:55
     */
    public Object hashGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * @Description: 获取map中的值，转换成指定对象
     * @param key 键
     * @param item 键
     * @param clazz 要转换的对象
     * @Return: T
     * @Author: wangzehui
     * @Date: 2025/11/11 15:39
     */
    public <T> T hashGet(String key, String item, Class<T> clazz) {
        Object value = this.hashGet(key, item);
        return JSON.parseObject(String.valueOf(value), clazz);
    }

    /**
     * @Description: 获取hashKey对应的所有键值
     * @param key 键
     * @Return: java.util.Map<java.lang.Object,java.lang.Object>
     * @Author: wangzehui
     * @Date: 2025/11/11 9:57
     */
    public Map<Object, Object> hashMapGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * @Description: 设置hashMap
     * @param key 键
     * @param map 集合
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/11 9:58
     */
    public boolean hashMapSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("hashMapSet error", e);
            return false;
        }
    }

    /**
     * @Description: 添加hashMap并设置过期时间
     * @param key 键
     * @param map 键
     * @param time 时间(秒)
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/11 9:59
     */
    public boolean hashMapSet(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("hashMapSet error", e);
            return false;
        }
    }

    /**
     * @Description: 向一张hash表中放入数据，如果不存在将创建
     * @param key 键
     * @param item map的键
     * @param value map的值
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/11 10:05
     */
    public boolean hashSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("hashSet error", e);
            return false;
        }
    }

    /**
     * @Description: 向一张hash表中放入数据，如果不存在将创建并设置过期时间
     * @param key 键
     * @param item map的键
     * @param value map的值
     * @param time 时间(秒)  如果已存在的hash表有时间，这里将会替换原有的时间
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/11 10:06
     */
    public boolean hashSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("hashSet error", e);
            return false;
        }
    }

    /**
     * @Description: 删除hash表中的值
     * @param key 键
     * @param item 项
     * @Return: void
     * @Author: wangzehui
     * @Date: 2025/11/11 10:08
     */
    public void hashDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * @Description: 判断hash表中是否有该项的值
     * @param key 键
     * @param item 项
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/11 10:09
     */
    public boolean hashHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * @Description: hash递增，如果不存在则创建一个，并把新增后的值返回
     * @param key 键
     * @param item 键
     * @param delta 要增加几(大于0)
     * @Return: double
     * @Author: wangzehui
     * @Date: 2025/11/11 10:11
     */
    public double hashIncrement(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * @Description: hash递减
     * @param key 键
     * @param item 键
     * @param delta 要减少几(小于0)
     * @Return: double
     * @Author: wangzehui
     * @Date: 2025/11/11 10:15
     */
    public double hashDecrement(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    /**
     * @Description: 使用HyperLogLog添加元素
     * @param key 键
     * @param values 值
     * @Return: long
     * @Author: wangzehui
     * @Date: 2025/11/11 10:17
     */
    public long pfAdd(String key, Object... values) {
        return redisTemplate.opsForHyperLogLog().add(key, values);
    }

    /**
     * @Description: 使用HyperLogLog获取基数估算值
     * @param key 键
     * @Return: long
     * @Author: wangzehui
     * @Date: 2025/11/11 10:18
     */
    public long pfCount(String key) {
        return redisTemplate.opsForHyperLogLog().size(key);
    }

    /**
     * @Description: 使用HyperLogLog删除元素
     * @param key 键
     * @Return: void
     * @Author: wangzehui
     * @Date: 2025/11/11 10:19
     */
    public void pfRemove(String key) {
        redisTemplate.opsForHyperLogLog().delete(key);
    }

    /**
     * @Description: 使用HyperLogLog合并元素
     * @param destination 目标键
     * @param sourceKeys 源键
     * @Return: void
     * @Author: wangzehui
     * @Date: 2025/11/11 10:20
     */
    public void pfMerge(String destination, String... sourceKeys) {
        redisTemplate.opsForHyperLogLog().union(destination, sourceKeys);
    }
}
