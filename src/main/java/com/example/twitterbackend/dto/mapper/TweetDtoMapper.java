package com.example.twitterbackend.dto.mapper;

import com.example.twitterbackend.dto.TweetDto;
import com.example.twitterbackend.dto.UserDto;
import com.example.twitterbackend.entity.Tweet;
import com.example.twitterbackend.entity.User;
import com.example.twitterbackend.util.TweetUtil;

import java.util.ArrayList;
import java.util.List;

public class TweetDtoMapper {

    public static TweetDto toTweetDto(Tweet tweet, User reqUser){

        if (reqUser == null) {
            return null;
        }

        UserDto user= UserDtoMapper.toUserDto(tweet.getUser());

        boolean isLiked= TweetUtil.isLikedByReqUser(reqUser, tweet);

        List<Long> retweetUserId= new ArrayList<>();

        for(User user1:tweet.getRetweetUsers()){
            retweetUserId.add(user1.getId());
        }

        TweetDto tweetDto=new TweetDto();
        if(tweet.getId()!=null){   tweetDto.setId(tweet.getId());  }

        tweetDto.setContent(tweet.getContent());
        tweetDto.setCreatedAt(tweet.getCreatedAt());
        tweetDto.setImage(tweet.getImage());
        tweetDto.setTotalLikes(tweet.getLikes().size());
        tweetDto.setTotalReplies(tweet.getReplyTweet().size());
        tweetDto.setTotalRetweets(tweet.getRetweetUsers().size());
        tweetDto.setUser(user);
        tweetDto.setLiked(isLiked);
        tweetDto.setRetweet(tweetDto.isRetweet());
        tweetDto.setRetweetUserId(retweetUserId);
        tweetDto.setReplyTweets(toTweetDtos(tweet.getReplyTweet(), reqUser));
        tweetDto.setVideo(tweet.getVideo());

        return tweetDto;
    }

    public static List<TweetDto> toTweetDtos(List<Tweet> tweets, User reqUser){

        if (reqUser == null) {
            return null;
        }

        List<TweetDto> tweetDtos= new ArrayList<>();

        for(Tweet tweet: tweets){
            TweetDto tweetDto= toReplyTweetDto(tweet, reqUser);
            tweetDtos.add(tweetDto);
        }
        return tweetDtos;
    }

    private static TweetDto toReplyTweetDto(Tweet tweet, User reqUser) {

        if (reqUser == null) {
            return null;
        }
        UserDto user= UserDtoMapper.toUserDto(tweet.getUser());

        boolean isLiked= TweetUtil.isLikedByReqUser(reqUser, tweet);

        List<Long> retweetUserId= new ArrayList<>();

        for(User user1:tweet.getRetweetUsers()){
            retweetUserId.add(user1.getId());
        }

        TweetDto tweetDto=new TweetDto();
        if(tweet.getId()!=null){  tweetDto.setId(tweet.getId());  }

        tweetDto.setContent(tweet.getContent());
        tweetDto.setCreatedAt(tweet.getCreatedAt());
        tweetDto.setImage(tweet.getImage());
        tweetDto.setTotalLikes(tweet.getLikes().size());
        tweetDto.setTotalReplies(tweet.getReplyTweet().size());
        tweetDto.setTotalRetweets(tweet.getRetweetUsers().size());
        tweetDto.setUser(user);
        tweetDto.setLiked(isLiked);
        tweetDto.setRetweet(tweet.isRetweet());
        tweetDto.setRetweetUserId(retweetUserId);
        tweetDto.setReplyTweets(toTweetDtos(tweet.getReplyTweet(), reqUser));
        tweetDto.setVideo(tweet.getVideo());

        return tweetDto;
    }
}
