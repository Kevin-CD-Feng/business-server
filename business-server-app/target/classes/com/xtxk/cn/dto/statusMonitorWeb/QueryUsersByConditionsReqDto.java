package com.xtxk.cn.dto.statusMonitorWeb;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description 根据部门查询用户
 * @Date create in 2022/11/7 15:42
 */
public class QueryUsersByConditionsReqDto {

    private final String endPoint = "com.xtxk.orgstructure.grpc.api.OrgnizationStructureService/QueryUsersByConditions";

    private String nodeId;

    private Param param;

    private final String service = "organization-structure-service:8080";

    private final String serviceKey = "StatusMonitor";

    private String token ;

    public QueryUsersByConditionsReqDto(Param param) {
        this.param = param;
    }

    public static class Param{

        private Integer beginIndex;

        private Integer count;

        private String departmentID;

        private String resouceSign;

        public Param(Integer beginIndex, Integer count, String resouceSign) {
            this.beginIndex = beginIndex;
            this.count = count;
            this.resouceSign = resouceSign;
        }

        public Integer getBeginIndex() {
            return beginIndex;
        }

        public void setBeginIndex(Integer beginIndex) {
            this.beginIndex = beginIndex;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getDepartmentID() {
            return departmentID;
        }

        public void setDepartmentID(String departmentID) {
            this.departmentID = departmentID;
        }

        public String getResouceSign() {
            return resouceSign;
        }

        public void setResouceSign(String resouceSign) {
            this.resouceSign = resouceSign;
        }
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    public String getService() {
        return service;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
