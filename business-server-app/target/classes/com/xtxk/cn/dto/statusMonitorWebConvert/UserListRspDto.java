package com.xtxk.cn.dto.statusMonitorWebConvert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description 查询物业用户返回
 * @Date create in 2022/11/7 16:02
 */
@ApiModel(description = "查询物业用户返回")
public class UserListRspDto {

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static class User {

        @ApiModelProperty(value = "用户账户")
        private String userAccount;

        @ApiModelProperty(value = "用户id")
        private String id;

        @ApiModelProperty(value = "用户名")
        private String name;

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
