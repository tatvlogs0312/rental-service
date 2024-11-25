package com.example.rentalservice.service.bill;

import com.example.rentalservice.common.JsonUtils;
import com.example.rentalservice.entity.Bill;
import com.example.rentalservice.entity.BillDetail;
import com.example.rentalservice.entity.Contract;
import com.example.rentalservice.entity.ContractUtility;
import com.example.rentalservice.enums.BillStatusEnum;
import com.example.rentalservice.enums.ContractStatusEnum;
import com.example.rentalservice.enums.NotificationTypeEnum;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.bill.CreateBillDetailReqDTO;
import com.example.rentalservice.model.bill.CreateBillReqDTO;
import com.example.rentalservice.model.fcm.NotificationReqDTO;
import com.example.rentalservice.model.fcm.NotificationType;
import com.example.rentalservice.repository.BillDetailRepository;
import com.example.rentalservice.repository.BillRepository;
import com.example.rentalservice.repository.ContractRepository;
import com.example.rentalservice.repository.ContractUtilityRepository;
import com.example.rentalservice.service.common.DataService;
import com.example.rentalservice.service.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillService {

    private final DataService dataService;
    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;
    private final ContractRepository contractRepository;
    private final ContractUtilityRepository contractUtilityRepository;
    private final FcmService fcmService;

    public void createContract(CreateBillReqDTO req) {
        Contract contract = dataService.getContract(req.getContractId());
        if (!Objects.equals(contract.getStatus(), ContractStatusEnum.SIGNED.name())) {
            throw new ApplicationException("Hợp đồng không hợp lệ, không thể tạo hóa đơn");
        }

        if (Objects.isNull(req.getIsRentContinue())) {
            throw new ApplicationException("Vui lòng chọn có tiếp tục thuê trọ không");
        }

        LocalDate startDate = contract.getEffectDate();
        LocalDate billDate = LocalDate.of(req.getYear(), req.getMonth(), 1);
        if (billDate.isBefore(startDate)) {
            throw new ApplicationException("Không thể tạo hóa đơn trước tháng hợp đồng có hiệu lực");
        }

        if (CollectionUtils.isEmpty(req.getDetails())) {
            throw new ApplicationException("Vui lòng điền số lượng sử dụng dịch vụ");
        }

        List<ContractUtility> contractUtilities = contractUtilityRepository.findAllByContractId(contract.getId());
        List<String> utilityTypes = contractUtilities.stream().map(ContractUtility::getUtilityId).toList();

        req.getDetails().forEach(u -> {
            if (!utilityTypes.contains(u.getUtilityId())) {
                throw new ApplicationException("Có loại dịch vụ không có trong hợp đồng");
            }
        });

        List<Bill> billExist = billRepository.findAllByContractIdAndMonthAndYearAndStatusIn(
                req.getContractId(), req.getMonth(), req.getYear(), List.of(BillStatusEnum.DRAFT.name(), BillStatusEnum.PENDING.name(), BillStatusEnum.PAYED.name()));
        if (!CollectionUtils.isEmpty(billExist)) {
            throw new ApplicationException("Đã có hóa đơn chờ khách thuê đóng hoặc đã đóng");
        }

        String billId = UUID.randomUUID().toString();
        Bill newBill = new Bill();
        newBill.setId(billId);
        newBill.setBillCode("HĐ" + billRepository.getSeqBill().toString() + "-" + req.getMonth() + "-" + req.getYear());
        newBill.setStatus(BillStatusEnum.DRAFT.name());
        newBill.setContractId(req.getContractId());
        newBill.setCreateDate(LocalDate.now());
        newBill.setMonth(req.getMonth());
        newBill.setYear(req.getYear());
        newBill.setLessor(contract.getLessor());
        newBill.setTenant(contract.getTenant());
        newBill.setIsRentContinue(req.getIsRentContinue());

        long money = 0L;
        List<BillDetail> billDetails = new ArrayList<>();
        for (CreateBillDetailReqDTO billDetailReq : req.getDetails()) {
            Optional<ContractUtility> contractUtility = contractUtilities.stream()
                    .filter(cu -> Objects.equals(cu.getUtilityId(), billDetailReq.getUtilityId()))
                    .findAny();
            if (contractUtility.isPresent()) {
                BillDetail billDetail = new BillDetail();
                billDetail.setId(UUID.randomUUID().toString());
                billDetail.setBillId(billId);
                billDetail.setNumberUsed(billDetailReq.getNumberUsed());
                billDetail.setUtilityId(billDetailReq.getUtilityId());
                billDetails.add(billDetail);

                money = money + (billDetailReq.getNumberUsed() * contractUtility.get().getPrice());
            }
        }

        if (BooleanUtils.isTrue(req.getIsRentContinue())) {
            money = money + contract.getActualPrice();
        }
        newBill.setNumberPayed(money);

        billRepository.save(newBill);
        billDetailRepository.saveAll(billDetails);
    }

    public void sendUser(String billId) {
        Bill bill = dataService.getBill(billId);
        bill.setStatus(BillStatusEnum.PENDING.name());
        billRepository.save(bill);

        Contract contract = dataService.getContract(bill.getContractId());

        NotificationReqDTO notificationReqDTO = NotificationReqDTO.builder()
                .title(String.format("Hoá đơn tháng %s năm %s", bill.getMonth(), bill.getYear()))
                .content("""
                        Bạn có hóa đơn cần thanh toán.
                        Bấm để xem chi tiết lý do.
                        """)
                .data(JsonUtils.toJson(new NotificationType(NotificationTypeEnum.BILL.name(), billId)))
                .userReceive(contract.getTenant())
                .build();
        fcmService.sendNotificationToUser(notificationReqDTO);
    }

    public void updatePayment(String billId) {
        Bill bill = dataService.getBill(billId);
        bill.setStatus(BillStatusEnum.PAYED.name());
        billRepository.save(bill);

        Contract contract = dataService.getContract(bill.getContractId());

        NotificationReqDTO notificationReqDTO = NotificationReqDTO.builder()
                .title(String.format("Hoá đơn tháng %s năm %s", bill.getMonth(), bill.getYear()))
                .content(String.format("""
                        Hóa đơn tháng %s năm %s đã được chủ trọ xác nhận bạn đã thanh toán.
                        Bấm để xem chi tiết lý do.
                        """, bill.getMonth(), bill.getYear()))
                .data(JsonUtils.toJson(new NotificationType(NotificationTypeEnum.BILL.name(), billId)))
                .userReceive(contract.getTenant())
                .build();
        fcmService.sendNotificationToUser(notificationReqDTO);
    }
}
