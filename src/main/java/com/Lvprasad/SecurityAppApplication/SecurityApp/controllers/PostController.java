package com.Lvprasad.SecurityAppApplication.SecurityApp.controllers;



import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.PostDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/home")
    public String home() {
        return "posts";
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {
        return  ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO inputPost) {
        return ResponseEntity.ok(postService.createNewPost(inputPost));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO inputPost, @PathVariable Long postId) {
        return ResponseEntity.ok(postService.updatePost(inputPost, postId));
    }

}
