package com.xtxk.recognition.prepare.service.algAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AlgModel {
    //违规事件code
    private String eventCode;
    //算法code
    private String algCode;
    //违规持续时长,单位分钟
    private Integer duration;
    //违规时间段(未定义)
    private String vioTimeDuration;
    //告警间隔时长(单位分钟)
    private Integer interval;

    private String resourceId;

    private String resourceSipId;

    //坐标值，给算法
    private String webCoordinate;

    //AarmDefine定义的帧率
    private Float frameInterval;

    private String resourceName;

    //补充字段
    private Integer Order;

    //补充字段,这个是轮询设备的轮询时间
    private Integer loopTime;

    private String algorithmName;

    private Boolean isEnableFace;


    public String getName(){
        String[] splits ={this.getAlgCode().trim(),this.getEventCode().trim(),String.valueOf(this.getOrder()),this.getResourceId().trim()};
        return String.join("_",splits);
    }
}