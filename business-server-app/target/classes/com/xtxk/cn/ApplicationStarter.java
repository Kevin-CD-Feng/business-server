package com.xtxk.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lost
 * @Description 启动类
 * @Date create in 2022-10-9 9:10:34
 */

@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableRetry(proxyTargetClass = true)
public class ApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }
}