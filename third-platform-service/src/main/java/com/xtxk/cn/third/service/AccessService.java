package com.xtxk.cn.third.service;


/***
 * @description TODO
 * @author liulei
 * @date 2023-09-04 18:32
 */
public interface AccessService {


    /***
     * 拉取门禁闸机数据
     * @param page
     * @param size
     * @return
     */
    Boolean pullAccessControlInfo(Integer page, Integer size);

    /***
     * 同步门禁闸机数据
     * @return
     */
    Boolean syncAccessControlInfo();
}
