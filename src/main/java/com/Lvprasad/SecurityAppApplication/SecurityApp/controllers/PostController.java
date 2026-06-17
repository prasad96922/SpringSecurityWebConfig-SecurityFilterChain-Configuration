package com.Lvprasad.SecurityAppApplication.SecurityApp.controllers;



import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.PostDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// secure and preauthorize use for business logic requirements

@RestController
@RequestMapping(path = "posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/home")
    public String home() {
        return "posts";
    }

//    @GetMapping
//    @Secured("ROLE_USER")
//    public ResponseEntity<List<PostDTO>> getAllPosts() {
//        return ResponseEntity.ok(postService.getAllPosts());
//    }

    @GetMapping
    @Secured({"ROLE_CREATOR", "ROLE_ADMIN"})
    public ResponseEntity<List<PostDTO>> getAllPosts() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Authorities = " + auth.getAuthorities());

        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN') OR hasAnyAuthority('POST_VIEW')")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
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
