package com.xtxk.test;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-09 15:20
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringTest.class)
public class SpringTest {
    @Test
    public void test() {

        String timeStr = "2021-01-18T16:49:29+08:00";
        DateTime dateTime =DateUtil.parse(timeStr);
        Date date = new Date(dateTime.getTime());
        System.out.println(DateUtil.formatDateTime(date));
    }
}
