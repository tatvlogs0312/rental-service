package com.example.rentalservice.common;

import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class RepositoryUtils {
    public static void setQueryParameters(Map<String, Object> queryParams, Query query) {
        if (MapUtils.isNotEmpty(queryParams)) {
            for (Entry<String, Object> entry : queryParams.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                query.setParameter(key, value);
            }
        }
    }

    public static <T> T setValueForField(Class<T> c, Object object) {
        if (object == null) {
            return null;
        } else if (StringUtils.equals(LocalDate.class.getName(), c.getName())) {
            object = ((Date) object).toLocalDate();
        } else if (StringUtils.equals(LocalDateTime.class.getName(), c.getName())) {
            object = ((Timestamp) object).toLocalDateTime();
        } else if (StringUtils.equals(BigDecimal.class.getName(), c.getName())) {
            object = ((BigDecimal) object).abs();
        }
        return (T) object;
    }

    public static Pageable createPageable(Integer page, Integer size) {
        if (Objects.isNull(page)) {
            page = 0;
        }

        if (Objects.isNull(size)) {
            size = 5;
        }

        return PageRequest.of(page, size);
    }
}
