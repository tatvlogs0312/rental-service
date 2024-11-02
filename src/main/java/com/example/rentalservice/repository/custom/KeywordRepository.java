package com.example.rentalservice.repository.custom;

import com.example.rentalservice.common.JsonUtils;
import com.example.rentalservice.entity.RoomPosition;
import com.example.rentalservice.entity.RoomType;
import com.example.rentalservice.redis.RedisService;
import com.example.rentalservice.repository.RoomPositionRepository;
import com.example.rentalservice.repository.RoomTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KeywordRepository {

    private final RoomTypeRepository roomTypeRepository;
    private final RoomPositionRepository roomPositionRepository;

    public List<String> roomTypes = new ArrayList<>();
    public List<String> provinces = new ArrayList<>();
    public List<String> districts = new ArrayList<>();
    public List<String> wards = new ArrayList<>();

    @PostConstruct
    private void init() {
        roomTypes = roomTypeRepository.findAll().stream().map(RoomType::getName).toList();
        provinces = roomPositionRepository.findAll().stream().map(RoomPosition::getProvince).distinct().toList();
        districts = roomPositionRepository.findAll().stream().map(RoomPosition::getDistrict).distinct().toList();
        wards = roomPositionRepository.findAll().stream().map(RoomPosition::getWard).distinct().toList();
    }
}
