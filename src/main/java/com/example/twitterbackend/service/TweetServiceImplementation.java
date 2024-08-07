package com.example.twitterbackend.service;

import com.example.twitterbackend.entity.Tweet;
import com.example.twitterbackend.entity.User;
import com.example.twitterbackend.exception.TweetException;
import com.example.twitterbackend.exception.UserException;
import com.example.twitterbackend.repository.TweetRepository;
import com.example.twitterbackend.requests.TweetReplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetServiceImplementation implements TweetService{

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Tweet createTweet(Tweet req, User user) throws UserException {

        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setImage(req.getImage());
        tweet.setUser(user);
        tweet.setTweet(true);
        tweet.setReply(false);
        tweet.setVideo(req.getVideo());
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findAllTweet() { //homepage wale tweet hai ye...
        return tweetRepository.findAllByIsTweetTrueOrderByCreatedAtDesc();
    }

    @Override
    public Tweet retweet(Long tweetId, User user) throws UserException, TweetException {

       Tweet tweet = findById(tweetId);
       if(tweet.getRetweetUsers().contains(user)){
           tweet.getRetweetUsers().remove(user);
       }else{
           tweet.getRetweetUsers().add(user);
           tweet.setRetweet(true);
       }
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet findById(Long TweetId) throws TweetException {
        Tweet tweet= tweetRepository.findById(TweetId)
                .orElseThrow(()-> new TweetException("Tweet not found with id"+ TweetId));

        return tweet;
    }

    @Override
    public void deleteTweetById(Long tweetId, Long userId) throws UserException, TweetException {

        Tweet tweet= findById(tweetId);

        if(!userId.equals(tweet.getUser().getId())){
            throw new UserException("you can't delete another user's tweet");
        }
        tweetRepository.deleteById(tweet.getId());
    }

    @Override
    public Tweet removeFromRetweet(Long tweetId, User user) throws UserException, TweetException {
        return null;
    }

    @Override
    public Tweet createdReply(TweetReplyRequest req, User user) throws TweetException {

        Tweet existingTweet = findById(req.getTweetId());

        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setImage(req.getImage());
        tweet.setUser(user);
        tweet.setTweet(false);
        tweet.setReply(true);
        tweet.setReplyFor(existingTweet);  //i.e. we have created this reply for the 'existingTweet'

        Tweet savedReply= tweetRepository.save(tweet);
       // tweet.getReplyTweet().add(savedReply);
        existingTweet.getReplyTweet().add(savedReply);

        tweetRepository.save(existingTweet);

        return existingTweet;
    }

    @Override
    public List<Tweet> getUserTweet(User user) {

        return tweetRepository.findByRetweetUsersContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(user, user.getId());
    }

    @Override
    public List<Tweet> findByLikesContainsUser(User user) {
        return tweetRepository.findByLikesUser_id(user.getId());
    }
}
