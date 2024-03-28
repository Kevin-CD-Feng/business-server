package com.xtxk.recognition.prepare.service.parser.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CrawlDataItem {
    @JsonProperty(value = "person_bbox")
    private List<Integer> personBbox;
    @JsonProperty(value = "person_conf")
    private Float personConf;

    @JsonProperty(value = "face_bbox")
    private List<Integer> faceBbox;

    @JsonProperty(value = "face_conf")
    private Float faceConf;
}