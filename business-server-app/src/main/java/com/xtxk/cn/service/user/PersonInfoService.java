package com.xtxk.cn.service.user;

import com.github.pagehelper.PageInfo;
import com.xtxk.cn.dto.carInfo.CarFlowRspDto;
import com.xtxk.cn.dto.carInfo.ResourceCntWithTypeDto;
import com.xtxk.cn.dto.personInfo.PersonFlowRspDto;
import com.xtxk.cn.dto.personInfo.PersonInfoDto;
import com.xtxk.cn.dto.personInfo.SearchInfoDTO;
import com.xtxk.cn.dto.personInfo.VioInfoDto;
import com.xtxk.cn.entity.PersonInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


public interface PersonInfoService {


    /***
     * 分页检索人员档案信息
     * @param searchInfoDTO
     * @return
     */
    PageInfo findPersonInfoList(SearchInfoDTO searchInfoDTO);

    /***
     * 保存人员档案信息
     * @param personInfoDto
     * @return
     */
    Boolean savePersonInfo(PersonInfoDto personInfoDto);

    /***
     * 删除人员档案信息
     * @param Id 用户id
     * @return
     */
    Boolean deleteById(Integer Id);

    /***
     * 批量插入数据
     * @param list
     * @return
     */
    Boolean insertBatch(List<PersonInfo> list);

    /***
     * 批量删除
     * @param ids
     * @return
     */
    Boolean deleteBatch(String ids);

    /**
     * 上传照片
     * @param file
     * @return
     */
    String uploadFile(MultipartFile file);

    /**
     * 上传照片
     * @param beginDate
     * @param endDate
     * @return
     */
    CarFlowRspDto queryCarFlow(Date beginDate, Date endDate);

    /**
     * 上传照片
     * @param beginDate
     * @param endDate
     * @return
     */
    PersonFlowRspDto queryPersonFlow( Date beginDate,Date endDate);


    List<VioInfoDto> queryViolationInfoByTargetId(String targetId,  Integer type);

    List<ResourceCntWithTypeDto> queryPersonCntWithType();
}
