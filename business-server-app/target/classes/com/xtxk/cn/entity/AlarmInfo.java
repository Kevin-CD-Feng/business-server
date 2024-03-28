package com.xtxk.cn.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xtxk.cn.dto.alarmInfo.AlarmEventConvert;
import com.xtxk.cn.dto.alarmInfo.AlarmStatusConvert;
import com.xtxk.cn.dto.alarmInfo.DisposalTypeConvert;
import com.xtxk.cn.enums.AlarmResourceTypeEnum;
import com.xtxk.cn.enums.AlarmStatusEnum;
import com.xtxk.cn.enums.DisposalTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;


@ApiModel(description = "告警时间PO")
public class AlarmInfo {

	@ExcelIgnore
	@ApiModelProperty(value = "主键",example = "1")
	private Integer id;

	@ExcelIgnore
	@ApiModelProperty(value = "告警来源id")
	private String resourceId;

	@ExcelProperty(value = "告警来源名称",index = 0)
	@ColumnWidth(value = 15)
	@ApiModelProperty(value = "告警来源名称",example = "摄像头1")
	private String resourceName;

	@ExcelIgnore
	@ColumnWidth(value = 18)
	@ApiModelProperty(value = "告警事件",example = "1")
	private String event;

	@ExcelProperty(value = "告警事件",index = 1)
	@ColumnWidth(value = 18)
	@ApiModelProperty(value = "告警事件名称")
	private String eventName;


	@ExcelIgnore
	@ColumnWidth(value = 18)
	@ApiModelProperty(value = "告警类型")
	private String algCode;

	@ExcelIgnore
	@ColumnWidth(value = 18)
	@ApiModelProperty(value = "告警类型名称")
	private String algCodeName;


	@ExcelProperty(value = "抓拍时间",index = 2)
	@ColumnWidth(value = 18)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "抓拍时间",example = "2022-10-20 11:04:00")
	private Date catchTime;

	@ExcelIgnore
	@ApiModelProperty(value = "告警区域",example = "1")
	private String area;

	@ExcelProperty(value = "告警区域",index = 3)
	@ApiModelProperty(value = "告警区域名称",example = "区域1")
	private String areaName;

	@ExcelIgnore
	@ApiModelProperty(value = "抓拍图片路径（多图片,分隔）",example = "xxx")
	private String catchImageUrl;

	@ExcelIgnore
	@ApiModelProperty(value = "是否需实时处置（0:非实时处理；1：实时处理）",example = "1")
	private Integer processFlag;

	@ExcelIgnore
	@ApiModelProperty(value = "是否实时处置名称",example = "实时处理")
	private String processFlagName;

	@ExcelProperty(value = "事件状态",index = 4,converter = AlarmStatusConvert.class)
	@ApiModelProperty(value = "事件状态（1:未核实;2:未处置;3:处置中;4:已处置）",example = "2")
	private Integer status;

	@ExcelIgnore
	@ApiModelProperty(value = "事件状态名称",example = "未处置")
	private String statusName;

	@ExcelIgnore
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间",example = "2022-10-20 11:04:00")
	private Date createTime;

	@ExcelIgnore
	@ApiModelProperty(value = "创建人",example = "张三")
	private String createUser;

	@ExcelIgnore
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新时间",example = "2022-10-20 11:04:00")
	private Date updateTime;

	@ExcelProperty(value = "更新人",index = 5)
	@ApiModelProperty(value = "更新人",example = "张三")
	private String updateUser;

	@ExcelIgnore
	@ApiModelProperty(value = "删除状态（0未删除1已删除）",example = "0")
	private Integer deleted;

	@ExcelIgnore
	@ApiModelProperty(value = "告警来源类型（1：设备上报 2：人工上报）",example = "0")
	private String resourceType;

	@ExcelProperty(value = "告警来源类型",index = 6)
	@ApiModelProperty(value = "告警来源类型名称",example = "设备上报")
	private String resourceTypeName;

	@ExcelProperty(value = "经度",index = 7)
	@ApiModelProperty(value = "经度",example = "17.369")
	private BigDecimal longitude;

	@ExcelProperty(value = "纬度",index = 8)
	@ApiModelProperty(value = "纬度",example = "17.369")
	private BigDecimal latitude;

	@ExcelProperty(value = "事件描述",index = 9)
	@ApiModelProperty(value = "事件描述")
	private String eventDesc;

	@ExcelProperty(value = "告警类型",index = 10)
	@ApiModelProperty(value = "告警类型")
	private String alarmType;


	@ExcelIgnore
	@ApiModelProperty(value = "核实，none表示未核实，valid表示核实有效，invalid表示核实无效")
	private String auditStatus;

	@ExcelIgnore
	@ApiModelProperty(value = "录像文件url")
	private String recordUrl;

	@ExcelIgnore
	@ApiModelProperty(value = "海康cameraCode")
	private String hkCameraCode;

	/**
	 * 处理人关联id
	 */
	private Integer detailId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getCatchTime() {
		return catchTime;
	}

	public void setCatchTime(Date catchTime) {
		this.catchTime = catchTime;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCatchImageUrl() {
		return catchImageUrl;
	}

	public void setCatchImageUrl(String catchImageUrl) {
		this.catchImageUrl = catchImageUrl;
	}

	public Integer getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(Integer processFlag) {
		this.processFlag = processFlag;
	}

	public String getProcessFlagName() {
		return DisposalTypeEnum.queryDescByCode(getProcessFlag());
	}

	public void setProcessFlagName(String processFlagName) {
		this.processFlagName = processFlagName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return AlarmStatusEnum.queryDescByCode(getStatus());
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAlgCode() {
		return algCode;
	}

	public void setAlgCode(String algCode) {
		this.algCode = algCode;
	}

	public String getAlgCodeName() {
		return algCodeName;
	}

	public void setAlgCodeName(String algCodeName) {
		this.algCodeName = algCodeName;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getRecordUrl() {
		return recordUrl;
	}

	public void setRecordUrl(String recordUrl) {
		this.recordUrl = recordUrl;
	}

	public String getHkCameraCode() {
		return hkCameraCode;
	}

	public void setHkCameraCode(String hkCameraCode) {
		this.hkCameraCode = hkCameraCode;
	}
}
