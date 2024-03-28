package com.example.twitterbackend.service;

import com.example.twitterbackend.entity.Like;
import com.example.twitterbackend.entity.Tweet;
import com.example.twitterbackend.entity.User;
import com.example.twitterbackend.exception.TweetException;
import com.example.twitterbackend.exception.UserException;
import com.example.twitterbackend.repository.LikeRepository;
import com.example.twitterbackend.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImplementation implements LikeService{

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private TweetService tweetService;

    @Override
    public Like likeTweet(Long tweetId, User user) throws UserException, TweetException {

        Like isLikeExist= likeRepository.isLikeExist(user.getId(), tweetId);

        if(isLikeExist!=null){
            likeRepository.deleteById(isLikeExist.getId());
            return isLikeExist;
        }
        Tweet tweet = tweetService.findById(tweetId);

        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(user);

        Like savedLike= likeRepository.save(like);
        tweet.getLikes().add(savedLike);
        tweetRepository.save(tweet);

        return savedLike;
    }

    @Override
    public List<Like> getAllLikes(Long tweetId) throws TweetException {

        Tweet tweet= tweetService.findById(tweetId);
        List<Like> likes= likeRepository.findByTweetId(tweetId);
        return likes;
    }
}
