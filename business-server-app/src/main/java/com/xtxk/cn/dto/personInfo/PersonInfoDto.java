package com.xtxk.cn.dto.personInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author HW
 */
@ApiModel("人员档案")
public class PersonInfoDto implements Serializable {

    private static final long serialVersionUID = 2132222563827959972L;

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank(message = "姓名不能为空")
    @Size(max = 12, message = "名称最大长度12字符")
    @Pattern(regexp = "^[\u4e00-\u9fa5A-Za-z]{2,12}$",message = "姓名格式错误，支持中文、英文，最大长度12字符")
    private String name;

    @ApiModelProperty(value = "性别", required = true)
    @NotNull(message = "性别不能为空!")
    private String gender;

    @JsonFormat(locale = "yyyy-MM-dd")
    @ApiModelProperty("出生日期")
    private Date birthDay;

    @ApiModelProperty("联系电话")
    @Size(max = 11, message = "联系电话最大长度11字符!")
    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "联系电话格式错误，支持数字，最大长度11字符")
    private String telePhone;

    @ApiModelProperty("身份证")
    @Size(max = 18, message = "身份证最大长度18字符!")
    @Pattern(regexp = "^\\d{15}|\\d{17}[0-9xX]{1}$",message = "身份证号格式错误，支持数字，英文字母最大长度18字符")
    private String idCard;

    @ApiModelProperty(value = "人员类型", required = true)
    @NotBlank(message = "人员类型不能为空!")
    private String personType;

    @ApiModelProperty(value = "所属片区", required = true)
    @NotBlank(message = "所属片区不能为空!")
    private String district;

    @ApiModelProperty("居住地址")
    @Size(max = 30, message = "居住地址最大长度30字符!")
    private String address;

    @ApiModelProperty("人脸图片")
    private String facePicture;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public String getFacePicture() {
        return facePicture;
    }

    public void setFacePicture(String facePicture) {
        this.facePicture = facePicture;
    }
}
