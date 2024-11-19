package com.example.rentalservice.service.contract;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.entity.*;
import com.example.rentalservice.enums.ContractStatusEnum;
import com.example.rentalservice.enums.RoomStatusEnum;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.contract.CreateContractReqDTO;
import com.example.rentalservice.repository.ContractRepository;
import com.example.rentalservice.repository.ContractUtilityRepository;
import com.example.rentalservice.service.common.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractUtilityRepository contractUtilityRepository;
    private final DataService dataService;

    public void createContract(CreateContractReqDTO req) {
        UserProfile tenant = dataService.getUserByUsername(req.getTenant());
        House house = dataService.getHouse(req.getHouseId());
        Room room = dataService.getRoom(req.getRoomId());

        String lessor = JwtUtils.getUsername();

        if (StringUtils.equals(room.getRoomStatus(), RoomStatusEnum.RENTED.name())) {
            throw new ApplicationException("Phòng đang cho thuê, không thể tạo hợp đồng");
        }

        String contractId = UUID.randomUUID().toString();
        Contract contract = new Contract();
        contract.setId(contractId);
        contract.setLessor(lessor);
        contract.setTenant(tenant.getUsername());
        contract.setHouseId(house.getId());
        contract.setRoomId(room.getId());
        String contractCode = "HD-" + contractRepository.getSeqContract().toString();
        contract.setContractCode(contractCode);
        contract.setStatus(ContractStatusEnum.DRAFT.name());
        contract.setEffectDate(LocalDate.parse(req.getStartDate()));
        contract.setActualPrice(req.getPrice());

        List<ContractUtility> contractUtilities = new ArrayList<>();
        req.getUtilities().forEach(u -> {
            ContractUtility contractUtility = new ContractUtility();
            contractUtility.setId(UUID.randomUUID().toString());
            contractUtility.setContractId(contractId);
            contractUtility.setUtilityId(u.getUtilityId());
            contractUtility.setPrice(u.getPrice());
            contractUtility.setUnit(u.getUnit());
            contractUtilities.add(contractUtility);
        });

        contractRepository.save(contract);
        if (!contractUtilities.isEmpty()) {
            contractUtilityRepository.saveAll(contractUtilities);
        }
    }
}
