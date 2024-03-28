package com.xtxk.cn.service.directory;

import com.xtxk.cn.entity.DirectoryInfo;
import com.xtxk.cn.entity.DistrictItem;

import java.util.List;

public interface DirectoryService {
    List<DirectoryInfo> selectDirectoryList();

    List<DistrictItem> selectAllArea();
}
