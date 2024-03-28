package com.xtxk.cn.configurer.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author lost
 * @description jackson配置
 * @date create in 2022-10-9 9:10:34
 */
@Configuration
public class JacksonConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonConfigurer.class);

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();
        //Deserializer
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringWithoutSpaceDeserializer(String.class));
        mapper.registerModule(module);
        //添加nullSerializer处理
        mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new JacksonNullSerializerModifier()));
        //日期处理
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //时区处理
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return converter;
    }

    /**
     * 校验参数
     * 去除参数空格
     * xss
     * sql注入
     */
    private class StringWithoutSpaceDeserializer extends StdDeserializer<String> {

        protected StringWithoutSpaceDeserializer(Class<String> clazz) {
            super(clazz);
        }

        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String currentName = jsonParser.getCurrentName();
            String originalText = jsonParser.getText();
            if (originalText == null) {
                return null;
            }
            //去除空格
            originalText = originalText.trim();
            //xss
            //originalText = JsoupUtil.clean(originalText);
            return originalText;
        }
    }

    /**
     * nullSerializer处理器
     */
    private class JacksonNullSerializerModifier extends BeanSerializerModifier {
        //arrayNull处理
        private NullArrayJsonSerializer arrayNull = new NullArrayJsonSerializer();
        //stringNull处理
        private NullStringJsonSerializer stringNull = new NullStringJsonSerializer();
        //numberNull处理
        private NullNumberJsonSerializer numberNull = new NullNumberJsonSerializer();
        //booleanNull处理
        private NullBooleanJsonSerializer booleanNull = new NullBooleanJsonSerializer();
        //DateNull处理
        private NullDateJsonSerializer dateNull = new NullDateJsonSerializer();
        //objectNull处理
        private NullObjectJsonSerializer objectNull = new NullObjectJsonSerializer();

        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
            for (int i = 0; i < beanProperties.size(); i++) {
                BeanPropertyWriter writer = beanProperties.get(i);
                /*判断字段的类型，如果是数组或集合则注册nullSerializer*/
                if (isArrayType(writer)) {
                    //数组
                    writer.assignNullSerializer(this.arrayNull);
                } else if (isStringType(writer)) {
                    //字符串
                    writer.assignNullSerializer(this.stringNull);
                } else if (isNumberType(writer)) {
                    //整形
                    writer.assignNullSerializer(this.numberNull);
                } else if (isBooleanType(writer)) {
                    //布尔
                    writer.assignNullSerializer(this.booleanNull);
                } else if (isDateType(writer)) {
                    //date
                    writer.assignNullSerializer(this.dateNull);
                } else {
                    writer.assignNullSerializer(this.objectNull);
                }
            }
            return beanProperties;
        }
    }

    /**
     * 是否是数组
     */
    private static boolean isArrayType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是String
     */
    private static boolean isStringType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是数值类型
     */
    private static boolean isNumberType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return Number.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是boolean
     */
    private static boolean isBooleanType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.equals(Boolean.class);
    }

    /**
     * 是否是date
     *
     * @param writer
     * @return
     */
    private static boolean isDateType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.equals(Date.class);
    }

    /**
     * 处理数组集合类型的null值
     */
    private static class NullArrayJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeEndArray();
        }
    }

    /**
     * 处理字符串类型的null值
     */
    private static class NullStringJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString("");
        }
    }

    /**
     * 处理数值类型的null值
     */
    private static class NullNumberJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(0);
        }
    }

    /**
     * 处理boolean类型的null值
     */
    private static class NullBooleanJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeBoolean(false);
        }
    }

    /**
     * 处理date类型的null值
     */
    private static class NullDateJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString("");
        }
    }

    /**
     * 处理实体对象类型的null值
     */
    private static class NullObjectJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeEndObject();
        }
    }
}