package com.example.twitterbackend.service;

import com.example.twitterbackend.entity.Tweet;
import com.example.twitterbackend.entity.User;
import com.example.twitterbackend.exception.TweetException;
import com.example.twitterbackend.exception.UserException;
import com.example.twitterbackend.repository.TweetRepository;
import com.example.twitterbackend.requests.TweetReplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetServiceImplementation implements TweetService{

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Tweet createTweet(Tweet req, User user) throws UserException {
        return null;
    }

    @Override
    public List<Tweet> findAllTweet() {
        return null;
    }

    @Override
    public Tweet retweet(Long tweetId, User user) throws UserException, TweetException {
        return null;
    }

    @Override
    public Tweet findById(Long TweetId) throws TweetException {
        return null;
    }

    @Override
    public void deleteTweetById(Long tweetId, Long userId) throws UserException, TweetException {

    }

    @Override
    public Tweet removeFromRetweet(Long tweetId, User user) throws UserException, TweetException {
        return null;
    }

    @Override
    public Tweet createdReply(TweetReplyRequest req, User user) throws TweetException {
        return null;
    }

    @Override
    public List<Tweet> getUserTweet(User user) {
        return null;
    }

    @Override
    public List<Tweet> findByLikesContainsUser(User user) {
        return null;
    }
}
