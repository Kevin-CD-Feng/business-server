package com.xtxk.recognition.prepare.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AlgormParser {
    public static <T> T parse(String input, Class<T> target) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(input, target);
    }
}