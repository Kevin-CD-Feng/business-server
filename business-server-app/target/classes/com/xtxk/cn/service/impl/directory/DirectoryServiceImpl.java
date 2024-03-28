package com.xtxk.cn.service.impl.directory;

import com.xtxk.cn.entity.DirectoryInfo;
import com.xtxk.cn.entity.DistrictItem;
import com.xtxk.cn.mapper.DirectoryInfoMapper;
import com.xtxk.cn.mapper.MonitorDeviceMapper;
import com.xtxk.cn.service.directory.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DirectoryServiceImpl implements DirectoryService {
    @Autowired
    private DirectoryInfoMapper directoryInfoMapper;

    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;

    /**
     * 查询全部目录结构数据及归属设备数量
     *
     * @return
     */
    @Override
    public List<DirectoryInfo> selectDirectoryList() {
        List<DirectoryInfo> infoList = directoryInfoMapper.selectAll();
        Map<String, List<DirectoryInfo>> map = infoList.stream().collect(Collectors.groupingBy(DirectoryInfo::getParentDirectoryId));
        infoList.forEach(info -> {
            info.setChildren(map.getOrDefault(info.getDirectoryId(), new ArrayList<>()));
            info.setDeviceCount(monitorDeviceMapper.getDirectoryDeviceCount(info.getDirectoryId()));
            if (info.getParentDirectoryId().equals("")) {
                info.setDeviceCount(monitorDeviceMapper.getTotalCount());
            } else {
                if (info.getChildren().size() > 0) {
                    Integer count = 0;
                    for (DirectoryInfo di : info.getChildren()) {
                        count += monitorDeviceMapper.getDirectoryDeviceCount(di.getDirectoryId());
                    }
                    info.setDeviceCount(count);
                }
            }

        });

        return map.get("");
    }

    /**
     * 查询全部区域列表
     * @return
     */
    @Override
    public List<DistrictItem> selectAllArea() {
        List<DistrictItem> list = new ArrayList<>();
        List<String> areas = directoryInfoMapper.selectAllArea();
        if (!areas.isEmpty()) {
            for (int i = 0; i < areas.size(); i++) {
                DistrictItem districtItem = new DistrictItem();
                districtItem.setId(i);
                districtItem.setDistrictName(areas.get(i));
                list.add(districtItem);
            }
        }
        return list;
    }
}
