package com.xtxk.cn.service.access;


import com.xtxk.cn.dto.accessRecord.AccessCarInRecordResult;
import com.xtxk.cn.dto.accessRecord.AccessCarOutRecordResult;
import com.xtxk.cn.dto.accessRecord.AccessPersonRecordResult;

public interface AccessRecordService {

    /**
     * 人员出入
     *
     * @return
     */
    AccessPersonRecordResult getAccessPersonRecordResult();

    /**
     * 车辆入
     *
     * @param date
     * @return
     */
    AccessCarInRecordResult getAccessCarInRecordResult(String date);

    /**
     * 车辆出
     *
     * @param date
     * @return
     */
    AccessCarOutRecordResult getAccessCarOutRecordResult(String date);

}
