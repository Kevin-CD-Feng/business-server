package com.xtxk.recognition.prepare.service.parser.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WalkDogDataItem {
    @JsonProperty(value = "bbox")
    private List<Integer> bbox;
    @JsonProperty(value = "conf")
    private Float conf;
    @JsonProperty(value = "label")
    private String label;
    @JsonProperty(value = "face_bbox")
    private List<Integer> faceBbox;
    @JsonProperty(value = "face_conf")
    private Float faceConf;
}