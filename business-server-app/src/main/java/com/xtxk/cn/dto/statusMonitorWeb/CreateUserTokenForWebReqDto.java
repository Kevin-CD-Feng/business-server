package com.xtxk.cn.dto.statusMonitorWeb;

import cn.hutool.core.util.RandomUtil;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description CreateUserTokenForWebReqDto
 * @Date create in 2022/11/7 9:55
 */
public class CreateUserTokenForWebReqDto {

    private String userID;

    private String password;

    private String ipAddress;

    private final int type = 1;

    private final String vcode = "aaaa";

    public CreateUserTokenForWebReqDto(String userID, String password) {
        this.userID = userID;
        this.password = password;
        String ipAddress = RandomUtil.randomString(8) + "-" + RandomUtil.randomString(4) + "-" +
                RandomUtil.randomString(4) + "-" + RandomUtil.randomString(4) + "-" +
                RandomUtil.randomString(12);
        this.ipAddress = ipAddress;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getType() {
        return type;
    }

    public String getVcode() {
        return vcode;
    }
}
