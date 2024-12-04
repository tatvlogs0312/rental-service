package com.example.rentalservice.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum ContractStatusEnum {
    DRAFT("Nháp"),
    PENDING_SIGNED("Chờ ký"),
    SIGNED("Đã ký"),
    END("Kết thúc hợp đồng"),
    CANCEL("Hủy"),
    REJECT("Từ chối");

    private String title;

    private static final Map<String, ContractStatusEnum> map = new HashMap<>();

    static {
        Arrays.stream(values())
                .forEach(m -> map.put(m.name(), m));
    }

    ContractStatusEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static ContractStatusEnum from(String code) {
        return map.getOrDefault(code, DRAFT);
    }
}
