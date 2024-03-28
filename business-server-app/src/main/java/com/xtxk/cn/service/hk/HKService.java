package com.xtxk.cn.service.hk;

import com.xtxk.cn.dto.hik.*;

public interface HKService {
    /**
     * 获取海康平台资源列表
     *
     * @return
     */
    HKCameraResourceResp getHKResources(HKResourcesReq request);

    /**
     * 获取海康摄像头实时预览url
     *
     * @return
     */
    HKCameraResp getHKCameraPreviewUrl(HKCameraPreviewUrlReq cameraPreviewUrlReq);

    /**
     * 获取海康摄像头录像回放url
     *
     * @param cameraPlaybackUrlReq
     * @return
     */
    HKCameraResp getHKCameraPlaybackUrl(HKCameraPlaybackUrlReq cameraPlaybackUrlReq);
}
