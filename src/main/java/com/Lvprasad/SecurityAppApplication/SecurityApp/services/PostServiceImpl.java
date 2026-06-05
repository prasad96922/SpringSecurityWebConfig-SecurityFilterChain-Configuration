package com.Lvprasad.SecurityAppApplication.SecurityApp.services;

import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.PostDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.PostEntity;
import com.Lvprasad.SecurityAppApplication.SecurityApp.exceptions.ResourceNotFoundException;
import com.Lvprasad.SecurityAppApplication.SecurityApp.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

//   public PostServiceImpl(PostRepository postRepository) {
//        this.postRepository = postRepository;
//   }


    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {

        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {

        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("post not found with Id:" + postId));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO inputPost, Long postId) {
        PostEntity olderPost = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post not found with Id:" + postId));
        inputPost.setId(postId);
        PostEntity newPostEntity = modelMapper.map(inputPost, PostEntity.class);
        return modelMapper.map(postRepository.save(newPostEntity), PostDTO.class);
    }
}
