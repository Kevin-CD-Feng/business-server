package com.xtxk.cn.configurer.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioPropertyConfigurer {

    @Bean
    @ConfigurationProperties(prefix = "minio")
    public MinioProperty buildMinioProperty(){
        return new MinioProperty();
    }
}