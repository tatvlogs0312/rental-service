package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {

    @Query(value = "select nextval('seq_contract')", nativeQuery = true)
    Long getSeqContract();

    @Query(value = """
         select c.id            as contractId,
                c.contract_code as contractCode,
                c.status        as contractStatusCode,
                c.created_date  as createdDate,
                ul.first_name   as lessorFirstName,
                ul.last_name    as lessorLastName,
                ul.phone_number as lessorPhoneNumber,
                ut.first_name   as tenantFirstName,
                ut.last_name    as tenantLastName,
                ut.phone_number as tenantPhoneNumber,
                h.id            as houseId,
                h.house_name    as houseName,
                r.id            as roomId,
                r.room_name     as roomName
         from contract c
                  join user_profile ul on c.lessor = ul.username
                  join user_profile ut on c.tenant = ut.username
                  join house h on c.house_id = h.id
                  join room r on c.room_id = r.id
         where 1 = 1
           and (:contractStatus is null or c.status = :contractStatus)
           and (:lessor is null or c.lessor = :lessor)
           and (:tenant is null or c.tenant = :tenant)
            """, nativeQuery = true)
    Page<Object[]> searchContract(String contractStatus, String lessor, String tenant, Pageable pageable);
}