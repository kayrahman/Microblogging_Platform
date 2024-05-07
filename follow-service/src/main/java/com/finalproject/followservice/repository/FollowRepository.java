package com.finalproject.followservice.repository;

import com.finalproject.followservice.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // Find all follow relationships by follower ID
    List<Follow> findByFollowerId(Long followerId);

    // Find all follow relationships by followee ID
    List<Follow> findByFolloweeId(Long followeeId);

    // Optional method to check if a specific follow relationship exists
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
