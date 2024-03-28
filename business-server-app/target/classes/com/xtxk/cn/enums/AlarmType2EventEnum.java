package com.xtxk.cn.enums;

import java.util.*;

public enum AlarmType2EventEnum {

    JDCBLRXR(1, 1,"机动车不礼让行人"),

    JDCLTLF(2,2, "机动车乱停乱放"),

    ZGDLG(3,3,"主干道遛狗"),

    LGWSGS(3,4, "遛狗未栓狗绳"),

    GKPW(4, 5,"高空抛物"),

    WLFY(5,6, "围栏翻越"),

    WLGQDW(5,7, "围栏隔墙递物");

    /**
     * type
     */
    private Integer type;

    /**
     * code
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;


    AlarmType2EventEnum(Integer type, Integer code, String desc) {
        this.type = type;
        this.code = code;
        this.desc = desc;
    }

    public static List<Map<Integer,String>> getValueByType(Integer type) {
        AlarmType2EventEnum[] alarmType2Events = values();
        List<Map<Integer,String>> mapList = new ArrayList<>();
        for (AlarmType2EventEnum alarmType2Event : alarmType2Events) {
            if (alarmType2Event.type.equals(type)) {
                Map<Integer,String> map = new HashMap<>();
                map.put(alarmType2Event.code,alarmType2Event.desc);
                mapList.add(map);
            }
        }
        return mapList;
    }

//    public static String getTypeByCode(String code){
//        Optional<AlarmType2EventEnum> first = Arrays.stream(AlarmType2EventEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
//        if (first.isPresent()) {
//            return first.get().type;
//        }
//        return 0;
//    }
//
//    public static String getDescByCode(Integer code){
//        Optional<AlarmType2EventEnum> first = Arrays.stream(AlarmType2EventEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
//        if (first.isPresent()) {
//            return first.get().desc;
//        }
//        return null;
//    }

}
