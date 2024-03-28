package com.xtxk.cn.utils.ip;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IPUtil
 *
 * @author chenzhi
 * @date 2022/10/18 10:02
 * @description
 */
public class IPUtil {

    /**
     * 判断ip格式
     * @param ip
     * @return
     */
    public static boolean checkIp(String ip){
        String patternString="(([0,1]?\\d?\\d|2[0-4]\\d|25[0-5])\\.){3}([0,1]?\\d?\\d|2[0-4]\\d|25[0-5])";
        Pattern pattern=Pattern.compile(patternString);
        Matcher matcher=pattern.matcher(ip);
        return matcher.matches();
    }
}
