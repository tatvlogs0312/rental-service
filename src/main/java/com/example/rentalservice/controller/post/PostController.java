package com.example.rentalservice.controller.post;

import com.example.rentalservice.common.JsonUtils;
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
        log.info("/rental-service/post/create - req: {}", JsonUtils.toJson(req));
        postService.createNewPost(req);
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

    @GetMapping("/{postId}")
    public ResponseEntity<Object> getById(@PathVariable String postId) {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }
}
