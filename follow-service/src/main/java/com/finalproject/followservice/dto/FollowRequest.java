package com.finalproject.followservice.dto;

import lombok.Data;

@Data
public class FollowRequest {
    private Long followerId; // ID of the user who initiates the follow
    private Long followeeId; // ID of the user to be followed
}
