package com.xtxk.recognition.prepare.service.enums;

/**
 * @author lost
 * @description 返回code枚举
 * @date 2022-10-9 9:10:34
 */
public enum ErrorCode {
    /**
     * 成功
     */
    SUCCESS("000000", "成功"),
    /**
     *
     */
    ERROR("FFFFFF", "错误"),
    /**
     *
     */
    PARAMS_ERROR("000001", "参数异常"),
    /**
     *
     */
    PARAMS_INLEGAL("000002", "参数不合法"),
    /**
     *
     */
    SERVICE_PARAM_ERROR("100000", "业务数据异常"),
    FILE_SIZE_LIMIT("100001", "文件大小超过默认值"),
    FILE_Extension_Exception("100002", "文件格式不正确"),
    MONITOR_DEVICE_ALREADY_EXIST("100003","监控设备已经存在"),
    IP_FORMAT_ERROR("100004","ip格式不正确"),
    DEVICE_ID_NULL("100005","设备id不能为空"),
    DEVICE_ID_NOT_EXIST("100006","设备id不存在"),
    BUILDING_ID_NULL("100007","楼栋建筑id不能为空"),
    BUILDING_ID_NOT_EXIST("100008","楼栋建筑id不存在"),
    GATEMACHINE_ID_NULL("100009","门禁闸机id不能为空"),
    GATEMACHINE_ID_NOT_EXIST("100010","门禁闸机id不存在"),
    EXCEL_MODE_ERROR("100011","excel模板错误"),
    ID_NUMBER_EXIST("100012","身份证号已经存在"),
    DEVICE_ID_EMPTY("100013","设备id不存在"),
    IMAGE_ERROR("100014","图片错误"),
    WRITE_TO_LOCAL_ERROR("100014","文件写入本地异常"),
    /**
     * 告警事件错误
     */
    ALARM_EVENT_ESIXT("200001", "告警事件已存在"),
    ALARM_EVENT_TYPE_NOT_MATCH("200002", "告警事件与类型不匹配"),
    ALARM_DATA_COVERT_ERROR("200003", "告警事件数据转换错误"),
    ALARM_EVENT_NOT_ESIXT("200004", "告警事件不存在"),
    UPDATE_ALARM_EVENT_ERROR("200005", "告警事件更新失败"),
    EVENT_BINDING_DEVICE_ERROR("200006", "告警事件已绑定设备，请先解绑"),
    EVENT_BINDING_ALGORITHMIC_ERROR("200007", "告警事件已绑定算法，请先解绑"),
    DELETE_EVENT_ERROR("200008", "删除失败"),
    EVENT_LIST_NOT_EXIST("200009", "告警列表不存在，请先添加"),
    ALARM_EVENT_NAME_NOT_EXIST("200010", "告警编码对应事件名称不存在"),

    /**
     * 算法模型错误
     */
    ALGORITHMIC_MODEL_EXIST("300001", "算法模型已存在"),
    ALGORITHMIC_MODEL_NOT_EXIST("300002", "算法模型不存在"),
    ALGORITHMIC_MODEL_CODE_ERROR("300003", "参数算法模型code错误"),
    UPDATE_ALGORITHMIC_MODEL_ERROR("300004", "算法模型更新失败"),
    ALGORITHMIC_BINDING_EVENT_ERROR("300005", "算法模型已绑定事件，请先解绑"),
    DELETE_ALGORITHMIC_MODEL_ERROR("300006", "删除失败"),

    /** 事件算法绑定错误 */
    ALARM_EVENT_2_ALGORITHMIC_ERROR("400001","告警事件对应算法错误"),
    ALARM_EVENT_2_ALGORITHMIC_NOT_EXIST("400002","事件算法绑定不存在"),
    ALARM_EVENT_2_ALGORITHMIC_ADD_ERROR("400003","事件算法绑定新增失败"),
    UPDATE_ALARM_EVENT_2_ALGORITHMIC_ERROR("400004","事件算法绑定更新失败"),
    DELETE_ALARM_EVENT_2_ALGORITHMIC_ERROR("400005","删除失败"),
    ALARM_EVENT_2_ALGORITHMIC_EXIST("400006","事件算法绑定已存在"),

    /** 告警策略配置错误 */
    ALARM_POLICY_CONFIGURATION_RESOURCEID_ERROR("500001","设备resourceId不一致错误"),
    ALARM_POLICY_CONFIGURATION_DATA_NOT_FOUND("500002","告警策略配置数据为空"),
    ALARM_POLICY_CONFIGURATION_DATA_REPEAT("500003","告警策略配置数据重复"),

    /** 告警中心错误 */
    QUERY_AREA_ALARM_COUNT_ERROR_BY_TYPE("600001","时间类型参数异常"),
    QUERY_ALARM_TYPE_RADIO_ERROR_BY_TYPE("600002","时间类型参数异常"),
    QUERY_ALARM_COUNT_ERROR_BY_TYPE("600003","时间类型参数异常"),
    ALARM_INFO_IS_EMPTY("600004","暂无告警信息"),
    ALARM_INFO_STATUS_ERROR("600005","告警信息状态错误"),
    DEAL_PERSON_EMPTY("600006","告警信息状态错误"),
    AREA_INFO_NOT_EXIST("600007","告警信息状态错误"),

    /** 字典模块错误 */
    DIC_EMPTY("700001","字典元素不存在"),
    DIC_CODE_EXIST("700002","字典元素code已存在"),
    DIC__ITEM_CODE_EXIST("700003","字典项code已存在"),
    DIC_ITEM_HAS_RELATION("700003","字典项删除存在下级关系，请勿删除");

    /**
     * 状态code
     */
    private String code;
    /**
     * 状态描述
     */
    private String desc;

    ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}