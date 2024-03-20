package com.example.twitterbackend.service;

import com.example.twitterbackend.entity.Tweet;
import com.example.twitterbackend.entity.User;
import com.example.twitterbackend.exception.TweetException;
import com.example.twitterbackend.exception.UserException;

import java.util.List;

public interface TweetService {

    public Tweet createTweet(Tweet req, User user) throws UserException;
    public List<Tweet> findAllTweet();
    public Tweet retweet(Long tweetId, User user) throws UserException, TweetException;
    public Tweet findById(Long TweetId) throws TweetException;
    public void deleteTweetById(Long tweetId, Long userId) throws UserException, TweetException;
    public Tweet removeFromRetweet(Long tweetId, User user) throws UserException, TweetException;

}
