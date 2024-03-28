package com.xtxk.cn.third.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.third.configuration.DataLakeConfiguration;
import com.xtxk.cn.third.dto.user.MockUserInfo;
import com.xtxk.cn.third.entity.user.OiPersonnelInformation;
import com.xtxk.cn.third.entity.user.PersonInfo;
import com.xtxk.cn.third.enums.user.SexEnums;
import com.xtxk.cn.third.mapper.TPersonFlowMapper;
import com.xtxk.cn.third.service.UserService;
import com.xtxk.cn.third.common.PageReqDto;
import com.xtxk.cn.third.mapper.OiPersonnelInformationMapper;
import com.xtxk.cn.third.mapper.PersonInfoMapper;
import com.xtxk.cn.third.service.BatchService;
import com.xtxk.cn.third.service.DateLakeService;
import com.xtxk.cn.third.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cursor.Cursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.xtxk.cn.third.util.RequestHeaderUtils.*;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-22 10:38
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private DataLakeConfiguration dataLakeConfiguration;
    @Autowired
    private DateLakeService dateLakeService;
    @Autowired
    private BatchService batchService;
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private PersonInfoMapper personInfoMapper;
    @Autowired
    private OiPersonnelInformationMapper oiPersonnelInformationMapper;
    @Autowired
    private TPersonFlowMapper tPersonFlowMapper;

    /***
     * 拉取小区人员数据
     * @param page
     * @param size
     * @return
     */
    @Override
    public Boolean pullUserInfo(Integer page, Integer size) {
        String url = appendUrl(dataLakeConfiguration.getUserUrl(), page, size);
        logger.info("==========================>拉取小区用户请求地址url: " + url);
        String token = dateLakeService.getToken();
        String response = httpUtils.post(url, token);
        if (StringUtils.isBlank(response)) {
            return false;
        }
        JSONObject jsonObject = JSONUtil.toBean(response, JSONObject.class);
        JSONArray array = parseJsonData(jsonObject);
        List<OiPersonnelInformation> employeeList = JSONUtil.toList(array, OiPersonnelInformation.class);
        batchService.batchSave(employeeList, OiPersonnelInformationMapper.class, OiPersonnelInformationMapper::insertSelective);
        PageReqDto pageInfo = parsePage(jsonObject);
        if (pageInfo.isPage()) {
            Integer currentPage = pageInfo.getPageNo();
            Integer limit = pageInfo.getPageSize();
            Integer total = pageInfo.getTotal();
            logger.info(String.format(" pullUserInfo 当前页: %s , 条数 : %s ; 当前总数：%s ; 总条数: %s", currentPage, limit, currentPage * limit, total));
            pullUserInfo(currentPage, limit);
        }
        return true;
    }


    /***
     * 同步小区人员数据
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean syncUserInfo() {
        List<OiPersonnelInformation> personList = new ArrayList<>();
        List<PersonInfo> personInfoList = new ArrayList<>();

        Cursor<OiPersonnelInformation> oilStreamCursor =  oiPersonnelInformationMapper.streamQueryList();
        oilStreamCursor.forEach(o -> personList.add(o));

        Cursor<PersonInfo> influxCursor = personInfoMapper.streamQueryList();
        influxCursor.forEach(p->personInfoList.add(p));

        List<OiPersonnelInformation> oilUseList = personList.stream().filter(p -> p.getBSFLAG() == 1).collect(Collectors.toList());
        List<OiPersonnelInformation> oilNoUseList = personList.stream().filter(p -> p.getBSFLAG() != 1).collect(Collectors.toList());
        Map<String, PersonInfo> personMap = personInfoList.stream().collect(Collectors.toMap(p -> p.getLakePersonId(),
                Function.identity(), (key1, key2) -> key2));

        List<PersonInfo> addList = new ArrayList<>();
        List<PersonInfo> updateList = new ArrayList<>();
        List<Integer> removeList = new ArrayList<>();
        for (OiPersonnelInformation info : oilUseList) {
            String personKey = info.getPERSON_ID();
            PersonInfo personInfo = personMap.get(personKey);
            if (ObjectUtil.isNotNull(personInfo)) {
                PersonInfo fo = updatePerson(info, personInfo);
                if (ObjectUtil.isNotNull(fo)) {
                    updateList.add(fo);
                }
            } else {
                addList.add(addPerson(info));
            }
        }

        for (OiPersonnelInformation info : oilNoUseList) {
            PersonInfo personInfo = personMap.get(info.getPERSON_ID());
            if (ObjectUtil.isNotNull(personInfo)) {
                removeList.add(personInfo.getPersonId());
            }
        }

        if (addList!=null && addList.size()>0) {
            logger.info("批量插入人员数量：" + addList.size());
            List<List<PersonInfo>> splitList = ListUtil.partition(addList, 100);
            for (List<PersonInfo> list : splitList) {
                logger.info("批量插入人员数据 ===> {}", JSONUtil.toJsonStr(list));
                personInfoMapper.insertBatchSave(list);
            }
        }
        if (updateList!=null && updateList.size()>0) {
            logger.info("批量更新人员数量：" + updateList.size());
            List<List<PersonInfo>> splitList = ListUtil.partition(updateList, 100);
            for (List<PersonInfo> list : splitList) {
                logger.info("批量更新人员数据 ===> {}", JSONUtil.toJsonStr(list));
                personInfoMapper.updateBatchSave(list);
            }
        }
        if (removeList!=null && removeList.size()>0) {
            logger.info("批量删除人员数量：" + removeList.size());
            List<List<Integer>> splitList = ListUtil.partition(removeList, 100);
            for (List<Integer> list : splitList) {
                logger.info("批量删除人员数据 ===> {}", JSONUtil.toJsonStr(list));
                personInfoMapper.deleteBatchSave(list);
            }
        }
        return true;
    }

    /***
     * mock
     * @param mockUserInfo
     * @return
     */
    @Override
    public Boolean mockUserInfo(List<MockUserInfo> mockUserInfo) {
        if (ArrayUtil.isEmpty(mockUserInfo)) {
            return false;
        }
        List<PersonInfo> list = new ArrayList<>();
        for (MockUserInfo userInfo : mockUserInfo) {
            PersonInfo info = new PersonInfo();
            info.setPersonName(userInfo.getPersonName());
            info.setBirth(userInfo.getBirth());
            info.setGender(String.valueOf(SexEnums.getCode(userInfo.getSex())));
            info.setBirth(userInfo.getBirth());
            info.setIdNumber(userInfo.getIdNumber());
            info.setLakePersonId(userInfo.getPersonId());
            info.setCreateTime(new Date());
            info.setUpdateTime(new Date());
            list.add(info);
        }
        return personInfoMapper.insertBatchSave(list) > 0;
    }

    /***
     * 添加小区人员
     * @param info
     * @return
     */
    private PersonInfo addPerson(OiPersonnelInformation info) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setPersonName(info.getNAME());
        personInfo.setGender(info.getSEX());
        personInfo.setBirth(info.getBIRTHDAY());
        personInfo.setIdNumber(info.getID_NUMBER());
        personInfo.setLakePersonId(info.getPERSON_ID());
        personInfo.setCreateTime(new Date());
        personInfo.setUpdateTime(new Date());
        return personInfo;
    }

    /***
     * 更新数据
     * @param oilInfo
     * @param info
     * @return
     */
    private PersonInfo updatePerson(OiPersonnelInformation oilInfo, PersonInfo info) {
        // TODO 调整
        String value = oilInfo.getNAME() + oilInfo.getSEX() + oilInfo.getID_NUMBER();
        String oldValue = info.getPersonName() + info.getGender() + info.getIdNumber();
        if (value.equals(oldValue)) {
            return null;
        }
        info.setPersonName(oilInfo.getNAME());
        info.setGender(oilInfo.getSEX());
        info.setBirth(oilInfo.getBIRTHDAY());
        info.setIdNumber(oilInfo.getID_NUMBER());
        info.setLakePersonId(oilInfo.getPERSON_ID());
        info.setUpdateTime(new Date());
        return info;
    }

}
