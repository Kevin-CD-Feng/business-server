package com.xtxk.cn.entity;


/**
 * @author: lost
 * @date: 2022-10-10 14:02:35
 * @description: 表名:t_device_info,描述:设备信息表
 */
public class DeviceInfo {
	/**
	 * 
	 */
	private Integer id;
	/**
	 * 资源id
	 */
	private String resourceId;
	/**
	 * 设备名称
	 */
	private String name;

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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
