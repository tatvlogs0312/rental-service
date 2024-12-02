package com.example.rentalservice.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum BillStatusEnum {
    DRAFT("Nháp"), PENDING("Chờ thanh toán"), PAYED("Đã thanh toán");

    private String title;

    private static final Map<String, BillStatusEnum> map = new HashMap<>();

    static {
        Arrays.stream(values())
                .forEach(m -> map.put(m.name(), m));
    }

    BillStatusEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static BillStatusEnum from(String code) {
        return map.getOrDefault(code, DRAFT);
    }
}
