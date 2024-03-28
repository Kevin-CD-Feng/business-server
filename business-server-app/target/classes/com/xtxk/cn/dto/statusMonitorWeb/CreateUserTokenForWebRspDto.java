package com.xtxk.cn.dto.statusMonitorWeb;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description CreateUserTokenForWebRspDto
 * @Date create in 2022/11/7 9:55
 */
public class CreateUserTokenForWebRspDto {

    private String responseCode;

    private String responseDesc;

    private Data data;

    public static class Data {

        private String tokenKey;

        private LoginInfo loginInfo;

        private String ipAddress;

        private String validTime;

        private String userName;

        private String userID;

        public String getTokenKey() {
            return tokenKey;
        }

        public void setTokenKey(String tokenKey) {
            this.tokenKey = tokenKey;
        }

        public LoginInfo getLoginInfo() {
            return loginInfo;
        }

        public void setLoginInfo(LoginInfo loginInfo) {
            this.loginInfo = loginInfo;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getValidTime() {
            return validTime;
        }

        public void setValidTime(String validTime) {
            this.validTime = validTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }
    }


    public static class LoginInfo {

        private String loginNodeName;

        private String loginIP;

        private String loginNodeID;

        public String getLoginNodeName() {
            return loginNodeName;
        }

        public void setLoginNodeName(String loginNodeName) {
            this.loginNodeName = loginNodeName;
        }

        public String getLoginIP() {
            return loginIP;
        }

        public void setLoginIP(String loginIP) {
            this.loginIP = loginIP;
        }

        public String getLoginNodeID() {
            return loginNodeID;
        }

        public void setLoginNodeID(String loginNodeID) {
            this.loginNodeID = loginNodeID;
        }
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
