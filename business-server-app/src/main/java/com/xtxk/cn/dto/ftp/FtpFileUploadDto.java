package com.xtxk.cn.dto.ftp;

import java.io.InputStream;

/**
 * @Author daiqing(daiqing @ xingtu.com)
 * @Description ftp文件上传实体类
 * @Date create in 2021/5/18 20:09
 * @Modified by
 */

public class FtpFileUploadDto {

    private String fileName;

    private InputStream inputStream;

    private String type;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
