package com.xtxk.recognition.prepare.service.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


public class AlarmPolicyConfiguration {
	/**
	 * 
	 */
	private Integer id;
	/**
	 * 设备id
	 */
	private String resourceId;
	/**
	 * 告警事件（1:机动车不礼让行人;2:机动车乱停乱放;3:主干道遛狗;4:遛狗未栓狗绳;5:高空抛物;6:围栏翻越;7:围栏隔墙递物）
	 */
	private String eventCode;
	/**
	 * 违规时长
	 */
	private String violationLength;
	/**
	 * 区域
	 */
	private String webCoordinate;
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
	 * 违规间隔时长
	 */
	private Integer violationInterval;

	/**
	 * 违规持续时长
	 */
	private Integer violationDuration;


	private Date violationBeginDate;

	private Date violationEndDate;

	private String algCode;

	/**
	 * 检测频率（秒/每帧）0222
	 */
	private Float frameInterval;


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getViolationLength() {
		return this.violationLength;
	}

	public void setViolationLength(String violationLength) {
		this.violationLength = violationLength;
	}

	public String getWebCoordinate() {
		return this.webCoordinate;
	}

	public void setWebCoordinate(String webCoordinate) {
		this.webCoordinate = webCoordinate;
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

	public Integer getViolationInterval() {
		return violationInterval;
	}

	public void setViolationInterval(Integer violationInterval) {
		this.violationInterval = violationInterval;
	}

	public Integer getViolationDuration() {
		return violationDuration;
	}

	public void setViolationDuration(Integer violationDuration) {
		this.violationDuration = violationDuration;
	}

	public Date getViolationBeginDate() {
		return violationBeginDate;
	}

	public void setViolationBeginDate(Date violationBeginDate) {
		this.violationBeginDate = violationBeginDate;
	}

	public Date getViolationEndDate() {
		return violationEndDate;
	}

	public void setViolationEndDate(Date violationEndDate) {
		this.violationEndDate = violationEndDate;
	}

	public String getAlgCode() {
		return algCode;
	}

	public void setAlgCode(String algCode) {
		this.algCode = algCode;
	}

	public Float getFrameInterval() {
		return frameInterval;
	}

	public void setFrameInterval(Float frameInterval) {
		this.frameInterval = frameInterval;
	}
}
