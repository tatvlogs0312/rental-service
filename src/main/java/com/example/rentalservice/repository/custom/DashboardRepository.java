package com.example.rentalservice.repository.custom;

import com.example.rentalservice.common.RepositoryUtils;
import com.example.rentalservice.model.dashboard.DashboardDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DashboardRepository {

    private final EntityManager entityManager;

    public List<DashboardDTO> getNumber(String lessor, Integer month, Integer year) {
        String sql = """
                select 'CONTRACT_DRAFT' as type, count(*) from contract where status = 'DRAFT' and lessor = :lessor
                union
                select 'CONTRACT_PENDING' as type, count(*) from contract where status = 'PENDING_SIGNED' and lessor = :lessor
                union
                select 'BILL_DRAFT' as type, count(*) from bill where month = :month and year = :year and status = 'DRAFT' and lessor = :lessor
                union
                select 'BILL_PENDING' as type, count(*) from bill where month = :month and year = :year and status = 'PENDING' and lessor = :lessor
                union
                select 'WARNING_PENDING' as type, count(*) from malfunction_warning where status = 'PENDING' and lessor = :lessor;
                """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("lessor", lessor);
        query.setParameter("month", month);
        query.setParameter("year", year);

        List<Object[]> result = (List<Object[]>) query.getResultList();
        List<DashboardDTO> dashboardDTOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            result.forEach(x -> {
                DashboardDTO dashboardDTO = new DashboardDTO();
                dashboardDTO.setType(RepositoryUtils.setValueForField(String.class, x[0]));
                dashboardDTO.setNumber(RepositoryUtils.setValueForField(Long.class, x[1]));
                dashboardDTOS.add(dashboardDTO);
            });
        }

        return dashboardDTOS;
    }
}
