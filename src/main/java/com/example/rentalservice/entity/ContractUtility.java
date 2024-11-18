package com.example.rentalservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "contract_utility")
@Table(name = "contract_utility")
public class ContractUtility {

    @Id
    private String id;

    private String contractId;

    private String utility_id;

    private Long price;

    private String unit;
}
