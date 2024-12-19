package com.example.rentalservice.service.dashboard;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.model.dashboard.DashboardDTO;
import com.example.rentalservice.model.dashboard.DashboardDetailDTO;
import com.example.rentalservice.repository.custom.DashboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardDetailDTO getDashboard() {
        LocalDate periods = LocalDate.now().minusDays(1);

        String lessor = JwtUtils.getUsername();
        int month = periods.getMonthValue();
        int year = periods.getYear();

        DashboardDetailDTO res = new DashboardDetailDTO();
        res.setMonth(month);
        res.setYear(year);

        List<DashboardDTO> dashboardDTOS = dashboardRepository.getNumber(lessor, month, year);
        if (!CollectionUtils.isEmpty(dashboardDTOS)) {
            res.setContractDraft(getNumberByType(dashboardDTOS, "CONTRACT_DRAFT"));
            res.setContractPending(getNumberByType(dashboardDTOS, "CONTRACT_PENDING"));
            res.setBillDraft(getNumberByType(dashboardDTOS, "BILL_DRAFT"));
            res.setBillPending(getNumberByType(dashboardDTOS, "BILL_PENDING"));
            res.setWarningPending(getNumberByType(dashboardDTOS, "WARNING_PENDING"));
        }

        return res;
    }

    private long getNumberByType(List<DashboardDTO> dashboardDTOS, String type) {
        long number = 0;
        Optional<DashboardDTO> dashboardDTO = dashboardDTOS.stream().filter(x -> Objects.equals(x.getType(), type)).findFirst();
        if (dashboardDTO.isPresent()) {
            return dashboardDTO.get().getNumber();
        }
        return number;
    }
}
