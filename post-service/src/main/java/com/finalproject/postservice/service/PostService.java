package com.finalproject.postservice.service;


import com.finalproject.postservice.dto.PostRequest;
import com.finalproject.postservice.dto.UserResponse;
import com.finalproject.postservice.model.Post;
import com.finalproject.postservice.model.UserNotFoundException;
import com.finalproject.postservice.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final WebClient.Builder webClientBuilder;

    public Post createPost(PostRequest postRequest, Long userId) {
        UserResponse userResponse = webClientBuilder.build()
                .get()
                .uri("http://user-service/api/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();

        if (userResponse != null) {
            Post post = Post.builder()
                    .content(postRequest.getContent())
                    .userId(userResponse.getId())
                    .createdAt(LocalDateTime.now())
                    .build();
            return postRepository.save(post);
        } else {

            throw new UserNotFoundException("User not found with id: " + userId);
        }
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post updatePost(Long id, PostRequest updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setContent(updatedPost.getContent());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


}
