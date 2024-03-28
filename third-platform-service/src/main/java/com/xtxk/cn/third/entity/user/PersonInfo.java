package com.xtxk.cn.third.entity.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class PersonInfo implements Serializable {

    private static final long serialVersionUID = 1336387409982998193L;
    private Integer personId;

    private String personName;

    private String personPhoto;

    private String gender;

    private Date birth;

    private String phone;

    private String idNumber;

    private String personType;

    private String district;

    private String address;

    private Date createTime;

    private Date updateTime;

    private String lakePersonId;


}