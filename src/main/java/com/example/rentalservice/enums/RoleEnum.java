package com.example.rentalservice.enums;

public enum RoleEnum {
    ADMIN("Admin hệ thống"),
    TENANT("Người thuê trọ"),
    LESSOR("Chủ trọ");

    private String title;

    RoleEnum(String title) {
        this.title = title;
    }
}
