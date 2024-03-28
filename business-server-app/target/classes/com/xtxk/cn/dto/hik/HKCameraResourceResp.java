package com.xtxk.cn.dto.hik;

import lombok.Data;

import java.util.List;

@Data
public class HKCameraResourceResp {
    Integer total;
    List<CameraDto> cameraList;
}
