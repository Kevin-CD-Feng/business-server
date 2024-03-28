package com.xtxk.cn.third.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-23 17:59
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "schedule.config")
public class ScheduleConfiguration implements Serializable {

    private static final long serialVersionUID = -757629027223805035L;

    private String userCron;
    private String carCron;
    private String blockCron;
    private String letCron;

}
