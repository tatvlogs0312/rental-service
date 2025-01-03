package com.example.rentalservice.controller.post;

import com.example.rentalservice.common.JsonUtils;
import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.model.post.NewPostReqDTO;
import com.example.rentalservice.model.post.PostReqDTO;
import com.example.rentalservice.model.search.req.PostSearchReqDTO;
import com.example.rentalservice.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;

    @Operation(summary = "Tao bai viet")
    @PostMapping("/create")
    public ResponseEntity<Object> createPost(NewPostReqDTO req) {
        log.info("/rental-service/post/create");
        postService.createNewPost(req);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Tao bai viet")
    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable String id, NewPostReqDTO req) {
        log.info("/rental-service/post/update - id: {}", id);
        postService.updatePost(id, req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deletePost(@RequestBody PostReqDTO req) {
        postService.deletePost(req.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<Object> searchPost(@RequestBody PostSearchReqDTO req) {
        log.info("/rental-service/post/search - req: {}", JsonUtils.toJson(req));
        return new ResponseEntity<>(postService.searchPost(req), HttpStatus.OK);
    }

    @PostMapping("/search-recommend")
    public ResponseEntity<Object> searchRecommendPost(@RequestBody PostSearchReqDTO req) {
        log.info("/rental-service/post/search-recommend - req: {}", JsonUtils.toJson(req));
        return new ResponseEntity<>(postService.searchRecommendPost(req), HttpStatus.OK);
    }

    @PostMapping("/search-for-lessor")
    public ResponseEntity<Object> searchForLessor(@RequestBody PostSearchReqDTO req) {
        log.info("/rental-service/post/search-for-lessor - user: {} - req: {}", JwtUtils.getUsername(), JsonUtils.toJson(req));
        return new ResponseEntity<>(postService.searchForLessor(req), HttpStatus.OK);
    }

    @PostMapping("/search-for-lessor/v2")
    public ResponseEntity<Object> searchForLessorV2(@RequestBody PostSearchReqDTO req) {
        log.info("/rental-service/post/search-for-lessor/v2 - user: {} - req: {}", JwtUtils.getUsername(), JsonUtils.toJson(req));
        return new ResponseEntity<>(postService.searchForLessorV2(req), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Object> getById(@PathVariable String postId) {
        log.info("/rental-service/post/{}", JsonUtils.toJson(postId));
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }
}
