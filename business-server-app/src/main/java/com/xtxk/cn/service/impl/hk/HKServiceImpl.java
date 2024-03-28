package com.xtxk.cn.service.impl.hk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.xtxk.cn.dto.hik.*;
import com.xtxk.cn.mapper.MonitorDeviceMapper;
import com.xtxk.cn.service.hk.HKService;
import com.xtxk.cn.utils.json.JsonUtil;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HKServiceImpl implements HKService {
    @Value("${hk.host}")
    private String host;

    @Value("${hk.appKey}")
    private String appKey;

    @Value("${hk.appSecret}")
    private String appSecret;

    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;

    @Override
    public HKCameraResourceResp getHKResources(HKResourcesReq request) {
        ArtemisConfig config = new ArtemisConfig();
        config.setHost(host);
        config.setAppKey(appKey);
        config.setAppSecret(appSecret);
        String cameraPath = "/artemis/api/resource/v1/cameras";
        Map<String, String> paramMap = new HashedMap<>();
        paramMap.put("pageNo", request.getPageNo());
        paramMap.put("pageSize", request.getPageSize());
        String body = JSON.toJSON(paramMap).toString();
        Map<String, String> path = new HashedMap<String, String>(2) {
            {
                put("https://", cameraPath);
            }
        };
        HKCameraResourceResp resp = new HKCameraResourceResp();
        try {
            String result = ArtemisHttpUtil.doPostStringArtemis(path, body, paramMap, null, "application/json", null);
            if (result != null) {
                JSONObject jsonObject = JSON.parseObject(result);
                String code = jsonObject.getString("code");
                if (code.equals("0")) {
                    String data = jsonObject.getString("data");
                    resp.setTotal(Integer.parseInt(JSON.parseObject(data).getString("total")));
                    List<CameraDto> list = JsonUtil.str2List(JSON.parseObject(data).getString("list"), CameraDto.class);
                    resp.setCameraList(list);
                    return resp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public HKCameraResp getHKCameraPreviewUrl(HKCameraPreviewUrlReq req) {
        ArtemisConfig config = new ArtemisConfig();
        config.setHost(host);
        config.setAppKey(appKey);
        config.setAppSecret(appSecret);
        String cameraPath = "/artemis/api/video/v2/cameras/previewURLs";
        Map<String, String> paramMap = new HashedMap<>();
        String cameraCode = monitorDeviceMapper.getCameraCodeByDeviceId(req.getDeviceId());
        paramMap.put("cameraIndexCode", cameraCode);
//        paramMap.put("streamType", 0);
        paramMap.put("protocol", req.getProtocol());
        String body = JSON.toJSON(paramMap).toString();
        Map<String, String> path = new HashedMap<String, String>(2) {
            {
                put("https://", cameraPath);
            }
        };
        HKCameraResp hkCameraResp = new HKCameraResp();
        try {
            String result = ArtemisHttpUtil.doPostStringArtemis(path, body, paramMap, null, "application/json", null);
            if (result != null) {
                JSONObject jsonObject = JSON.parseObject(result);
                String code = jsonObject.getString("code");
                if (code.equals("0")) {
                    String data = jsonObject.getString("data");
                    hkCameraResp.setUrl(JSON.parseObject(data).getString("url"));
                    return hkCameraResp;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hkCameraResp;
    }

    @Override
    public HKCameraResp getHKCameraPlaybackUrl(HKCameraPlaybackUrlReq req) {
        ArtemisConfig config = new ArtemisConfig();
        config.setHost(host);
        config.setAppKey(appKey);
        config.setAppSecret(appSecret);
        String cameraPath = "/artemis/api/video/v2/cameras/playbackURLs";
        Map<String, String> paramMap = new HashedMap<>();
        String cameraCode = monitorDeviceMapper.getCameraCodeByDeviceId(req.getDeviceId());
        paramMap.put("cameraIndexCode", cameraCode);
        paramMap.put("protocol", req.getProtocol());
        paramMap.put("recordLocation", req.getRecordLocation());
        paramMap.put("beginTime", String.format("%sT%s.000+08:00", req.getBeginDate(), req.getBeginTime()));
        paramMap.put("endTime", String.format("%sT%s.000+08:00", req.getEndDate(), req.getEndTime()));
        String body = JSON.toJSON(paramMap).toString();
        Map<String, String> path = new HashedMap<String, String>(2) {
            {
                put("https://", cameraPath);
            }
        };
        HKCameraResp hkCameraResp = new HKCameraResp();
        try {
            String result = ArtemisHttpUtil.doPostStringArtemis(path, body, paramMap, null, "application/json", null);
            if (result != null) {
                JSONObject jsonObject = JSON.parseObject(result);
                String code = jsonObject.getString("code");
                if (code.equals("0")) {
                    String data = jsonObject.getString("data");
                    hkCameraResp.setUrl(JSON.parseObject(data).getString("url"));
                    return hkCameraResp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hkCameraResp;
    }
}
