package com.finalproject.followservice.controller;

import com.finalproject.followservice.dto.FollowRequest;
import com.finalproject.followservice.dto.FollowResponse;
import com.finalproject.followservice.dto.UserResponse;
import com.finalproject.followservice.model.Follow;
import com.finalproject.followservice.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
@AllArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<FollowResponse> followUser(@RequestBody FollowRequest followRequest) {
        try {
            Follow follow = followService.createFollow(followRequest);
            FollowResponse response = FollowResponse.builder()
                    .id(follow.getId())
                    .followerId(followRequest.getFollowerId())
                    .followeeId(followRequest.getFolloweeId())
                    .createdAt(follow.getCreatedAt())
                    .message("Follow created successfully")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            FollowResponse errorResponse = FollowResponse.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


    @DeleteMapping("/{followerId}/{followeeId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long followerId, @PathVariable Long followeeId) {
        try {
            followService.deleteFollow(followerId, followeeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

   /* @GetMapping("/followers/{userId}")
    public ResponseEntity<String> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok("Endpoint is reachable");
    }*/

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<UserResponse>> getFollowers(@PathVariable Long userId) {
        List<UserResponse> followers = followService.getFollowers(userId);
        if (followers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserResponse>> getFollowing(@PathVariable Long userId) {
        List<UserResponse> followings = followService.getFollowing(userId);
        if (followings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(followings, HttpStatus.OK);
    }
}
