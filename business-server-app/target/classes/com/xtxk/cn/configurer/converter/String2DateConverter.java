package com.xtxk.cn.configurer.converter;

import com.alibaba.excel.util.DateUtils;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class String2DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if(Strings.isEmpty(source)){
            return null;
        }else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return format.parse(source);
            } catch (ParseException e) {
                return null;
            }
        }
    }
}