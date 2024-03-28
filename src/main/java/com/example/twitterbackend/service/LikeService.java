package com.example.twitterbackend.service;

import com.example.twitterbackend.entity.Like;
import com.example.twitterbackend.entity.User;
import com.example.twitterbackend.exception.TweetException;
import com.example.twitterbackend.exception.UserException;

import java.util.List;

public interface LikeService {

    public Like likeTweet(Long tweetId, User user) throws UserException, TweetException;

    public List<Like> getAllLikes(Long tweetId) throws TweetException;

}
