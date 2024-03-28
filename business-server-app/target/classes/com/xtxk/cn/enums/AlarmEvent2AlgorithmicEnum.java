package com.xtxk.cn.enums;

import cn.hutool.json.JSONUtil;

import java.util.*;

public enum AlarmEvent2AlgorithmicEnum {
    //算法名称(1:机动车不礼让行人检测;2:机动车乱停乱放检测3:主干道遛狗检测;4:遛狗未栓狗绳检测;5:高空抛物检测;6:围栏翻越检测;7:围栏隔墙递物检测)
    JDCBLRXR(1, 1,"机动车不礼让行人检测"),

    JDCLTLF(2,2, "机动车乱停乱放检测"),

    ZGDLG(3,3,"主干道遛狗检测"),

    LGWSGS(4,4, "遛狗未栓狗绳检测"),

    GKPW(5, 5,"高空抛物检测"),

    WLFY(6,6, "围栏翻越检测"),

    WLGQDW(7,7, "围栏隔墙递物检测");

    /**
     * eventCode
     */
    private Integer eventCode;

    /**
     * code
     */
    private Integer algorithmicCode;

    /**
     * 描述
     */
    private String desc;

    AlarmEvent2AlgorithmicEnum(Integer eventCode, Integer algorithmicCode, String desc) {
        this.eventCode = eventCode;
        this.algorithmicCode = algorithmicCode;
        this.desc = desc;
    }

    public static List<Map<String,Object>> getValueByEventCode(Integer eventCode) {
        AlarmEvent2AlgorithmicEnum[] alarmEvent2AlgorithmicEnums = values();
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (AlarmEvent2AlgorithmicEnum alarmEvent2AlgorithmicEnum : alarmEvent2AlgorithmicEnums) {
            if (alarmEvent2AlgorithmicEnum.eventCode.equals(eventCode)) {
                Map<String,Object> map = new HashMap<>();
                map.put("code",alarmEvent2AlgorithmicEnum.algorithmicCode);
                map.put("desc",alarmEvent2AlgorithmicEnum.desc);
                mapList.add(map);
            }
        }
        return mapList;
    }

    public static Integer getAlgorithmicCodeByEventCode(Integer eventCode){
        Optional<AlarmEvent2AlgorithmicEnum> first = Arrays.stream(AlarmEvent2AlgorithmicEnum.values()).filter(temp -> temp.eventCode.equals(eventCode)).findFirst();
        if (first.isPresent()) {
            return first.get().algorithmicCode;
        }
        return 0;
    }

    public static Integer getEventCodeByAlgorithmicCode(Integer algorithmicCode){
        Optional<AlarmEvent2AlgorithmicEnum> first = Arrays.stream(AlarmEvent2AlgorithmicEnum.values()).filter(temp -> temp.algorithmicCode.equals(algorithmicCode)).findFirst();
        if (first.isPresent()) {
            return first.get().eventCode;
        }
        return 0;
    }

    public static void main(String[] args) {

    }

}
