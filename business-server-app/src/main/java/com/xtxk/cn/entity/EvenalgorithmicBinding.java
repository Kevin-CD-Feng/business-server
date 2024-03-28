package com.xtxk.cn.entity;

import java.util.Date;

/**
 * @author: lost
 * @date: 2022-10-10 14:02:36
 * @description: 表名:t_event_algorithmic_binding,描述:事件算法绑定表
 */
public class EvenalgorithmicBinding {
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
	 * 算法名称(1:机动车不礼让行人检测;2:机动车乱停乱放检测3:主干道遛狗检测;4:遛狗未栓狗绳检测;5:高空抛物检测;6:围栏翻越检测;7:围栏隔墙递物检测)
	 */
	private String algorithmicNameCode;

	private String algorithmicName;

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

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getAlgorithmicNameCode() {
		return algorithmicNameCode;
	}

	public void setAlgorithmicNameCode(String algorithmicNameCode) {
		this.algorithmicNameCode = algorithmicNameCode;
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

	public String getAlgorithmicName() {
		return algorithmicName;
	}

	public void setAlgorithmicName(String algorithmicName) {
		this.algorithmicName = algorithmicName;
	}
}
