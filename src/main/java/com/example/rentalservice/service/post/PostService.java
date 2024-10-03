package com.example.rentalservice.service.post;

import com.example.rentalservice.common.InputUtils;
import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.common.RepositoryUtils;
import com.example.rentalservice.common.Utils;
import com.example.rentalservice.entity.*;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.post.NewPostReqDTO;
import com.example.rentalservice.model.post.detail.PostDetailDTO;
import com.example.rentalservice.model.room.detail.PositionDTO;
import com.example.rentalservice.model.room.detail.UtilityDTO;
import com.example.rentalservice.model.search.req.PostSearchReqDTO;
import com.example.rentalservice.model.search.res.PostSearchResDTO;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.repository.*;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final RoomImageRepository roomImageRepository;
    private final RoomUtilityRepository roomUtilityRepository;
    private final RoomPositionRepository roomPositionRepository;
    private final UserProfileRepository userProfileRepository;
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
                        .firstImage(RepositoryUtils.setValueForField(String.class, post[i.getAndIncrement()]))
                        .build();
                models.add(postSearchResDTO);
            });

            response.setData(models);
            response.setTotalData(data.getTotalElements());
            response.setTotalPage(data.getTotalPages());
        }

        return response;
    }


    //Xem chi tiet bai viet
    public PostDetailDTO getPostById(String postId) {
        Post post = dataService.getPost(postId);

        PostDetailDTO postDetailDTO = new PostDetailDTO();
        postDetailDTO.setPostId(post.getId());
        postDetailDTO.setTitle(post.getTitle());
        postDetailDTO.setContent(post.getContent());

        Optional<UserProfile> userProfileOtp = userProfileRepository.findFirstByUsername(post.getLessor());
        if (userProfileOtp.isPresent()) {
            UserProfile userProfile = userProfileOtp.get();
            postDetailDTO.setLessorName(userProfile.getFirstName() + " " + userProfile.getLastName());
            postDetailDTO.setLessorNumber(userProfile.getPhoneNumber());
        }

        Room room = dataService.getRoom(post.getRoomId());
        postDetailDTO.setRoomId(room.getId());
        postDetailDTO.setAcreage(room.getAcreage());
        postDetailDTO.setNumberOfRoom(room.getNumberOfRom());
        postDetailDTO.setPrice(room.getPrice());

        Optional<RoomPosition> roomPositionOpt = roomPositionRepository.findFirstByRoomId(room.getId());
        if (roomPositionOpt.isPresent()) {
            RoomPosition roomPosition = roomPositionOpt.get();
            postDetailDTO.setPosition(PositionDTO.builder()
                    .detail(roomPosition.getDetail())
                    .ward(roomPosition.getWard())
                    .district(roomPosition.getDistrict())
                    .province(roomPosition.getDistrict())
                    .build());
        }

        List<RoomImage> roomImages = roomImageRepository.findAllByRoomId(room.getId());
        if (!CollectionUtils.isEmpty(roomImages)) {
            postDetailDTO.setImage(roomImages.stream().map(RoomImage::getUrl).toList());
        }

        List<Object[]> roomUtility = roomUtilityRepository.findAllByRoomId(room.getId());
        if (!CollectionUtils.isEmpty(roomUtility)) {
            postDetailDTO.setUtility(
                    roomUtility.stream()
                            .map(x -> UtilityDTO.builder()
                                    .utilityId(RepositoryUtils.setValueForField(String.class, x[0]))
                                    .utilityName(RepositoryUtils.setValueForField(String.class, x[1]))
                                    .utilityPrice(RepositoryUtils.setValueForField(Long.class, x[2]))
                                    .utilityUnit(RepositoryUtils.setValueForField(String.class, x[3]))
                                    .build())
                            .toList()
            );
        }

        return postDetailDTO;
    }

}
