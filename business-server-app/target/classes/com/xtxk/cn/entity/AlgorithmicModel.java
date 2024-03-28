package com.xtxk.cn.entity;

import java.util.Date;

/**
 * @author: lost
 * @date: 2022-10-10 14:02:36
 * @description: 表名:t_algorithmic_model,描述:算法模型管理表
 */
public class AlgorithmicModel {
	/**
	 * 
	 */
	private Integer id;
	/**
	 * 算法名称(1:机动车不礼让行人检测;2:机动车乱停乱放检测3:主干道遛狗检测;4:遛狗未栓狗绳检测;5:高空抛物检测;6:围栏翻越检测;7:围栏隔墙递物检测)
	 */
	private String nameCode;

	private String name;
	/**
	 * 算法服务地址
	 */
	private String url;
	/**
	 * 请求参数范例
	 */
	private String parameterSample;
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

	public String getNameCode() {
		return nameCode;
	}

	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParameterSample() {
		return this.parameterSample;
	}

	public void setParameterSample(String parameterSample) {
		this.parameterSample = parameterSample;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
