package com.xtxk.cn.third.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
 * @description 区域湖小区人员mock
 * @author liulei
 * @date 2023-08-28 10:17
 */
@Data
public class MockUserInfo implements Serializable {
    private static final long serialVersionUID = -652445828849950296L;

    @ApiModelProperty(value = "小区人员id")
    private String personId;
    @ApiModelProperty(value = "小区人员id")
    private String personName;
    @ApiModelProperty(value = "身份证号")
    private String idNumber;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "出生日期")
    private Date birth;

}
