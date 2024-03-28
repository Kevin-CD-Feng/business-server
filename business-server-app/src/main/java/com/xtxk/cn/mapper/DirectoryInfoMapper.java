package com.xtxk.cn.mapper;

import com.xtxk.cn.entity.DirectoryInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 目录结构mapper
 */
@Mapper
public interface DirectoryInfoMapper {
    /**
     * 查询全部目录结构数据
     *
     * @return
     */
    List<DirectoryInfo> selectAll();

    /**
     * 查询全部区域列表
     *
     * @return
     */
    List<String> selectAllArea();
}
