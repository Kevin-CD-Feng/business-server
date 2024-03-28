package com.xtxk.cn.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/***
 * @description 算法数据属性值解析
 * @author liulei
 * @date 2023/6/5 10:30
 */
public class ParseJson {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseJson.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String lastKey;

    public List<Integer> parseJson(String json, List<String> parseKeys) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            List<Integer> data = new ArrayList<>();
            processNode(jsonNode, parseKeys, data);
            return data;
        } catch (JsonProcessingException e) {
            LOGGER.error(" parseJson 解析算法识别数据异常：" + e.getMessage());
            throw new RuntimeException("parseJson 解析算法识别数据异常：" + e.getMessage());
        }
    }

    private void processNode(JsonNode node, List<String> parseKeys, List<Integer> result) {
        if (node.isContainerNode()) {
            if (node.isArray()) {
                Iterator<JsonNode> iterable = node.iterator();
                while (iterable.hasNext()) {
                    JsonNode jsonNode = iterable.next();
                    processNode(jsonNode, parseKeys, result);
                }
            } else {
                Iterator<Map.Entry<String, JsonNode>> filedsIterator = node.fields();
                Map.Entry<String, JsonNode> filed;
                while (filedsIterator.hasNext()) {
                    filed = filedsIterator.next();
                    lastKey = filed.getKey();
                    processNode(filed.getValue(), parseKeys, result);
                }
            }
        } else if (node.isNull()) {
            if (parseKeys.contains(lastKey)) {
                if (node!=null) {
                    result.add(Integer.valueOf(String.valueOf(node)));
                }
            }
        } else {
            if (parseKeys.contains(lastKey)) {
                if (node!=null) {
                    result.add(Integer.valueOf(String.valueOf(node)));
                }
            }
        }
    }

}
