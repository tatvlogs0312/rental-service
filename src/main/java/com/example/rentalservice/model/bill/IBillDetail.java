package com.example.rentalservice.model.bill;

public interface IBillDetail {
    String getBillDetailId();

    String getUtilityId();

    String getUtilityName();

    Integer getNumberUsed();

    Long getUtilityPrice();

    String getUtilityUnit();
}
