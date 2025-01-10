package com.example.rentalservice.service.statistical;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.common.RepositoryUtils;
import com.example.rentalservice.enums.BillStatusEnum;
import com.example.rentalservice.enums.RoomStatusEnum;
import com.example.rentalservice.model.statistical.BillStatisticalDTO;
import com.example.rentalservice.model.statistical.BillYearDTO;
import com.example.rentalservice.model.statistical.RoomStatisticalDTO;
import com.example.rentalservice.repository.BillRepository;
import com.example.rentalservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticalService {

    private final RoomRepository roomRepository;
    private final BillRepository billRepository;

    public RoomStatisticalDTO getRoomStatistical() {
        String lessor = JwtUtils.getUsername();

        return RoomStatisticalDTO.builder()
                .numberEmpty(roomRepository.countAllByRoomStatusAndLessorAndDeleted(RoomStatusEnum.EMPTY.name(), lessor, false))
                .numberRented(roomRepository.countAllByRoomStatusAndLessorAndDeleted(RoomStatusEnum.RENTED.name(), lessor, false))
                .build();
    }

    public BillStatisticalDTO getBillStatistical() {
        String lessor = JwtUtils.getUsername();

        return BillStatisticalDTO.builder()
                .numberDraft(billRepository.countAllByLessorAndStatus(lessor, BillStatusEnum.DRAFT.name()))
                .numberPending(billRepository.countAllByLessorAndStatus(lessor, BillStatusEnum.PENDING.name()))
                .numberPaid(billRepository.countAllByLessorAndStatus(lessor, BillStatusEnum.PAYED.name()))
                .build();
    }

    public List<BillYearDTO> getBillYear(Integer year) {
        String lessor = JwtUtils.getUsername();

        List<Object[]> totals = billRepository.getBillInYear(lessor, year);
        return totals.stream()
                .map(x -> BillYearDTO.builder()
                        .month(RepositoryUtils.setValueForField(Integer.class, x[0]))
                        .total(RepositoryUtils.setValueForField(Long.class, x[1]))
                        .build())
                .toList();
    }
}
