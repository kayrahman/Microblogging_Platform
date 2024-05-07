package com.finalproject.followservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponse {

    private Long id;
    private Long followerId;
    private Long followeeId;
   // private String followerUsername;
   // private String followeeUsername;
    private LocalDateTime createdAt;
    private String message;

}
