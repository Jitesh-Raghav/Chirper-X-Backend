package com.example.twitterbackend.repository;

import com.example.twitterbackend.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
