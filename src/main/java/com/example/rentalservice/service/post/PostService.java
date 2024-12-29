package com.example.rentalservice.service.post;

import com.example.rentalservice.common.*;
import com.example.rentalservice.entity.*;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.post.ILessorPost;
import com.example.rentalservice.model.post.NewPostReqDTO;
import com.example.rentalservice.model.post.detail.PostDetailDTO;
import com.example.rentalservice.model.room.detail.PositionDTO;
import com.example.rentalservice.model.search.KeywordDTO;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.model.search.req.PostSearchReqDTO;
import com.example.rentalservice.model.search.res.LessorPostDTO;
import com.example.rentalservice.model.search.res.LessorPostResDTO;
import com.example.rentalservice.model.search.res.PostSearchResDTO;
import com.example.rentalservice.model.user_profile.UserPaperResDTO;
import com.example.rentalservice.proxy.StorageServiceProxy;
import com.example.rentalservice.repository.*;
import com.example.rentalservice.service.common.DataService;
import com.example.rentalservice.service.common.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final RoomTypeRepository roomTypeRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
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
        if (CollectionUtils.isEmpty(req.getFiles())) {
            throw new ApplicationException("vui lòng đăng ít nhất 1 ảnh");
        }

        Post post = new Post();
        post.setId(Utils.generateId());
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post.setLessor(JwtUtils.getUsername());
        post.setCreateTime(LocalDateTime.now());
        post.setNumberWatch(0L);
        post.setPositionDetail(req.getPositionDetail());
        post.setWard(Utils.handleWard(req.getWard()));
        post.setDistrict(Utils.handleDistrict(req.getDistrict()));
        post.setProvince(Utils.handleProvince(req.getProvince()));
        post.setPrice(req.getPrice());
        post.setRoomTypeId(req.getRoomType());
        post.setAcreage(req.getAcreage());
        post.setNumberOfRoom(req.getNumberOfRoom());

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

    //update post
    public void updatePost(String postId, NewPostReqDTO req) {
        Post post = dataService.getPost(postId);

        Post newPost = new Post();
        newPost.setId(post.getId());
        newPost.setTitle(req.getTitle());
        newPost.setContent(req.getContent());
        newPost.setLessor(post.getLessor());
        newPost.setCreateTime(post.getCreateTime());
        newPost.setNumberWatch(post.getNumberWatch());
        newPost.setPositionDetail(req.getPositionDetail());
        newPost.setWard(req.getWard());
        newPost.setDistrict(req.getDistrict());
        newPost.setProvince(req.getProvince());
        newPost.setPrice(req.getPrice());
        newPost.setRoomTypeId(req.getRoomType());
        newPost.setAcreage(req.getAcreage());
        newPost.setNumberOfRoom(req.getNumberOfRoom());

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

        //delete before delete
        postImageRepository.deleteAllByPostId(postId);

        postRepository.save(newPost);
        if (!CollectionUtils.isEmpty(postImages)) {
            postImageRepository.saveAll(postImages);
        }
    }


    //Xóa bài đăng
    public void deletePost(String postId) {
        postRepository.deleteById(postId);
        postImageRepository.deleteAllByPostId(postId);
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
        Page<Object[]> data = postRepository.findAllRecommend(req.getIgnore(), roomTypes, wards, priceFrom, priceTo, pageable);
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

    public PagingResponse<LessorPostResDTO> searchForLessor(PostSearchReqDTO req) {
        String username = JwtUtils.getUsername();
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());

        PagingResponse<LessorPostResDTO> response = new PagingResponse<>();
        Page<Post> postPage = postRepository.findAllByLessorOrderByCreateTimeDesc(username, pageable);
        if (postPage.hasContent()) {
            List<LessorPostResDTO> data = postPage.getContent().stream().map(p -> LessorPostResDTO.builder()
                            .id(p.getId())
                            .title(p.getTitle())
                            .numberWatch(p.getNumberWatch())
                            .createTime(DateUtils.toStr(p.getCreateTime(), DateUtils.F_HHMMSSDDMMYYYY))
                            .build())
                    .toList();
            response.setData(data);
        } else {
            response.setData(new ArrayList<>());
        }

        response.setTotalData(postPage.getTotalElements());
        response.setTotalPage(postPage.getTotalPages());

        return response;
    }

    public PagingResponse<LessorPostDTO> searchForLessorV2(PostSearchReqDTO req) {
        String username = JwtUtils.getUsername();
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());

        PagingResponse<LessorPostDTO> response = new PagingResponse<>();
        Page<ILessorPost> postPage = postRepository.findAllByLessor(username, pageable);
        if (postPage.hasContent()) {
            List<LessorPostDTO> data = postPage.stream().map(LessorPostDTO::new).toList();
            response.setData(data);
        } else {
            response.setData(new ArrayList<>());
        }

        response.setTotalData(postPage.getTotalElements());
        response.setTotalPage(postPage.getTotalPages());

        return response;
    }


    //Xem chi tiet bai viet
    public PostDetailDTO getPostById(String postId) {
        Post post = dataService.getPost(postId);

        PostDetailDTO postDetailDTO = new PostDetailDTO();
        postDetailDTO.setPostId(post.getId());
        postDetailDTO.setTitle(post.getTitle());
        postDetailDTO.setContent(post.getContent());

        postDetailDTO.setAcreage(post.getAcreage());
        postDetailDTO.setNumberOfRoom(post.getNumberOfRoom());
        postDetailDTO.setPrice(post.getPrice());

        postDetailDTO.setPosition(PositionDTO.builder()
                .detail(post.getPositionDetail())
                .ward(post.getWard())
                .district(post.getDistrict())
                .province(post.getDistrict())
                .build());

        Optional<UserProfile> userProfileOtp = userProfileRepository.findFirstByUsername(post.getLessor());
        if (userProfileOtp.isPresent()) {
            UserProfile userProfile = userProfileOtp.get();
            postDetailDTO.setLessorName(userProfile.getFirstName() + " " + userProfile.getLastName());
            postDetailDTO.setLessorNumber(userProfile.getPhoneNumber());
        }

        Optional<RoomType> roomType = roomTypeRepository.findById(post.getRoomTypeId());
        roomType.ifPresent(t -> {
            postDetailDTO.setRoomId(t.getId());
            postDetailDTO.setRoomTypeName(t.getName());
        });

        List<PostImage> roomImages = postImageRepository.findAllByPostId(post.getId());
        if (!CollectionUtils.isEmpty(roomImages)) {
            postDetailDTO.setImage(roomImages.stream().map(PostImage::getUrl).toList());
        }

        String tenant = JwtUtils.getUsername();

        CompletableFuture.runAsync(() -> {
            log.info("post lessor: {} - user: {}", tenant, post.getLessor());
            if (StringUtils.isNotBlank(tenant) && !StringUtils.equals(tenant, post.getLessor())) {
                Optional<ViewHistory> historyOtp = viewHistoryRepository.findFirstByUsernameAndPostId(tenant, postId);
                ViewHistory viewHistory;
                if (historyOtp.isEmpty()) {
                    viewHistory = new ViewHistory();
                    viewHistory.setId(UUID.randomUUID().toString());
                    viewHistory.setPostId(postId);
                    viewHistory.setTimeView(LocalDateTime.now());
                    viewHistory.setUsername(tenant);
                    viewHistory.setRoomType(post.getRoomTypeId());
                    viewHistory.setPosition(postDetailDTO.getPosition().getWard());
                    viewHistory.setPrice(post.getPrice());
                } else {
                    viewHistory = historyOtp.get();
                    viewHistory.setTimeView(LocalDateTime.now());
                }
                viewHistoryRepository.save(viewHistory);

                post.setNumberWatch(post.getNumberWatch() + 1);
                postRepository.save(post);
            }
        });

        return postDetailDTO;
    }

}
