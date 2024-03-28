package com.xtxk.cn.dto.statusMonitorWebConvert;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description TODO
 * @Date create in 2022/11/7 17:26
 */
public class UserPageRspDto {

    @ApiModelProperty(value = "数据列表")
    private List<User> users;

    /**
     * 总数
     */
    @ApiModelProperty(value = "总记录数")
    private long count;

    /**
     * 分页总数
     */
    @ApiModelProperty(value = "总分页数")
    private int pageCount;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static class User {

        @ApiModelProperty(value = "用户账户")
        private String accountName;

        @ApiModelProperty(value = "用户id")
        private String id;

        @ApiModelProperty(value = "用户名")
        private String username;

        @ApiModelProperty(value = "开始有效期")
        private String createTime;

        @ApiModelProperty(value = "结束有效期")
        private String validEndDate;

        //todo 权限字段
        private String[] permission;

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getValidEndDate() {
            return validEndDate;
        }

        public void setValidEndDate(String validEndDate) {
            this.validEndDate = validEndDate;
        }

        public String[] getPermission() {
            return permission;
        }

        public void setPermission(String[] permission) {
            this.permission = permission;
        }
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }


}
