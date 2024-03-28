package com.xtxk.recognition.prepare.service.parser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WalkDogResp {
    @JsonProperty(value = "success")
    private Boolean success;
    @JsonProperty(value = "data")
    private List<WalkDogDataItem> data;
    @JsonIgnore
    private Object params;
}