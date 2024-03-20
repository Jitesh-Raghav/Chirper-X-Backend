package com.example.twitterbackend.repository;

import com.example.twitterbackend.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l from Like l where l.user.id=:userId AND l.tweet.id=:tweetId")
    public Like isLikeExist(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Query("SELECT l from Like l where l.tweet.id=:tweetId")
    public List<Like> findByTweetId(@Param("tweetId") Long tweetId);
}
