package com.xtxk.cn.service.impl.alarm;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.date.DateUtil;
import com.xtxk.cn.configurer.minio.MinioConfig;
import com.xtxk.cn.enums.FileDistPathType;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Component
public class VideoManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoManager.class);

    @Autowired
    private MinioConfig minioConfig;

    public String writeRecordToMinio(InputStream inputStream) {
//        String relativePath = prefix +"/"+ FileDistPathType.EVENT.getPath() +
//                "/" + DateUtil.format(new Date(), "yyyyMMdd");
//        String relativePath = "record" +"/";
        String fileName = UUID.randomUUID().toString() + "." + "mp4";
//        String filePath = fileName;
        String path_ex = minioConfig.uploadStream(inputStream, fileName);
        IOUtils.closeQuietly(inputStream);
        return path_ex;
    }
}
