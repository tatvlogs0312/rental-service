package com.example.rentalservice.enums;

public enum PaperEnum {

    FRONT("CCCD/MNND mặt trước"),
    BACK("CCCD/MNND mặt sau"),
    SELFIE("Khuôn mặt người dùng"),
    ;

    private String title;

    PaperEnum(String title) {
        this.title = title;
    }
}
