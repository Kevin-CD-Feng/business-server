package com.xtxk.recognition.prepare.service.parser.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class XCLRXRJsonEntity {
    @JsonProperty(value = "code")
    private Integer code;
    @JsonProperty(value = "data")
    private XCLRXRJsonData data;
}