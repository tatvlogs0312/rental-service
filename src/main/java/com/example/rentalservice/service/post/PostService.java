package com.example.rentalservice.service.post;

import com.example.rentalservice.common.InputUtils;
import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.common.RepositoryUtils;
import com.example.rentalservice.common.Utils;
import com.example.rentalservice.entity.Post;
import com.example.rentalservice.entity.Room;
import com.example.rentalservice.entity.RoomImage;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.post.NewPostReqDTO;
import com.example.rentalservice.model.search.req.PostSearchReqDTO;
import com.example.rentalservice.model.search.res.PostSearchResDTO;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.repository.PostRepository;
import com.example.rentalservice.repository.RoomImageRepository;
import com.example.rentalservice.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

        Boolean existPost = postRepository.existsAllByRoomId(req.getRoomId());
        if (BooleanUtils.isTrue(existPost)) {
            throw new ApplicationException("Phòng đã tạo bài đăng");
        }

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
        post.setRoomId(req.getRoomId());

        postRepository.save(post);
    }


    //Xóa bài đăng
    public void deletePost(String postId) {
        postRepository.deleteById(postId);
    }


    //Tìm kiem bai dang
    public PagingResponse<PostSearchResDTO> searchPost(PostSearchReqDTO req) {
        InputUtils.handleInputSearchPost(req);

        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());

        PagingResponse<PostSearchResDTO> response = new PagingResponse<>();
        Page<Object[]> data = postRepository.findAllByCondition(req.getRoomTypeId(), req.getDetail(), req.getWard(),
                req.getDistrict(), req.getProvince(), req.getPriceFrom(), req.getPriceTo(), pageable);
        if (data.hasContent()) {
            List<PostSearchResDTO> models = new ArrayList<>();
            data.getContent().forEach(post -> {
                AtomicInteger i = new AtomicInteger(0);
                PostSearchResDTO postSearchResDTO = PostSearchResDTO.builder()
                        .postId(RepositoryUtils.setValueForField(String.class, post[i.getAndIncrement()]))
                        .title(RepositoryUtils.setValueForField(String.class, post[i.getAndIncrement()]))
                        .positionDetail(RepositoryUtils.setValueForField(String.class, post[i.getAndIncrement()]))
                        .province(RepositoryUtils.setValueForField(String.class, post[i.getAndIncrement()]))
                        .district(RepositoryUtils.setValueForField(String.class, post[i.getAndIncrement()]))
                        .ward(RepositoryUtils.setValueForField(String.class, post[i.getAndIncrement()]))
                        .typeCode(RepositoryUtils.setValueForField(String.class, post[i.getAndIncrement()]))
                        .typeName(RepositoryUtils.setValueForField(String.class, post[i.getAndIncrement()]))
                        .price(RepositoryUtils.setValueForField(Long.class, post[i.getAndIncrement()]))
                        .postTime(RepositoryUtils.setValueForField(LocalDateTime.class, post[i.getAndIncrement()]))
                        .build();
                models.add(postSearchResDTO);
            });

            response.setData(models);
            response.setTotalData(data.getTotalElements());
            response.setTotalPage(data.getTotalPages());
        }

        return response;
    }
}
