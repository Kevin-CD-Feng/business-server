package com.xtxk.cn.third.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/***
 * @description TODO
 * @author liulei
 * @date 2023/4/12 16:00
 */
@Component
public class RedisUtils {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置字符串类型缓存
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置字符串类型缓存并设置过期时间
     *
     * @param key
     * @param value
     * @param expire 过期时间，单位：分钟
     */
    public boolean setStringExpire(String key, Object value, long expire) {
        try {
            if (expire > 0) {
                redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            } else {
                setString(key, String.valueOf(value));
            }
            return true;
        } catch (Exception e) {
            logger.error("setStringExpire设置缓存失败,key:{} value: {} expire: {} error: {}", key, value, expire, e.getMessage());
            return false;
        }
    }

    /**
     * 设置对象类型缓存并设置过期时间
     *
     * @param key
     * @param value
     * @param expire
     */
    public void setObject(String key, Object value, TimeUnit expire) {
        redisTemplate.opsForValue().set(key, value, 3000, expire);
    }

    /**
     * 读取字符串类型缓存
     * @param key
     * @return
     */
    public String getString(String key) {
        return redisTemplate.opsForValue().get(key).toString();
    }

    /**
     * 读取字符串类型缓存并设置过期时间
     *
     * @param key
     * @param expire
     * @return
     */
    public String getString(String key, TimeUnit expire) {
        expire(key, expire);
        return getString(key);
    }

    /**
     * 读取对象类型缓存
     *
     * @param key
     * @return
     */
    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key删除缓存内容
     *
     * @param key
     */
    public boolean removeObject(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 设置缓存过期时间
     *
     * @param key
     * @param expire
     */
    public boolean expire(String key, TimeUnit expire) {
        return redisTemplate.expire(key, 3000, expire);
    }

    /**
     * 返回当前选定数据库中的key数量
     *
     * @return
     */
    public long getRedisSize() {
        return redisTemplate.keys("*").size();
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 使用hash 添加单个元素
     *
     * @param key
     * @param field
     * @param value
     * @param expire
     */
    public boolean hashSet(String key, String field, Object value, int expire) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            if (expire > 0) {
                expire(key, expire);
            }
            return true;
        } catch (Exception e) {
            logger.error("redis hashSet, key :{}, values :{} , error: {} ", key, value, e.getMessage());
            return false;
        }
    }

    /**
     * 使用哈希结构存储设置多个元素缓存并设置过期时间
     *
     * @param key
     * @param values
     * @param expire
     */
    public boolean hashSetMap(String key, Map<String, Object> values, int expire) {
        try {
            redisTemplate.opsForHash().putAll(key, values);
            expire(key, expire);
            return true;
        } catch (Exception e) {
            logger.error("redis setHashObject缓存失败, key :{}, values :{} , error: {}  ", key, (values), e.getMessage());
            return false;
        }
    }

    /**
     * 读取哈希结构中某个field的内容
     *
     * @param key
     * @param field
     * @return
     */
    public Object getHashObject(String key, String field) {
        try {
            return redisTemplate.opsForHash().get(key, field);
        } catch (Exception e) {
            logger.error("redis getHashObject异常, key :{}, field :{} , error: {}  ", key, field, e.getMessage());
        }
        return null;
    }

    /**
     * 获取hash key值的数量
     *
     * @param key
     */
    public long getHashSize(String key) {
        try {
            return redisTemplate.opsForHash().size(key);
        } catch (Exception e) {
            logger.error("=========================》从redis中获取key值size: {} ，错误信息： {} ", key, e.getMessage());
        }
        return 0;
    }

    /**
     * 读取哈希结构中所有values值
     *
     * @param key
     * @return
     */
    public List<?> getHashObject(String key) {
        try {
            return redisTemplate.opsForHash().values(key);
        } catch (Exception e) {
            logger.error("从redis中获取key值: {} 数据失败，错误信息： {} ", key, e.getMessage());
            return null;
        }
    }

    /**
     * 根据key值获取整个HashMap对象信息
     *
     * @param key
     * @return
     */
    public Map<Object, Object> getHashMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 根据key和 模糊匹配的field查询hash对象
     *
     * @param key
     * @param fieldMatch 模糊匹配的field
     * @param count      需要返回结果集个数
     * @return
     */
    public Map<String, Object> scanHashData(String key, String fieldMatch, long count) {
        Map<String, Object> result = new HashMap<>();
        Cursor<Map.Entry<Object, Object>> scan = redisTemplate.opsForHash().scan(key,
                new ScanOptions.ScanOptionsBuilder().count(count).match(fieldMatch).build());
        while (scan.hasNext()) {
            Map.Entry<Object, Object> map = scan.next();
            result.put(String.valueOf(map.getKey()), map.getValue());
        }
        return result;
    }

    /**
     * 移除哈希结构中的某个field内容
     *
     * @param key
     * @param field
     */
    public long removeHashObject(String key, String field) {
        try {
            return redisTemplate.opsForHash().delete(key, field);
        } catch (Exception e) {
            logger.error("删除redis key: {}失败~" + e.getMessage());
            return 0;
        }
    }

    /**
     * 数组存放队列中
     * List
     *
     * @param key
     * @param data
     * @param expire
     */
    public <T> boolean leftPushAll(String key, List<T> data, int expire) {
        redisTemplate.opsForList().leftPushAll(key, data);
        expire(key, expire);
        return true;
    }

    /**
     * 单独放入对队列中
     * value为对象类型，且不设置过期时间，默认永久
     * List
     *
     * @param key
     * @param value
     * @param expire
     */
    public boolean leftPush(String key, Object value, int expire) {
        return redisTemplate.opsForList().leftPush(key, value) > 0;
    }

    /**
     * 获取某个队列中元素值
     * List
     *
     * @param key
     */
    public long size(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 弹出最右边的元素，弹出之后该值在列表中将不复存在
     * List
     *
     * @param key
     */
    public <T> T rightPop(String key) {
        return (T) redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 根据index获取list值 *
     *
     * @param key
     * @param index -1 返回最后一个,下标越界返回null
     */
    public <T> T index(String key, long index) {
        return (T) redisTemplate.opsForList().index(key, index);
    }

    /**
     * @param key
     */
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /***
     * 修改key名称
     * @param oldKey
     * @param newKey
     */
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /***
     * set 添加元素
     * @param key
     * @param value
     */
    public boolean addSet(String key, Object value) {
        return redisTemplate.opsForSet().add(key, value) > 0;
    }

    /***
     * set 删除某个元素
     * @param key
     * @param value
     * @return
     */
    public boolean removeSet(String key, Object value) {
        return redisTemplate.opsForSet().remove(key, value) > 0;
    }



    /**
     * 设置过期时间
     * @param key
     * @param time
     */
    public void expire(String key, long time) {
        redisTemplate.opsForValue().set(key, time);
    }
}
