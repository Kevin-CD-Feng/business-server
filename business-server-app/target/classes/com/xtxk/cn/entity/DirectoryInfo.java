package com.xtxk.cn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DirectoryInfo implements Serializable {
    private String directoryId;

    private String directoryName;

    private String parentDirectoryId;

    private Integer deviceCount;

    private List<DirectoryInfo> children;


}
