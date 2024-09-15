package com.example.rentalservice.service.post;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.common.Utils;
import com.example.rentalservice.entity.Post;
import com.example.rentalservice.entity.Room;
import com.example.rentalservice.entity.RoomImage;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.post.NewPostReqDTO;
import com.example.rentalservice.repository.PostRepository;
import com.example.rentalservice.repository.RoomImageRepository;
import com.example.rentalservice.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final RoomImageRepository roomImageRepository;
    private final DataService dataService;


    //tạo bài đăng
    public void createNewPost(NewPostReqDTO req) {
        Room room = dataService.getRoom(req.getRoomId());

        List<RoomImage> images = roomImageRepository.findAllByRoomId(room.getId());
        if (CollectionUtils.isEmpty(images)) {
            throw new ApplicationException("Vui lòng upload hình ảnh phòng trước khi đăng tin");
        }

        Post post = new Post();
        post.setId(Utils.generateId());
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post.setLessor(JwtUtils.getUsername());
        post.setCreateTime(LocalDateTime.now());

        postRepository.save(post);
    }


    //Xóa bài đăng
    public void deletePost(String postId) {
        postRepository.deleteById(postId);
    }
}
