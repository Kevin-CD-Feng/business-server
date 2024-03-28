package com.xtxk.recognition.prepare.service.parser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class XCLRXREventResult {

    @JsonProperty(value = "car_bbox")
    private List<Integer> car_bbox;
    @JsonProperty(value = "car_conf")
    private Float car_conf;
    @JsonProperty(value = "car_points")
    private List<List<Integer>> car_points;
    @JsonProperty(value = "person_bbox")
    private List<Integer> person_bbox;
    @JsonProperty(value = "person_conf")
    private Float person_conf;
    @JsonProperty(value = "person_points")
    private List<List<Integer>> person_points;
    @JsonProperty(value = "violation")
    private String violation;

    @JsonProperty(value = "conf")
    private Float conf;
    @JsonProperty(value = "bbox")
    private List<Integer> bbox;
    @JsonProperty(value = "label")
    private String label;
    @JsonProperty(value = "scale_bbox")
    private List<Float> scale_bbox;

    @JsonProperty(value = "plate")
    private String plate;

    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private List<Integer> plate_bbox;
}