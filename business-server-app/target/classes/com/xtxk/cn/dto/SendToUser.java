package com.xtxk.cn.dto;

import lombok.Data;

import java.util.List;

@Data
public class SendToUser<T> {
    private String funcName;
    private List<T> data;
}