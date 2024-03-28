package com.xtxk.cn.utils.object;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.dto.Select2Item;
import com.xtxk.cn.enums.AlarmTypeEnum;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
public class EnumListUtil {

    private static final String ENUM_CLASSPATH = "java.lang.Enum";

    private static List<Field> commonHandle(Class<?> enumClass) {

        // 获取所有public方法
        Method[] methods = enumClass.getMethods();
        List<Field> fieldList = new ArrayList<>();

        // 1.通过get方法提取字段,避免get作为自定义方法的开头,建议使用'find'或其余命名
        Arrays.stream(methods).map(Method::getName)
                .filter(methodName -> methodName.startsWith("get")
                        && !"getDeclaringClass".equals(methodName)
                        && !"getClass".equals(methodName))
                .forEachOrdered(methodName -> {
                    try {
                        Field field = enumClass.getDeclaredField(StringUtils.uncapitalize(methodName.substring(3)));
                        if (!ObjectUtils.isEmpty(field)) {
                            fieldList.add(field);
                        }
                    } catch (NoSuchFieldException | SecurityException e) {
                        e.printStackTrace();
                    }
                });

        return fieldList;
    }

    // 枚举类转换List,元素为自定义实体类
    public static List<Select2Item> enumToSelect2List(Class<?> enumClass) {

        List<Select2Item> resultList = new ArrayList<>();
        if (!ENUM_CLASSPATH.equals(enumClass.getSuperclass().getCanonicalName())) {
            return resultList;
        }

        List<Field> fieldList = commonHandle(enumClass);

        // 2.将字段作为key,逐一把枚举值作为value存入list
        if (CollectionUtils.isEmpty(fieldList)) {
            return resultList;
        }

        Enum<?>[] enums = (Enum[]) enumClass.getEnumConstants();
        Select2Item select2Item = null;
        for (Enum<?> anEnum : enums) {

            select2Item = new Select2Item();
            for (Field field : fieldList) {

                field.setAccessible(true);
                try {
                    select2Item.setId(field.get(anEnum).toString());
                    select2Item.setText(anEnum.name());
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            // 将Select2Item添加到集合中
            resultList.add(select2Item);
        }
        return resultList;
    }

    // 枚举类转换List,元素为Map
    public static List<Map<String, Object>> enumToMapList(Class<?> enumClass) {

        List<Map<String, Object>> resultList = new ArrayList<>();
        if (!ENUM_CLASSPATH.equals(enumClass.getSuperclass().getCanonicalName())) {
            return resultList;
        }

        List<Field> fieldList = commonHandle(enumClass);

        // 2.将字段作为key,逐一把枚举值作为value存入list
        if (CollectionUtils.isEmpty(fieldList)) {
            return resultList;
        }

        Enum<?>[] enums = (Enum[]) enumClass.getEnumConstants();
        Map<String, Object> map = null;
        for (Enum<?> anEnum : enums) {

            map = new HashMap<>(fieldList.size());
            for (Field field : fieldList) {

                field.setAccessible(true);
                try {
                    // 向map集合添加字段名称和字段值
                    map.put(field.getName(), field.get(anEnum));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            // 将Map添加到集合中
            resultList.add(map);
        }
        return resultList;
    }

//    public static void main(String[] args) {
//
//        List<Map<String, Object>> maps = EnumListUtil.enumToMapList(AlarmTypeEnum.class);
//        maps.forEach(item->{
//            System.out.println(item.get("code")+":"+item.get("desc"));
//        });
//    }

    public static void main(String[] args) {
        String msg = "[{\"serviceCode\":\"MCY000003\",\"serviceName\":\"床位费\",\"discount_rate\":\"80%\",\"minoutdays\":\"0\",\"maxoutdays\":\"5\"}," +
                "{\"serviceCode\":\"MCY000002\",\"serviceName\":\"床位费\",\"discount_rate\":\"80%\",\"minoutdays\":\"0\",\"maxoutdays\":\"5\"}," +
            "{\"serviceCode\":\"MCY000001\",\"serviceName\":\"床位费\",\"discount_rate\":\"80%\",\"minoutdays\":\"0\",\"maxoutdays\":\"5\"}," +
            "{\"serviceCode\":\"MCY000005\",\"serviceName\":\"床位费\",\"discount_rate\":\"80%\",\"minoutdays\":\"0\",\"maxoutdays\":\"5\"}]";
        groupByCode(msg);
    }

    private static Map<String, List<?>> groupByCode(String msg) {
        JSONArray data = JSONUtil.parseArray(msg);
        Map<String, List<?>> dataMap = new HashMap<String, List<?>>();
        //数据分组算法
        for (int i = 0; i < data.size(); i++) {
            JSONObject json = (JSONObject)data.get(i);
            List<Object> tempList = (List<Object>) dataMap.get(json.get("serviceCode"));
            if(tempList == null) { //说明是第一次，则创建list
                tempList = new ArrayList<>();
                tempList.add(json);
                dataMap.put(json.get("serviceCode").toString(), tempList);//放入map中
            }else {
                tempList.add(json);
            }
        }
        //遍历
        for (String serviceCode : dataMap.keySet()) {
            System.out.println(serviceCode+":"+dataMap.get(serviceCode));
        }
        return dataMap;
    }
}
