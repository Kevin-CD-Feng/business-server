package com.xtxk.cn.entity;

import java.util.Date;

/**
 * @author: lost
 * @date: 2022-10-10 14:02:36
 * @description: 表名:t_alarm_define,描述:告警事件定义表
 */
public class AlarmDefine {
	/**
	 * 
	 */
	private Integer id;
	/**
	 * 告警事件（1:机动车不礼让行人;2:机动车乱停乱放;3:主干道遛狗;4:遛狗未栓狗绳;5:高空抛物;6:围栏翻越;7:围栏隔墙递物）
	 */
	private String eventCode;

	private String eventName;
	/**
	 * 告警类型（1:机动车不礼让行人;2:机动车乱停乱放;3:不文明养犬;4:高空抛物;5:周界入侵）
	 */
	private String typeCode;
	/**
	 * 处置（0：非实时处置；1：实时处置）
	 */
	private Integer disposalFlag;
	/**
	 * 检测频率（帧/秒）
	 */
	private Integer intervals;
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
	private Integer deleted = 0;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getDisposalFlag() {
		return this.disposalFlag;
	}

	public void setDisposalFlag(Integer disposalFlag) {
		this.disposalFlag = disposalFlag;
	}

	public Integer getIntervals() {
		return this.intervals;
	}

	public void setIntervals(Integer intervals) {
		this.intervals = intervals;
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

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
}
