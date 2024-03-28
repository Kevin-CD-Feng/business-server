package com.xtxk.recognition.prepare.service.parser.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CrawlResp {
    @JsonProperty(value = "success")
    private Boolean success;
    @JsonProperty(value = "data")
    private List<CrawlDataItem> data;
}