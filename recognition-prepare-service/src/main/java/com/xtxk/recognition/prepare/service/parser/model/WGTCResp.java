package com.xtxk.recognition.prepare.service.parser.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WGTCResp implements Serializable {
    @JsonProperty(value = "code")
    private Integer code;
    @JsonProperty(value = "data")
    private List<WGTCDataItem> data;
}