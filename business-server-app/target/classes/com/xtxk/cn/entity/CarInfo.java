package com.xtxk.cn.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * (CarInfo)实体类
 *
 * @author HW
 */
public class CarInfo implements Serializable {

    private static final long serialVersionUID = 901873159357983782L;
    /**
     * 主键
     */
    @ExcelIgnore
    private Integer carId;
    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号", required = true)
    @NotBlank(message = "车牌号不能为空!")
    @Size(max = 12, message = "车牌号最大长度12字符!")
    @Pattern(regexp = "^[\u4e00-\u9fa5][A-Z][A-Z0-9]{5,6}$",message = "车牌号格式错误，支持中文、英文，最大长度12字符")
    private String carNumber;
    /**
     * 车主姓名
     */
    @ApiModelProperty(value = "车主姓名", required = true)
    @NotBlank(message = "车主姓名不能为空!")
    @Size(max = 12, message = "车主姓名最大长度12字符!")
    @ExcelProperty(value = "车主姓名", index = 1)
    @ColumnWidth(20)
    @Pattern(regexp = "^[\u4e00-\u9fa5A-Za-z]{2,12}$",message = "车主姓名格式错误，支持中文、英文，最大长度12字符")
    private String carOwnerName;
    /**
     * 电话号码
     */
    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank(message = "联系电话不能为空!")
    @Size(max = 11, message = "联系电话最大长度11字符!")
    @ExcelProperty(value = "联系电话", index = 2)
    @ColumnWidth(20)
    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "车主手机号格式错误，支持数字，最大长度11字符")
    private String carOwnerPhone;
    /**
     * 车辆类型
     */
    @ApiModelProperty(value = "车辆类型", required = true)
    @NotBlank(message = "车辆类型不能为空!")
    @ExcelIgnore
    @ColumnWidth(15)
    private String carType;

    @ExcelProperty(value = "车辆类型", index = 3)
    private String carTypeName;

    /**
     * 创建时间
     */
    @ExcelIgnore
    private Date createTime;
    /**
     * 更新时间
     */
    @ExcelIgnore
    private Date updateTime;


    @ApiModelProperty(value = "违规次数", required = true)
    private Integer vioCnt;


    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public String getCarOwnerPhone() {
        return carOwnerPhone;
    }

    public void setCarOwnerPhone(String carOwnerPhone) {
        this.carOwnerPhone = carOwnerPhone;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public Integer getVioCnt() {
        return vioCnt;
    }

    public void setVioCnt(Integer vioCnt) {
        this.vioCnt = vioCnt;
    }
}

