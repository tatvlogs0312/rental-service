package com.example.rentalservice.enums;

public enum BookingEnum {
    BOOKED("Đã đặt"),
    ACCEPT("Chấp nhận cho thuê"),
    REJECT("Từ chối cho thuê"),
    CANCEL("Hủy đặt"),
    ;

    private String title;

    BookingEnum(String title) {
        this.title = title;
    }
}
