package com.xtxk.cn.third.service;

import org.apache.logging.log4j.util.BiConsumer;

import java.util.List;

/***
 * @description 批处理
 * @author liulei
 * @date 2023-08-2311:51
 */
public interface BatchService {

    /***
     * mybatis 批量保存数据
     * @param list
     * @param clazz
     * @param biConsumer
     * @param <M>
     * @param <T>
     * @return
     */
    <M, T> boolean batchSave(List<T> list, Class<M> clazz, BiConsumer<M, T> biConsumer);

}
