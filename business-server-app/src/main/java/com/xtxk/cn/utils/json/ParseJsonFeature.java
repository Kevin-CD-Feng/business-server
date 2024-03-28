package com.xtxk.cn.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Iterator;
import java.util.Map;

/***
 * @description 事件特征值解析
 * @author liulei
 * @date 2023-06-28 21:06
 */
public class ParseJsonFeature {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParseJsonFeature.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String lastKey;

    public Boolean parseFeature(String json, String feature) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return processNode(jsonNode, feature);
        } catch (JsonProcessingException e) {
            LOGGER.error(" parseFeature 解析算法识别数据异常：" + e.getMessage());
            throw new RuntimeException("parseFeature 解析算法识别数据异常：" + e.getMessage());
        }
    }

    private boolean processNode(JsonNode node, String parseKeys) {
        boolean flag = false;
        if (node.isContainerNode()) {
            if (node.isArray()) {
                Iterator<JsonNode> iterable = node.iterator();
                while (iterable.hasNext()) {
                    JsonNode jsonNode = iterable.next();
                    flag = processNode(jsonNode, parseKeys);
                    if (flag) {
                        break;
                    }
                }
            } else {
                Iterator<Map.Entry<String, JsonNode>> filedsIterator = node.fields();
                Map.Entry<String, JsonNode> filed;
                while (filedsIterator.hasNext()) {
                    filed = filedsIterator.next();
                    lastKey = filed.getKey();
                    flag = processNode(filed.getValue(), parseKeys);
                    if (flag) {
                        break;
                    }
                }
            }
        } else if (node.isNull()) {
            String featureKey = lastKey + ":" + String.valueOf(node).replaceAll("\"", "");
            //System.out.println("featureKey ===>" + featureKey + " parseKeys: " + parseKeys);
            if (parseKeys.equals(featureKey)) {
                flag = true;
            }
        } else {
            String featureKey = lastKey + ":" + String.valueOf(node).replaceAll("\"", "");
            //System.out.println("featureKey ===>" + featureKey + " parseKeys: " + parseKeys);
            if (parseKeys.equals(featureKey)) {
                flag = true;
            }
        }
        return flag;
    }
}
