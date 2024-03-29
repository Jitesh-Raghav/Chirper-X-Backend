package com.example.twitterbackend.repository;

import com.example.twitterbackend.entity.Tweet;
import com.example.twitterbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findAllByIsTweetTrueOrderByCreatedAtDesc();

    //To get user ne jitne bhi tweet retweet kiye hai...
    List<Tweet> findByRetweetUsersContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(User user, Long userId);

    List<Tweet> findByLikesContainingOrderByCreatedAtDesc(User user);

    @Query("SELECT t From Tweet t JOIN t.likes l where l.user.id=:userId")
    List<Tweet> findByLikesUser_id(Long userId);


}
