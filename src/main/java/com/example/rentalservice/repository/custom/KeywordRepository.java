package com.example.rentalservice.repository.custom;

import com.example.rentalservice.entity.Post;
import com.example.rentalservice.entity.RoomType;
import com.example.rentalservice.repository.PostRepository;
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
    private final PostRepository postRepository;

    public List<String> roomTypes = new ArrayList<>();
    public List<String> provinces = new ArrayList<>();
    public List<String> districts = new ArrayList<>();
    public List<String> wards = new ArrayList<>();

    @PostConstruct
    private void init() {
        roomTypes = roomTypeRepository.findAll().stream().map(RoomType::getName).toList();
        provinces = postRepository.findAll().stream().map(Post::getProvince).distinct().toList();
        districts = postRepository.findAll().stream().map(Post::getDistrict).distinct().toList();
        wards = postRepository.findAll().stream().map(Post::getWard).distinct().toList();
    }
}
