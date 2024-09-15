package com.example.rentalservice.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Mapper {

    private final ObjectMapper objectMapper;

    public <T> T convertValue(Object input, Class<T> tClass) {
        try {
            return objectMapper.convertValue(input, new TypeReference<T>() {});
        } catch (Exception e) {
            log.error("Convert error");
            log.error(e.getMessage());
        }

        return null;
    }

}
