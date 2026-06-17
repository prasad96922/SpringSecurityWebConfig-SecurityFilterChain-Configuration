package com.Lvprasad.SecurityAppApplication.SecurityApp.utils;


import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.PostDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.UserEntity;
import com.Lvprasad.SecurityAppApplication.SecurityApp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

    public boolean isOwnerOfPost(Long postId) {

        System.out.println("Checking ownership for postId = " + postId);

        UserEntity user = (UserEntity)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        System.out.println("Logged in user = " + user.getId());

        PostDTO postDTO = postService.getPostById(postId);

        System.out.println("Post owner = "
                + postDTO.getAuthor().getId());

        return postDTO.getAuthor()
                .getId().equals(user.getId());

    }
}
