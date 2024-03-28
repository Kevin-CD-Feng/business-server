package com.xtxk.cn.third.service;

import com.xtxk.cn.third.dto.build.MockBuild;
import com.xtxk.cn.third.entity.build.BuildingInfo;

import java.util.List;

/***
 * @description 楼栋数据
 * @author liulei
 * @date 2023-08-22 10:37
 */
public interface BuildService {

    /***
     * 拉取楼栋数据
     * @param page
     * @param size
     * @return
     */
    Integer pullBuildInfo(Integer page,Integer size);

    /***
     *  同步楼栋数据
     * @return
     */
    Boolean syncBuild();

    /***
     * mock
     * @return
     */
    Boolean mockInfo(List<MockBuild> buildList);

    /***
     * 查询楼栋数据
     * @return
     */
    List<BuildingInfo> selectByLakeBuild();
}
