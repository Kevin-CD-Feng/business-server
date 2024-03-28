package com.xtxk.cn.third.entity.user;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 人流量
 * @author Administrator
 */
@Data
public class TPersonFlow implements Serializable {

    private static final long serialVersionUID = -5464641486708573202L;

    private String flowId;

    private String direction;

    private String gateCode;

    private String userId;

    private Date catchTime;

    private String imgPath;

    private Date createTime;

}