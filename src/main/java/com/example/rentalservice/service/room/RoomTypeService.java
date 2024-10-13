package com.example.rentalservice.service.room;

import com.example.rentalservice.entity.RoomType;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.model.search.req.RoomTypeSearchReqDTO;
import com.example.rentalservice.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public PagingResponse<RoomType> searchRoomType(RoomTypeSearchReqDTO req) {
        if (StringUtils.isBlank(req.getName())) {
            req.setName("");
        }

        if (Objects.isNull(req.getSize())) {
            req.setSize(Integer.MAX_VALUE);
        }

        if (Objects.isNull(req.getPage())) {
            req.setPage(0);
        }

        PagingResponse<RoomType> response = new PagingResponse<>();

        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());

        Page<RoomType> page = roomTypeRepository.searchByName(req.getName(), pageable);
        if (page.hasContent()) {
            response.setData(page.getContent());
            response.setTotalData(page.getTotalElements());
            response.setTotalPage(page.getTotalPages());
        }

        return response;
    }
}
