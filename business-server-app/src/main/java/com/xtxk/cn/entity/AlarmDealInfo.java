package com.xtxk.cn.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author: lost
 * @date: 2022-10-10 14:02:36
 * @description: 表名:t_alarm_deal_info,描述:
 */
public class AlarmDealInfo {
	/**
	 * 
	 */
	private Integer id;
	/**
	 * 告警主键id
	 */
	private Integer alarmId;
	/**
	 * 处理人员账号
	 */
	private String dealPerson;
	/**
	 * 下发时间
	 */
	private Date issuedTime;
	/**
	 * 处理完成时间
	 */
	private Date completionTime;
	/**
	 * 处理后图片
	 */
	private String completionUrl;
	/**
	 * 处理结果描述
	 */
	private String dealDesc;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新人
	 */
	private String updateUser;
	/**
	 * 0未删除1已删除
	 */
	private Integer deleted;

	/**
	 * 处理人员名字
	 */
	private String dealPersonName;

	private Integer dealStatus;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAlarmId() {
		return this.alarmId;
	}

	public void setAlarmId(Integer alarmId) {
		this.alarmId = alarmId;
	}

	public String getDealPerson() {
		return this.dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	public Date getIssuedTime() {
		return this.issuedTime;
	}

	public void setIssuedTime(Date issuedTime) {
		this.issuedTime = issuedTime;
	}

	public Date getCompletionTime() {
		return this.completionTime;
	}

	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
	}

	public String getCompletionUrl() {
		return this.completionUrl;
	}

	public void setCompletionUrl(String completionUrl) {
		this.completionUrl = completionUrl;
	}

	public String getDealDesc() {
		return this.dealDesc;
	}

	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getDealPersonName() {
		return dealPersonName;
	}

	public void setDealPersonName(String dealPersonName) {
		this.dealPersonName = dealPersonName;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}
}
