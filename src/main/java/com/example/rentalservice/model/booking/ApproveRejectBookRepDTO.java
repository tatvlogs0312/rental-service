package com.example.rentalservice.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApproveRejectBookRepDTO {
    private String bookingId;
    private String status;
    private String message;
}
