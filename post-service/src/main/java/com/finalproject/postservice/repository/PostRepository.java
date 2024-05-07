package com.finalproject.postservice.repository;

import com.finalproject.postservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
