package com.example.rentalservice.enums;

public enum MalfunctionWarningEnum {
    PENDING("Chờ giải quyết"),
    COMPLETE("Đã giải quyết"),
    CANCEL("Hủy");
    ;

    private String title;

    MalfunctionWarningEnum(String title) {
        this.title = title;
    }
}
