package com.xtxk.cn.dto.personInfo;

import com.xtxk.cn.common.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class SearchInfoDTO extends PageParam implements Serializable {

    private static final long serialVersionUID = 8337742171813748068L;

    @ApiModelProperty(value = "关键字")
    private String keyWords;

    @ApiModelProperty(value = "人员类型 local_person:本地人员,outside_person:外来人员,manager_person:物业人员,impt_person:重点人员")
    private String personType;

    @ApiModelProperty(value = "所属片区,district_one:一区,district_two:二区,district_three:三区,district_four:四区,district_five:五区")
    private String district;

//    public String getKeyWords() {
//        return keyWords;
//    }
//
//    public void setKeyWords(String keyWords) {
//        this.keyWords = keyWords;
//    }
//
//    public String getPersonType() {
//        return personType;
//    }
//
//    public void setPersonType(String personType) {
//        this.personType = personType;
//    }
//
//    public String getDistrict() {
//        return district;
//    }
//
//    public void setDistrict(String district) {
//        this.district = district;
//    }
}
