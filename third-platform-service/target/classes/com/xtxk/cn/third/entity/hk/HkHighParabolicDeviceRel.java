package com.xtxk.cn.third.entity.hk;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class HkHighParabolicDeviceRel implements Serializable {

    private static final long serialVersionUID = -8004607350330005500L;

    private String hkDeviceId;

    private String deviceId;

    private String deviceName;

    private Date createTime;

    private Date updateTime;

}