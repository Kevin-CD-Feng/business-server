package com.xtxk.recognition.prepare.service.configurer;

import com.xtxk.recognition.prepare.service.entity.AlarmPolicyConfiguration;
import io.minio.*;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class MinioConfig implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioConfig.class);
    @Autowired
    private MinioProperty minioProperty;

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
            minioClient.putObject(PutObjectArgs.builder().bucket(this.minioProperty.getBucketName()).object(fileName)
                    .contentType(file.getContentType())
                    .stream(inputStream, inputStream.available(), -1).build());
            LOGGER.info("上传文件{}到【{}】成功", fileName, this.minioProperty.getBucketName());
            return fileName;
        } catch (Exception e) {
            LOGGER.error("上传文件{}到【{}】失败,异常原因：{}", fileName, this.minioProperty.getBucketName(), e.getMessage());
            return null;
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
            minioClient.uploadObject(UploadObjectArgs.builder().bucket(this.minioProperty.getBucketName()).object(fileName).filename(filePath).build());
            LOGGER.info("上传本地文件{}到【{}】成功", fileName, this.minioProperty.getBucketName());
            return fileName;
        } catch (Exception e) {
            LOGGER.error("上传本地文件{}到【{}】失败,异常原因：{}", fileName, this.minioProperty.getBucketName(), e.getMessage());
            return null;
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
            minioClient.putObject(PutObjectArgs.builder().bucket(this.minioProperty.getBucketName())
                    .object(fileName)
                    .stream(inputStream, inputStream.available(), -1)
                    .contentType(new MimetypesFileTypeMap().getContentType(fileName))
                    .build());
            LOGGER.info("上传文件流{}到【{}】成功", fileName, this.minioProperty.getBucketName());
            return fileName;
        } catch (Exception e) {
            LOGGER.info("上传文件流{}到【{}】失败,异常原因:{}", fileName, this.minioProperty.getBucketName(), e.getMessage());
            return null;
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
                .bucket(this.minioProperty.getBucketName())
                .build();
        try {
            minioClient.removeObject(args);
            LOGGER.info("删除文件【{}】成功", fileName);
            return true;
        } catch (Exception e) {
            LOGGER.info("删除文件【{}】失败,异常原因：{}", fileName, e.getMessage());
            return false;
        }
    }

    /***
     * 下载文件
     * @param fileName 文件名称
     * @return
     */
    public InputStream downLoad(String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(this.minioProperty.getBucketName()).object(fileName).build());
        } catch (Exception e) {
            LOGGER.error("下载文件异常异常:" + e.getMessage());
            return null;
        }
    }

    /***
     * 获取文件信息
     * @param fileName
     * @return
     * @throws Exception
     */
    public StatObjectResponse fileObject(String fileName) throws Exception {
        return minioClient.statObject(StatObjectArgs.builder().bucket(this.minioProperty.getBucketName()).object(fileName).build());
    }

    /***
     * 根据文件名称预览文件
     * @param fileName
     * @return
     * @throws Exception
     */
    public String getPreviewFileUrl(String fileName) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET)
                .bucket(this.minioProperty.getBucketName())
                .object(fileName).build());
    }

    /***
     * 判断文件是否存在
     * @param fileName
     * @return
     */
    public boolean doesFileExist(String fileName) {
        try {
            minioClient.statObject(StatObjectArgs.builder().
                    bucket(this.minioProperty.getBucketName()).object(fileName).build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 所有的文件上传后统一修改文件名称，规则:yyyyMMdd/UUID.fileType
     *
     * @param fileName 文件名称
     * @return
     */
    private String genFileName(String fileName) {
        //return DateUtil.getDays() + "/" + U.uuid() + "." + FilenameUtils.getExtension(fileName).toLowerCase();
        return fileName;
    }

    @Override
    public void run(String... args) throws Exception {
        minioClient = MinioClient.builder()
                .endpoint(this.minioProperty.getEndpoint())
                .credentials(this.minioProperty.getAccessKey(), this.minioProperty.getSecretKey())
                .build();
        LOGGER.info("minio client【{}】连接成功", this.minioProperty.getEndpoint());
        // 检测是否存在默认的文件存储bucket，不存在创建
        try {
            Boolean exist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(this.minioProperty.getBucketName()).build());
            // 过滤掉已经存在的buckets
            if (!exist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(this.minioProperty.getBucketName()).build());
                LOGGER.info("创建文件存储桶{}成功", this.minioProperty.getBucketName());
            }
        } catch (Exception e) {
            LOGGER.error("初始化minio存储桶异常：{}", e.getMessage());
        }
    }
}
