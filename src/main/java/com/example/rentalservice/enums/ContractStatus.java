package com.example.rentalservice.enums;

public enum ContractStatus {
    DRAFT("Nháp"),
    EFFECTED("Có hiệu lực"),
    WANT_CANCEL("Muốn hủy hợp đồng"),
    CANCEL("Hủy hợp đồng")
    ;

    private String title;

    ContractStatus(String title) {
        this.title = title;
    }
}
