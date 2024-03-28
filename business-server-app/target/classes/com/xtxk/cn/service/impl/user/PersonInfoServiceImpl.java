package com.xtxk.cn.service.impl.user;

import com.alibaba.excel.util.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtxk.cn.dto.carInfo.CarFlowRspDto;
import com.xtxk.cn.dto.carInfo.CarFlowVo;
import com.xtxk.cn.dto.carInfo.ResourceCntWithTypeDto;
import com.xtxk.cn.dto.personInfo.*;
import com.xtxk.cn.entity.DicItem;
import com.xtxk.cn.entity.PersonInfo;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.mapper.PersonInfoMapper;
import com.xtxk.cn.service.impl.dic.DicItemHandler;
import com.xtxk.cn.service.user.PersonInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonInfoServiceImpl.class);

    @Value("${fileServicePath}")
    private String fileServicePath;

    @Value("${uploadFileBasePath}")
    private String uploadFileBasePath;

    @Value("${photoPath}")
    private String photoPath;
    /**
     * 用户头像相对路径
     */
    private final String uploadImagePath = "person";

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Autowired
    private DicItemHandler dicItemHandler;

    @Override
    public PageInfo findPersonInfoList(SearchInfoDTO searchInfoDTO) {
        PageHelper.startPage(searchInfoDTO.getPageNo(), searchInfoDTO.getPageSize());
        List<PersonInfo> list = personInfoMapper.queryPage(searchInfoDTO);
        List<Integer> vioIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setGenderName(mapping.get(e.getGender()));
                e.setDistrictName(mapping.get(e.getDistrict()));
                e.setPersonTypeName(mapping.get(e.getPersonType()));
                e.setPersonPhoto(photoPath+e.getPersonName()+".jpg");
                vioIds.add(e.getPersonId());
            });
        }
        if(vioIds.size()>0) {
            List<String> collect = vioIds.stream().map(String::valueOf).collect(Collectors.toList());
            List<VioModelCntDto> vioModelCntDtos = this.personInfoMapper.queryVioCnt(2, collect);
            Map<String, VioModelCntDto> collect1 = vioModelCntDtos.stream().collect(Collectors.toMap(VioModelCntDto::getModelId, it -> it));
            list.forEach(e->{
                if(collect1.containsKey(String.valueOf(e.getPersonId()))){
                    e.setVioCnt(collect1.get(String.valueOf(e.getPersonId())).getCnt());
                }
            });
        }
        return new PageInfo(list);
    }

    @Override
    public Boolean savePersonInfo(PersonInfoDto personInfoDto) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setPersonName(personInfoDto.getName());
        personInfo.setPersonPhoto(personInfoDto.getFacePicture());
        personInfo.setGender(personInfoDto.getGender());
        personInfo.setBirth(personInfoDto.getBirthDay());
        personInfo.setPhone(personInfoDto.getTelePhone());
        String idCard = personInfoDto.getIdCard();
        personInfo.setIdNumber(StringUtils.isBlank(idCard) ? idCard : idCard.toUpperCase());
        personInfo.setPersonType(personInfoDto.getPersonType());
        personInfo.setDistrict(personInfoDto.getDistrict());
        personInfo.setAddress(personInfoDto.getAddress());
        String idNumber = personInfo.getIdNumber();
        if (personInfoDto.getId() != null) {
            Integer personId = personInfoDto.getId();
            PersonInfo pi = personInfoMapper.queryById(personId);
            if (StringUtils.isNotBlank(idNumber) && (!idNumber.equals(pi.getIdNumber()))) {
                PersonInfo po = personInfoMapper.getByIdNumber(idNumber);
                if (null != po) {
                    LOGGER.info("该身份证号已经存在，身份证号:{}", idNumber);
                    throw new ServiceException(ErrorCode.ID_NUMBER_EXIST);
                }
            }
            personInfo.setPersonId(personId);
            personInfo.setUpdateTime(new Date());
            return personInfoMapper.update(personInfo) > 0;
        } else {
            personInfo.setCreateTime(new Date());
            personInfo.setUpdateTime(new Date());

            if (StringUtils.isNotBlank(idNumber)) {
                PersonInfo pi = personInfoMapper.getByIdNumber(idNumber);
                if (null != pi) {
                    LOGGER.info("该身份证号已经存在，身份证号:{}", idNumber);
                    throw new ServiceException(ErrorCode.ID_NUMBER_EXIST);
                }
            }
            return personInfoMapper.insert(personInfo) > 0;
        }
    }

    @Override
    public Boolean deleteById(Integer Id) {
        return personInfoMapper.deleteById(Id) > 0;
    }

    @Override
    public Boolean insertBatch(List<PersonInfo> list) {
        Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
        Map<String, String> tmpMapping = new HashMap<>();
        Set<Map.Entry<String, String>> entries = mapping.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            tmpMapping.put(entry.getValue(), entry.getKey());
        }
        for (PersonInfo info : list) {
            info.setUpdateTime(new Date());
            info.setCreateTime(new Date());
            info.setGender(tmpMapping.get(info.getGenderName()));
            info.setPersonType(tmpMapping.get(info.getPersonTypeName()));
            info.setDistrict(tmpMapping.get(info.getDistrictName()));
        }
        return personInfoMapper.insertBatch(list) > 0;
    }

    @Override
    public Boolean deleteBatch(String ids) {
        List<Integer> list = Stream.of(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return personInfoMapper.deleteBatch(list) > 0;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String imgUrl = upload(file, file.getOriginalFilename());
        return imgUrl;
    }

    @Override
    public CarFlowRspDto queryCarFlow(Date beginDate, Date endDate) {
        CarFlowRspDto rspDto = new CarFlowRspDto();
        rspDto.setTotalOut(0);
        rspDto.setTotalIn(0);
        rspDto.setData(new ArrayList<>());
        List<CarFlowVo> dtos1 = this.personInfoMapper.queryCarFlow(beginDate, endDate);
        dtos1.stream().forEach(it->{
            rspDto.getData().add(it);
            if(it.getDirection().equals("out")){
                Integer out =rspDto.getTotalOut()+Optional.ofNullable(it.getCnt()).orElse(0);
                rspDto.setTotalOut(out);
            }else{
                Integer in =rspDto.getTotalIn()+Optional.ofNullable(it.getCnt()).orElse(0);
                rspDto.setTotalIn(in);
            }
        });
        return rspDto;
    }

    @Override
    public PersonFlowRspDto queryPersonFlow(Date beginDate, Date endDate) {
        PersonFlowRspDto rspDto = new PersonFlowRspDto();
        rspDto.setTotalOut(0);
        rspDto.setTotalIn(0);
        rspDto.setData(new ArrayList<>());
        List<PersonFlowVo> dtos1 = this.personInfoMapper.queryPersonFlow(beginDate, endDate);
        dtos1.stream().forEach(it->{
            rspDto.getData().add(it);
            if(it.getDirection().equals("out")){
                Integer out =rspDto.getTotalOut()+Optional.ofNullable(it.getCnt()).orElse(0);
                rspDto.setTotalOut(out);
            }else{
                Integer in =rspDto.getTotalIn()+Optional.ofNullable(it.getCnt()).orElse(0);
                rspDto.setTotalIn(in);
            }
        });
        return rspDto;
    }


    private String upload(MultipartFile file, String fileName) {
        if (null == file) {
            LOGGER.error("file is null");
            throw new ServiceException(ErrorCode.PARAMS_ERROR);
        }
        if (null == fileName) {
            LOGGER.error("fileName is not exist,fileName:{}", fileName);
            throw new ServiceException(ErrorCode.PARAMS_ERROR);
        }
        String newFileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
        String fullPath = uploadFileBasePath + File.separator + uploadImagePath + File.separator + newFileName;
        File dest = new File(fullPath);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            LOGGER.error("", e);
        }
        return fileServicePath + uploadImagePath + File.separator + newFileName;
    }


    @Override
    public List<VioInfoDto> queryViolationInfoByTargetId(String targetId, Integer type) {
        List<VioInfoDto> vioInfoDtos = this.personInfoMapper.queryViolationInfoByTargetId(targetId, type);
        List<DicItem> alarm_events = this.dicItemHandler.getAllDicItemByDicCode("alarm_event");
        List<DicItem> alarm_types = this.dicItemHandler.getAllDicItemByDicCode("alarm_type");
        //将上面两个转换为map
        Map<String, DicItem> collect = alarm_events.stream().collect(Collectors.toMap(DicItem::getItemCode, it -> it));
        Map<String, DicItem> collect1 = alarm_types.stream().collect(Collectors.toMap(DicItem::getItemCode, it -> it));
        vioInfoDtos.forEach(it->{
            if(collect.containsKey(it.getEventCode())){
                DicItem dicItem = collect.get(it.getEventCode());
                it.setEventCodeDesc(dicItem.getItemName());
                String parentCode = dicItem.getItemParentCode();
                if(collect1.containsKey(parentCode)){
                    DicItem dicItem1 = collect1.get(parentCode);
                    it.setAlgCode(dicItem1.getItemCode());
                    it.setAlgCodeDesc(dicItem1.getItemName());
                }
            }
            it.setCatchTimeStr(DateUtils.format(it.getCatchTime(),DateUtils.DATE_FORMAT_19_FORWARD_SLASH));
        });
        return vioInfoDtos;
    }

    @Override
    public List<ResourceCntWithTypeDto> queryPersonCntWithType() {
        List<ResourceCntWithTypeDto> resourceCntWithTypeDtos = this.personInfoMapper.queryPersonCntWithType();
        List<DicItem> car_type = this.dicItemHandler.getAllDicItemByDicCode("person_type");
        Map<String, DicItem> collect = car_type.stream().collect(Collectors.toMap(DicItem::getItemCode, it -> it));
        List<String> exists = new ArrayList<>();
        resourceCntWithTypeDtos.forEach(it->{
            if(collect.containsKey(it.getCategory())){
                DicItem dicItem = collect.get(it.getCategory());
                it.setCategoryDesc(dicItem.getItemName());
                exists.add(it.getCategory());
            }
        });

        collect.forEach((k,v)->{
            if(!exists.contains(k)){
                ResourceCntWithTypeDto dto = new ResourceCntWithTypeDto();
                dto.setCnt(0);
                dto.setCategory(v.getItemCode());
                dto.setCategoryDesc(v.getItemName());
                resourceCntWithTypeDtos.add(dto);
            }
        });
        return resourceCntWithTypeDtos;
    }
}