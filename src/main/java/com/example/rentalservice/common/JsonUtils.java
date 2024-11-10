package com.example.rentalservice.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
public class JsonUtils {

    public static String toJson(Object o) {
        byte[] buffers = new byte[0];

        SimpleModule module = new SimpleModule();
        module.addSerializer(MultipartFile.class, new MultipartFileSerializer());
        module.addSerializer(buffers.getClass(), new ByteArraySerializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            log.info("JsonUtils toJson - Exception: {}", e.getMessage());
        }
        return "";
    }

    public static <T> T fromJson(String json, Class<T> type) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            log.info("JsonUtils fromJson - Exception: {}", e.getMessage());
        }
        return null;
    }

    public static class MultipartFileSerializer extends StdSerializer<MultipartFile> {

        public MultipartFileSerializer() {
            this(null);
        }

        public MultipartFileSerializer(Class<MultipartFile> t) {
            super(t);
        }

        @Override
        public void serialize(
                MultipartFile value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException, JsonProcessingException {

            jgen.writeString("<binary>");
        }
    }

    public static class ByteArraySerializer extends StdSerializer<byte[]> {

        public ByteArraySerializer() {
            this(null);
        }

        public ByteArraySerializer(Class<byte[]> t) {
            super(t);
        }

        @Override
        public void serialize(
                byte[] value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException, JsonProcessingException {

            jgen.writeString("<binary>");
        }
    }
}