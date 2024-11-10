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
import com.example.rentalservice.model.search.KeywordDTO;
import com.example.rentalservice.model.search.req.PostSearchReqDTO;
import com.example.rentalservice.model.search.res.PostSearchResDTO;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.model.user_profile.UserPaperResDTO;
import com.example.rentalservice.proxy.StorageServiceProxy;
import com.example.rentalservice.repository.*;
import com.example.rentalservice.service.common.DataService;
import com.example.rentalservice.service.common.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final RoomImageRepository roomImageRepository;
    private final RoomUtilityRepository roomUtilityRepository;
    private final RoomPositionRepository roomPositionRepository;
    private final UserProfileRepository userProfileRepository;
    private final ViewHistoryRepository viewHistoryRepository;
    private final SearchService searchService;
    private final DataService dataService;
    private final StorageServiceProxy storageServiceProxy;

    @Qualifier("cachedThreadPool")
    @Autowired
    private Executor executor;


    //tạo bài đăng
    public void createNewPost(NewPostReqDTO req) {
        Post post = new Post();
        post.setId(Utils.generateId());
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post.setLessor(JwtUtils.getUsername());
        post.setCreateTime(LocalDateTime.now());
        post.setNumberWatch(0L);
        post.setPositionDetail(req.getPositionDetail());
        post.setWard(req.getWard());
        post.setDistrict(req.getDistrict());
        post.setProvince(req.getProvince());
        post.setPrice(req.getPrice());
        post.setRoomTypeId(req.getRoomType());

        List<PostImage> postImages = new ArrayList<>();
        req.getFiles().forEach(file -> {
            try {
                var pathAsync = CompletableFuture.supplyAsync(() -> {
                    UserPaperResDTO userPaperResDTO = storageServiceProxy.uploadFile(file);
                    return userPaperResDTO.getFile();
                }, executor).get();

                PostImage postImage = new PostImage();
                postImage.setId(Utils.generateId());
                postImage.setPostId(post.getId());
                postImage.setUrl(pathAsync);
                postImages.add(postImage);

            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        postRepository.save(post);
        if (!CollectionUtils.isEmpty(postImages)) {
            postImageRepository.saveAll(postImages);
        }
    }


    //Xóa bài đăng
    public void deletePost(String postId) {
        postRepository.deleteById(postId);
    }


    //Tìm kiem bai dang
    public PagingResponse<PostSearchResDTO> searchPost(PostSearchReqDTO req) {
        //Handle keyword tim kiem
        if (StringUtils.isNotBlank(req.getKeyword())) {
            KeywordDTO keywordDTO = searchService.handleKeyword(req.getKeyword());
            req.setProvince(keywordDTO.getProvince());
            req.setDistrict(keywordDTO.getDistrict());
            req.setWard(keywordDTO.getWard());
            if (Objects.nonNull(keywordDTO.getPriceIs())) {
                req.setPriceFrom(keywordDTO.getPriceIs());
                req.setPriceTo(keywordDTO.getPriceIs());
            } else {
                req.setPriceFrom(keywordDTO.getPriceFrom());
                req.setPriceTo(keywordDTO.getPriceTo());
            }
        }

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

    //Lay danh sach de xuat
    public PagingResponse<PostSearchResDTO> searchRecommendPost(PostSearchReqDTO req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());

        List<String> roomTypes = new ArrayList<>();
        List<String> wards = new ArrayList<>();
        Long priceFrom = 0L;
        Long priceTo = 100000000L;
        List<ViewHistory> viewHistories = viewHistoryRepository.findAllByUsername(JwtUtils.getUsername());
        if (!CollectionUtils.isEmpty(viewHistories)) {
            roomTypes = viewHistories.stream().map(ViewHistory::getRoomType).toList();
            wards = viewHistories.stream().map(ViewHistory::getPosition).toList();

            if (viewHistories.size() > 1) {
                List<Long> prices = viewHistories.stream().map(ViewHistory::getPrice).sorted().toList();
                priceFrom = prices.get(0);
                priceTo = prices.get(viewHistories.size() - 1);
            } else {
                priceFrom = viewHistories.get(0).getPrice() - 1000000;
                priceTo = viewHistories.get(0).getPrice() + 1000000;
            }
        }


        PagingResponse<PostSearchResDTO> response = new PagingResponse<>();
        Page<Object[]> data = postRepository.findAllRecommend(roomTypes, wards, priceFrom, priceTo, pageable);
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

        String tenant = JwtUtils.getUsername();

        CompletableFuture.runAsync(() -> {
            if (StringUtils.isNotBlank(tenant)) {
                Optional<ViewHistory> historyOtp = viewHistoryRepository.findFirstByUsernameAndPostId(tenant, postId);
                ViewHistory viewHistory;
                if (historyOtp.isEmpty()) {
                    viewHistory = new ViewHistory();
                    viewHistory.setId(UUID.randomUUID().toString());
                    viewHistory.setRoomId(room.getId());
                    viewHistory.setPostId(postId);
                    viewHistory.setTimeView(LocalDateTime.now());
                    viewHistory.setUsername(tenant);
                    viewHistory.setRoomType(room.getRoomTypeId());
                    viewHistory.setPosition(postDetailDTO.getPosition().getWard());
                    viewHistory.setPrice(room.getPrice());
                } else {
                    viewHistory = historyOtp.get();
                    viewHistory.setTimeView(LocalDateTime.now());
                }
                viewHistoryRepository.save(viewHistory);
            }

            post.setNumberWatch(post.getNumberWatch() + 1);
            postRepository.save(post);
        });

        return postDetailDTO;
    }

}
