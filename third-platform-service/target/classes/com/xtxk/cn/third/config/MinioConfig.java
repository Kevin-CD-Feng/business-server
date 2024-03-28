package com.xtxk.cn.third.config;


import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import io.minio.*;
import io.minio.http.Method;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 封装minio文件上传下载
 *
 * @author Administrator
 * @version 1.0
 */
@Component
public class MinioConfig implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MinioConfig.class);

    /**
     * minio 服务其地址
     */
    @Value("${minio.endpoint}")
    private String endpoint;
    /**
     * 用户名
     */
    @Value("${minio.accessKey}")
    private String accessKey;
    /**
     * 密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;
    /**
     * 默认文件存储bucket
     */
    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 文件上传下载操作句柄
     */
    private MinioClient minioClient;


    /***
     * 通过MultipartFile上传文件
     * @param file
     * @return 文件名称
     * @throws IOException
     */
    public String upload(MultipartFile file) {
        String fileName = genFileName(file.getOriginalFilename());
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName)
                    .contentType(file.getContentType())
                    .stream(inputStream, inputStream.available(), -1).build());
            logger.info("上传文件{}到【{}】成功", fileName, bucketName);
            return fileName;
        } catch (Exception e) {
            logger.error("上传文件{}到【{}】失败,异常原因：{}", fileName, bucketName, e.getMessage());
            throw new RuntimeException("上传文件失败,异常原因：" + e.getMessage());
        }
    }

    /***
     * 上传本地文件
     * @param filePath 本地文件路径
     * @return
     */
    public String localUpload(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        String fileName = genFileName(file.getName());
        try {
            minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucketName).object(fileName).filename(filePath).build());
            logger.info("上传本地文件{}到【{}】成功", fileName, bucketName);
            return fileName;
        } catch (Exception e) {
            logger.error("上传本地文件{}到【{}】失败,异常原因：{}", fileName, bucketName, e.getMessage());
            throw new RuntimeException("上传本地文件失败,异常原因：" + e.getMessage());
        }
    }


    /***
     * 上传文件流
     * @param inputStream
     * @param objectName 文件名称
     * @return
     */
    public String uploadStream(InputStream inputStream, String objectName) {
        String fileName = genFileName(objectName);
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, inputStream.available(), -1)
                    .contentType(new MimetypesFileTypeMap().getContentType(fileName))
                    .build());
            logger.info("上传文件流{}到【{}】成功", fileName, bucketName);
            return fileName;
        } catch (Exception e) {
            logger.error("上传文件流{}到【{}】失败,异常原因:{}", fileName, bucketName, e.getMessage());
            throw new RuntimeException("上传文件流异常：" + e.getMessage());
        }
    }

    /***
     * 根据文件名称删除文件
     * @param fileName
     * @return
     */
    public boolean delete(String fileName) {
        RemoveObjectArgs args = RemoveObjectArgs.builder()
                .object(fileName)
                .bucket(bucketName)
                .build();
        try {
            minioClient.removeObject(args);
            logger.info("删除文件【{}】成功", fileName);
            return true;
        } catch (Exception e) {
            logger.error("删除文件【{}】失败,异常原因：{}", fileName, e.getMessage());
            throw new RuntimeException("删除文件流异常：" + e.getMessage());
        }
    }


    /***
     * 下载文件
     * @param fileName 文件名称
     * @return
     */
    public InputStream downLoad(String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            logger.error("下载文件异常异常:" + e.getMessage());
            throw new RuntimeException("下载文件流异常：" + e.getMessage());
        }
    }

    /***
     * 获取文件信息
     * @param fileName
     * @return
     * @throws Exception
     */
    public StatObjectResponse fileObject(String fileName) throws Exception {
        return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }

    /***
     * 根据文件名称预览文件
     * @param fileName
     * @return
     * @throws Exception
     */
    public String getPreviewFileUrl(String fileName) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(fileName).build());
    }

    /***
     * 判断文件是否存在
     * @param fileName
     * @return
     */
    public boolean doesFileExist(String fileName) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /***
     * 绘制图片区域
     * @param imageByte
     * @param points
     * @param imageFormatter
     * @return
     * @throws IOException
     */
    public byte[] draw(byte[] imageByte, List<Integer> points, String imageFormatter) throws IOException {
        if (points == null || points.size() == 0) {
            return imageByte;
        }
        // TODO 存在多个识别区域 【position [935,298,155,260]】 每4个点为一组
        List<List<Integer>> morePoints = Lists.partition(points, 4);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageByte));
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        //设置线条宽度
        graphics.setStroke(new BasicStroke(8));
        graphics.setColor(Color.RED);
        //graphics.setFont(new Font("微软雅黑", Font.ITALIC, 20));
        morePoints.forEach(f -> {
            //格式点坐标x轴  y轴 宽度 高度
            graphics.drawRect(f.get(0), f.get(1), f.get(2), f.get(3));
        });
        //关闭绘制
        graphics.dispose();
        //将绘制后的图片获取字节
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, imageFormatter, out);
        byte[] bytes = out.toByteArray();
        if (out != null) {
            IOUtils.closeQuietly(out);
        }
        return bytes;
    }

    /**
     * 所有的文件上传后统一修改文件名称，规则:yyyyMMdd/UUID.fileType
     *
     * @param fileName 文件名称
     * @return
     */
    private String genFileName(String fileName) {
        return DateUtil.date() + "/" + UUID.randomUUID().toString().replace("-","") + "." + FilenameUtils.getExtension(fileName).toLowerCase();
    }

    @Override
    public void run(String... args) throws Exception {
        Objects.requireNonNull(bucketName, "bucketName 不能为空！");
        minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        logger.info("minio client【{}】连接成功", endpoint);
        // 检测是否存在默认的文件存储bucket，不存在创建
        try {
            Boolean exist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            // 过滤掉已经存在的buckets
            if (!exist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                logger.info("创建文件存储桶{}成功", bucketName);
            }
        } catch (Exception e) {
            logger.error("初始化minio存储桶异常：{}", e.getMessage());
        }
    }

}
