package com.xtxk.recognition.prepare.service.manager;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.date.DateUtil;
import com.xtxk.recognition.prepare.service.configurer.MinioConfig;
import com.xtxk.recognition.prepare.service.enums.ErrorCode;
import com.xtxk.recognition.prepare.service.enums.FileDistPathType;
import com.xtxk.recognition.prepare.service.exception.ServiceException;
import io.minio.GetObjectArgs;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class ImageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageManager.class);

    @Autowired
    private MinioConfig minioConfig;

    /**
     * base64字符串写入本地
     * @param prefix
     * @param imageStr
     * @return
     */
    public String writeToLocal(String basePath,String prefix,String imageStr){
        //文件路径前缀
        String filePrefixPath = prefix + File.separator + FileDistPathType.EVENT.getPath() +
                File.separator + DateUtil.format(new Date(), "yyyy-MM-dd");
        File directory = new File(basePath + File.separator + filePrefixPath);
        //目录不存在就创建
        if (!directory.exists()) {
            directory.mkdirs();
        }

        InputStream inputStream = new ByteArrayInputStream(Base64Decoder.decode(imageStr));
        //文件重命名
        String fileName = UUID.randomUUID().toString() + "." + "jpg";
        String filePath = basePath + File.separator + filePrefixPath + File.separator + fileName;
        try {
            int index;
            byte[] bytes = new byte[1024];
            FileOutputStream ops = new FileOutputStream(filePath);
            while((index = inputStream.read(bytes)) != -1){
                ops.write(bytes,0,index);
                ops.flush();
            }
            ops.close();
            inputStream.close();
        } catch (Exception e) {
            LOGGER.error("文件上传异常", e);
            throw new ServiceException(ErrorCode.WRITE_TO_LOCAL_ERROR);
        }
        LOGGER.info("文件写入成功,路径：{}", filePath);
        /** 返回相对路径 */
        return filePrefixPath + File.separator + fileName;
    }

    public String writeToMinio(String prefix,String imageStr) {
        InputStream inputStream = new ByteArrayInputStream(Base64Decoder.decode(imageStr));
//        String relativePath = prefix +"/"+ FileDistPathType.EVENT.getPath() +
//                "/" + DateUtil.format(new Date(), "yyyyMMdd");
//        String fileName = UUID.randomUUID().toString() + "." + "jpg";
        String filePath = prefix;
        String path_ex = minioConfig.uploadStream(inputStream,filePath);
        IOUtils.closeQuietly(inputStream);
        return path_ex;
    }


    public String readFromMinio(String path) throws IOException {
        InputStream inputStream = minioConfig.downLoad(path);
        byte [] jpgByte = org.apache.commons.io.IOUtils.toByteArray(inputStream);
        return Base64.getEncoder().encodeToString(jpgByte);
    }
}
