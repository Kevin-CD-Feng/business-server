package com.xtxk.recognition.prepare.service.parser.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WGTCDataItem implements Serializable {
    @JsonProperty(value = "bbox")
    private List<Integer> bbox;
    @JsonProperty(value = "conf")
    private Float conf;
    @JsonProperty(value = "label")
    private String label;
    @JsonProperty(value = "carNo")
    private String carNo;
}