package com.example.rentalservice.controller.post;

import com.example.rentalservice.model.post.NewPostReqDTO;
import com.example.rentalservice.model.post.PostReqDTO;
import com.example.rentalservice.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<Object> createPost(NewPostReqDTO req) {
        postService.createNewPost(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<Object> deletePost(PostReqDTO req) {
        postService.deletePost(req.getId());
        return ResponseEntity.ok().build();
    }
}
