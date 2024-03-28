package com.xtxk.cn.third.service.impl.hk;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.lmax.disruptor.EventHandler;
import com.xtxk.cn.third.config.MinioConfig;
import com.xtxk.cn.third.configuration.HkConfiguration;
import com.xtxk.cn.third.dto.hk.AlarmInfoRequest;
import com.xtxk.cn.third.dto.hk.EventMessage;
import com.xtxk.cn.third.entity.hk.HkHighParabolicDeviceRel;
import com.xtxk.cn.third.mapper.HkHighParabolicDeviceRelMapper;
import com.xtxk.cn.third.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.*;

/***
 * @description 高空抛物消息回调
 * @author liulei
 * @date 2023-09-04 10:17
 */
@Service
public class ConsumerMessageImpl implements EventHandler<EventMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerMessageImpl.class);
    @Autowired
    private MinioConfig minioConfig;
    @Autowired
    private HkHighParabolicDeviceRelMapper deviceRelMapper;
    @Resource
    private HttpUtils httpUtils;
    @Autowired
    private HkConfiguration hkConfiguration;
    private Map<String, HkHighParabolicDeviceRel> deviceMap = new HashMap<>();

    @Override
    public void onEvent(EventMessage eventMessage, long l, boolean b) throws Exception {
        String imagUrl = eventMessage.getImgUrl();
        byte[] data = httpUtils.download(imagUrl);
        String filePath = minioConfig.uploadStream(new ByteArrayInputStream(data), imagUrl.substring(imagUrl.lastIndexOf("/") + 1));
        HkHighParabolicDeviceRel device = deviceMap.get(eventMessage.getResourceId());
        if (StringUtils.isBlank(filePath) || ObjectUtil.isNull(device)) {
            logger.info("onEvent 回调消息处理,文件路径 filePath:{} ,设备信息:{}", filePath, JSONUtil.toJsonStr(device));
            return;
        }

        DateTime dateTime = DateUtil.parse(eventMessage.getTime());
        Date date = new Date(dateTime.getTime());
        AlarmInfoRequest request = new AlarmInfoRequest();
        // shi'jain
        request.setCatchTime(String.valueOf(date.getTime()));
        request.setEventCode(hkConfiguration.getEventCode());
        request.setData(UUID.randomUUID().toString());
        request.setPath(filePath);
        request.setResourceId(device.getDeviceId());
        pushMessage(request);
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @PostConstruct
    private void refresh() {
        logger.info("定时刷新海康设备id关联设备数据");
        List<HkHighParabolicDeviceRel> list = deviceRelMapper.selectList();
        for (HkHighParabolicDeviceRel hk : list) {
            deviceMap.put(hk.getHkDeviceId(), hk);
        }
    }

    /***
     * 告警推送接口
     * @param info
     */
    private void pushMessage(AlarmInfoRequest info) {
        String requestUrl = hkConfiguration.getPushMessageUrl();
        try {
            HttpRequest httpRequest = HttpUtil.createPost(requestUrl);
            httpRequest.timeout(3000);
            httpRequest.body(JSONUtil.toJsonStr(info));
            HttpResponse httpResponse = httpRequest.execute();
            if (!httpResponse.isOk()) {
                logger.error("调用消息告警推送接口失败:{}", httpResponse.body());
            }
        } catch (Exception e) {
            logger.error("推送高空抛物告警数据异常：requestUrl:{}, data:{}, errorMsg:{}", requestUrl, JSONUtil.toJsonStr(info), e.getMessage());
        }
    }


}
