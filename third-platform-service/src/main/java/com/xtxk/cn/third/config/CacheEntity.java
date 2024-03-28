package com.xtxk.cn.third.config;

import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-22 15:41
 */
@Data
public class CacheEntity<T> implements Serializable {

    private static final long serialVersionUID = -3855020141770031110L;

    /***
     * 缓存数据
     */
    private T data;

    /***
     * 创建的单位时间
     */
    private long createTime = System.currentTimeMillis();

    /***
     * 缓存有效期 单位ms 小于0 永久保存
     */
    private long cacheTime;

    public CacheEntity(T data, long cacheTime) {
        this.data = data;
        this.cacheTime = cacheTime;
    }

    public CacheEntity() {

    }
}
