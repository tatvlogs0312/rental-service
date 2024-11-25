package com.example.rentalservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "bill_detail")
@Table(name = "bill_detail")
public class BillDetail {

    @Id
    private String id;

    private String billId;

    private String utilityId;

    private Integer numberUsed;
}
