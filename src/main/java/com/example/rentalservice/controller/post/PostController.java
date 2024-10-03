package com.example.rentalservice.controller.post;

import com.example.rentalservice.model.post.NewPostReqDTO;
import com.example.rentalservice.model.post.PostReqDTO;
import com.example.rentalservice.model.search.req.PostSearchReqDTO;
import com.example.rentalservice.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@RequestBody NewPostReqDTO req) {
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
        return new ResponseEntity<>(postService.searchPost(req), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Object> getById(@PathVariable String postId) {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }
}
