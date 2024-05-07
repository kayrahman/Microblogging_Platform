package com.finalproject.followservice.service;


import com.finalproject.followservice.dto.FollowRequest;
import com.finalproject.followservice.dto.FollowResponse;
import com.finalproject.followservice.dto.UserResponse;
import com.finalproject.followservice.model.Follow;
import com.finalproject.followservice.repository.FollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final WebClient.Builder webClientBuilder;

    @Transactional
    public Follow createFollow(FollowRequest followRequest) {
        if (followRequest.getFollowerId().equals(followRequest.getFolloweeId())) {
            throw new IllegalArgumentException("User cannot follow themselves.");
        }

        boolean exists = followRepository.existsByFollowerIdAndFolloweeId(followRequest.getFollowerId(), followRequest.getFolloweeId());
        if (exists) {
            throw new IllegalStateException("Follow relationship already exists.");
        }

        Follow follow = new Follow();
        follow.setFollowerId(followRequest.getFollowerId());
        follow.setFolloweeId(followRequest.getFolloweeId());
        follow.setCreatedAt(LocalDateTime.now());
        return followRepository.save(follow);
    }

    @Transactional
    public void deleteFollow(Long followerId, Long followeeId) {
        Follow follow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                .orElseThrow(() -> new IllegalArgumentException("Follow relationship not found."));
        followRepository.delete(follow);
    }

    public List<UserResponse> getFollowers(Long userId) {
        System.out.println("FETCHING FOLLOWERS FOR: " + userId);
        List<Long> followerIds = followRepository.findByFolloweeId(userId)
                .stream()
                .map(Follow::getFollowerId)
                .collect(Collectors.toList());

        System.out.println("FOLLOWERS IDS: " + followerIds);
        System.out.println("FETCH USER DETAIL: " + fetchUserDetails(Long.valueOf(2)));

        return followerIds.stream()
                .map(this::fetchUserDetails)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getFollowing(Long userId) {
        List<Long> followingIds = followRepository.findByFollowerId(userId)
                .stream()
                .map(Follow::getFolloweeId)
                .collect(Collectors.toList());
        return followingIds.stream()
                .map(this::fetchUserDetails)
                .collect(Collectors.toList());
    }

    private UserResponse fetchUserDetails(Long userId) {
        return webClientBuilder.build()
                .get()
                .uri("http://user-service/api/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block(); // Consider using non-blocking reactive approach
    }
}

