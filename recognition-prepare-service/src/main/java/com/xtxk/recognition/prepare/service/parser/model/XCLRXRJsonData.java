package com.xtxk.recognition.prepare.service.parser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class XCLRXRJsonData {
    @JsonProperty(value = "event_result")
    private List<XCLRXREventResult> event_result;
    @JsonIgnore
    private Object model_result;
}