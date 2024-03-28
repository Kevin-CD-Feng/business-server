package com.xtxk.cn.third;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-21 11:31
 */
@EnableScheduling
@SpringBootApplication
public class ThirdApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThirdApplication.class, args);
    }
}
