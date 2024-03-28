package com.xtxk.cn.dto.statusMonitorWeb;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description 根据部门查询用户
 * @Date create in 2022/11/7 15:45
 */
public class QueryUsersByConditionsRspDto {

    private Integer responseCode;

    private String responseDesc;

    private ReturnValue returnValue;

    public static class ReturnValue{

        private User[] list;

        private Integer totalCount;

        public User[] getList() {
            return list;
        }

        public void setList(User[] list) {
            this.list = list;
        }

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }
    }

    public static class User{

       private String userAccount;

       private String userID;

       private String userName;

       private String validBeginDate;

       private String validEndDate;

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getValidBeginDate() {
            return validBeginDate;
        }

        public void setValidBeginDate(String validBeginDate) {
            this.validBeginDate = validBeginDate;
        }

        public String getValidEndDate() {
            return validEndDate;
        }

        public void setValidEndDate(String validEndDate) {
            this.validEndDate = validEndDate;
        }
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public ReturnValue getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ReturnValue returnValue) {
        this.returnValue = returnValue;
    }
}
