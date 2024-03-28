package com.xtxk.cn.third.dto.hk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-22 18:39
 */
@Data
@NoArgsConstructor
public class EventObjectsThrownDetection implements Serializable {
    private static final long serialVersionUID = -3480636679241375219L;

    @JsonProperty("method")
    private String method;
    @JsonProperty("params")
    private ParamsDTO params;

    @Data
    @NoArgsConstructor
    public static class ParamsDTO implements Serializable {

        private static final long serialVersionUID = 6761045300677594008L;
        @JsonProperty("ability")
        private String ability;
        @JsonProperty("clients")
        private List<String> clients;
        @JsonProperty("events")
        private List<EventsDTO> events;
        @JsonProperty("sendTime")
        private String sendTime;
        @JsonProperty("uids")
        private List<String> uids;

        @Data
        @NoArgsConstructor
        public static class EventsDTO implements Serializable {
            private static final long serialVersionUID = 7723646274699932158L;
            @JsonProperty("data")
            private DataDTO data;
            @JsonProperty("eventId")
            private String eventId;
            @JsonProperty("eventType")
            private String eventType;
            @JsonProperty("happenTime")
            private String happenTime;
            @JsonProperty("srcIndex")
            private String srcIndex;
            @JsonProperty("srcParentIndex")
            private String srcParentIndex;
            @JsonProperty("srcType")
            private String srcType;
            @JsonProperty("status")
            private Integer status;
            @JsonProperty("timeout")
            private Integer timeout;

            @Data
            @NoArgsConstructor
            public static class DataDTO implements Serializable {
                private static final long serialVersionUID = -1022265760168449601L;
                @JsonProperty("ObjectsThrownDetection")
                private List<ObjectsThrownDetectionDTO> objectsThrownDetection;
                @JsonProperty("activePostCount")
                private Integer activePostCount;
                @JsonProperty("channelID")
                private Integer channelID;
                @JsonProperty("channelName")
                private String channelName;
                @JsonProperty("dataProcInterval")
                private String dataProcInterval;
                @JsonProperty("dataType")
                private String dataType;
                @JsonProperty("dataTime")
                private String dataTime;
                @JsonProperty("eventDescription")
                private String eventDescription;
                @JsonProperty("eventState")
                private String eventState;
                @JsonProperty("eventType")
                private String eventType;
                @JsonProperty("ipAddress")
                private String ipAddress;
                @JsonProperty("ipv6Address")
                private String ipv6Address;
                @JsonProperty("isDataRetransmission")
                private Boolean isDataRetransmission;
                @JsonProperty("macAddress")
                private String macAddress;
                @JsonProperty("picUploadInterval")
                private String picUploadInterval;
                @JsonProperty("portNo")
                private Integer portNo;
                @JsonProperty("protocol")
                private String protocol;
                @JsonProperty("recvTime")
                private String recvTime;
                @JsonProperty("sendTime")
                private String sendTime;
                @JsonProperty("targetAttrs")
                private TargetAttrsDTO targetAttrs;

                @Data
                @NoArgsConstructor
                public static class TargetAttrsDTO implements Serializable {
                    private static final long serialVersionUID = -1274651866922800554L;
                    //TODO 监控设备唯一id
                    @JsonProperty("cameraIndexCode")
                    private String cameraIndexCode;
                    @JsonProperty("deviceIndexCode")
                    private String deviceIndexCode;
                    @JsonProperty("imageServerCode")
                    private String imageServerCode;
                    @JsonProperty("picServerIndexCode")
                    private String picServerIndexCode;
                }

                @Data
                @NoArgsConstructor
                public static class ObjectsThrownDetectionDTO implements Serializable {
                    private static final long serialVersionUID = 7932907314648889033L;
                    @JsonProperty("Image")
                    private ImageDTO image;
                    @JsonProperty("RuleInfo")
                    private RuleInfoDTO ruleInfo;
                    @JsonProperty("endTime")
                    private String endTime;
                    @JsonProperty("startTime")
                    private String startTime;

                    @Data
                    @NoArgsConstructor
                    public static class ImageDTO implements Serializable {
                        private static final long serialVersionUID = 6672617941745998527L;
                        @JsonProperty("resourcesContent")
                        private String resourcesContent;
                        @JsonProperty("resourcesContentType")
                        private String resourcesContentType;
                    }

                    @Data
                    @NoArgsConstructor
                    public static class RuleInfoDTO implements Serializable {
                        private static final long serialVersionUID = -7284924877862564064L;
                        @JsonProperty("confidence")
                        private Integer confidence;
                        @JsonProperty("overlayPicEnabled")
                        private Boolean overlayPicEnabled;
                        @JsonProperty("ruleID")
                        private Integer ruleID;
                        @JsonProperty("ruleName")
                        private String ruleName;
                        @JsonProperty("sensitive")
                        private Integer sensitive;
                        @JsonProperty("uploadPicEnabled")
                        private Boolean uploadPicEnabled;
                    }
                }
            }
        }
    }
}
