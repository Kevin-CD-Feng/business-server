package com.xtxk.cn.third.config;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/***
 * @description 本地缓存
 * @author liulei
 * @date 2023-08-22 15:32
 */
@Configuration
public class CacheConfig {

    private static ConcurrentHashMap<String, CacheEntity> cacheMap = new ConcurrentHashMap<>();
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void infinite() {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 1, 1, TimeUnit.MINUTES);
    }

    public void refresh() {
        for (String key : cacheMap.keySet()) {
            if (isExpire(key)) {
                remove(key);
            }
        }
    }


    /***
     * 设置缓存
     * @param key
     * @param value
     * @param cacheTime
     * @param timeUnit
     * @param <T>
     * @return
     */
    public synchronized <T> boolean put(String key, T value, long cacheTime, TimeUnit timeUnit) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        CacheEntity<T> cacheEntity = new CacheEntity<>();
        cacheEntity.setCacheTime(timeUnit.toMillis(cacheTime));
        cacheEntity.setData(value);
        cacheMap.put(key, cacheEntity);
        return true;
    }

    /***
     * 获取缓存数据
     * @param key
     * @return
     */
    public synchronized Object get(String key) {
        if (StringUtils.isBlank(key) || isExpire(key)) {
            return null;
        }
        CacheEntity cache = cacheMap.get(key);
        if (ObjectUtil.isNull(cache)) {
            return null;
        } else {
            return cache.getData();
        }
    }

    /***
     * 移除缓存数据
     * @param key
     * @return
     */
    public synchronized boolean remove(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        if (!cacheMap.containsKey(key)) {
            return true;
        }
        cacheMap.remove(key);
        return true;
    }

    /***
     * 判断是否过期
     * @param key
     * @return
     */
    private synchronized boolean isExpire(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        CacheEntity cache = cacheMap.get(key);
        if (ObjectUtil.isNull(cache)) {
            return false;
        }
        long createTime = cache.getCreateTime();
        long currentTime = System.currentTimeMillis();
        long cacheTime = cache.getCacheTime();
        if (cacheTime > 0 && currentTime - createTime > cacheTime) {
            return true;
        } else {
            return false;
        }
    }

}
