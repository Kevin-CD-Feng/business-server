package com.xtxk.cn.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class PersonInfo implements Serializable {

    private static final long serialVersionUID = 950610979324044509L;
    /**
     * 主键
     */
    @ExcelIgnore
    private Integer personId;
    /**
     * 人员名称
     */
    @ExcelProperty(value = "姓名", index = 0)
    @ColumnWidth(20)
    @ApiModelProperty(value = "姓名", required = true)
    private String personName;
    /**
     * 照片
     */
    @ExcelIgnore
    private String personPhoto;
    /**
     * 性别 1男 2女
     */
    @ExcelIgnore
    private String gender;

    @ExcelProperty(value = "性别", index = 1)
    @ApiModelProperty(value = "性别", required = true)
    private String genderName;
    /**
     * 出生日期
     */
    @DateTimeFormat("yyyy/MM/dd")
    @ExcelProperty(value = "出生日期", index = 2)
    @ColumnWidth(15)
    private Date birth;
    /**
     * 电话号码
     */
    @ExcelProperty(value = "联系电话", index = 3)
    @ColumnWidth(15)
    @ApiModelProperty(value = "联系电话", required = true)
    private String phone;
    /**
     * 身份证号码
     */
    @ExcelProperty(value = "身份证号", index = 4)
    @ColumnWidth(20)
    @ApiModelProperty(value = "身份证号", required = true)
    private String idNumber;
    /**
     * 人员类型 1本地人员 2外来人员 3物业人员
     */

    @ExcelIgnore
    @ColumnWidth(15)
    private String personType;

    @ExcelProperty(value = "人员类型", index = 5)
    @ApiModelProperty(value = "人员类型", required = true)
    private String personTypeName;
    /**
     * 所属区域 1一区 2 二区 3三区 4四区 5五区
     */
    @ExcelIgnore
    @ColumnWidth(15)
    private String district;

    @ExcelProperty(value = "所属片区", index = 6)
    @ApiModelProperty(value = "所属片区", required = true)
    private String districtName;
    /**
     * 居住地址
     */
    @ExcelProperty(value = "居住位置", index = 7)
    @ColumnWidth(50)
    private String address;
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

    @ExcelIgnore
    @ApiModelProperty(value = "年龄", required = true)
    private Integer age;

    @ApiModelProperty(value = "违规次数", required = true)
    private Integer vioCnt;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(String personPhoto) {
        this.personPhoto = personPhoto;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public Integer getVioCnt() {
        return vioCnt;
    }

    public void setVioCnt(Integer vioCnt) {
        this.vioCnt = vioCnt;
    }
}

