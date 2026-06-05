package com.Lvprasad.SecurityAppApplication.SecurityApp.services;



import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);

    PostDTO updatePost(PostDTO inputPost,Long postId);
}
