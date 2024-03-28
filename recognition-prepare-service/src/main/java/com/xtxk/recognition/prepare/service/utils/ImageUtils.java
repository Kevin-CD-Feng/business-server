package com.xtxk.recognition.prepare.service.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import com.xtxk.recognition.prepare.service.enums.ErrorCode;
import com.xtxk.recognition.prepare.service.exception.ServiceException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ImageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * 校验base64
     * @param imageBaseStr
     * @return
     */
    public static List<String> checkImageBaseStr(String[] imageBaseStr){
        return Arrays.stream(imageBaseStr).map(temp -> {
            if(checkImage(temp)){
                return temp;
            }else{
                return null;
            }
        }).filter(obj-> ObjectUtil.isNotEmpty(obj)).collect(Collectors.toList());
    }



    /**
     * 校验imageBase64是否是图片
     * @param imageBase64
     * @return
     */
    public static boolean checkImage(String imageBase64){
        boolean flag = false;
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(imageBase64)));
            if(null != bufferedImage){
                flag = true;
            }
        } catch (IOException e) {
            LOGGER.error("校验字符串是否为流数据异常");
            throw new ServiceException(ErrorCode.ERROR);
        }
        return flag;
    }

    /**
     * 根据图片url转化为base64
     * @param imagePaths
     * @param path
     * @return
     */
    public static String[] imagePathToBase64(String[] imagePaths, String path) {
        return Arrays.stream(imagePaths).map(imagePath -> {
            byte[] bytes = new byte[0];
            try {
                if (imagePath.contains(path)) {
                    bytes = IOUtils.toByteArray(new URL(imagePath));
                } else {
                    bytes = IOUtils.toByteArray(new URL(path + imagePath));
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            return Base64.encode(bytes);
        }).toArray(String[]::new);
    }
}
