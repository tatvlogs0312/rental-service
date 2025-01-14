package com.example.rentalservice.repository.custom;

import com.example.rentalservice.entity.Post;
import com.example.rentalservice.entity.RoomType;
import com.example.rentalservice.repository.PostRepository;
import com.example.rentalservice.repository.RoomTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KeywordRepository {

    private final RoomTypeRepository roomTypeRepository;
    private final PostRepository postRepository;

    public Map<String, String> roomTypes = new HashMap<>();
    public List<String> provinces = new ArrayList<>();
    public List<String> districts = new ArrayList<>();
    public List<String> wards = new ArrayList<>();

    @PostConstruct
    private void init() {
        this.getData();
    }

    private void getData() {
        roomTypes = roomTypeRepository.findAll().stream().collect(Collectors.toMap(RoomType::getName, RoomType::getId));
        provinces = postRepository.findAll().stream().map(Post::getProvince).distinct().toList();
        districts = postRepository.findAll().stream().map(Post::getDistrict).distinct().toList();
        wards = postRepository.findAll().stream().map(Post::getWard).distinct().toList();
    }

    public void getAfterAdd() {
        this.getData();
    }
}
