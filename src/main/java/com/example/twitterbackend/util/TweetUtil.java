package com.example.twitterbackend.util;

import com.example.twitterbackend.entity.Like;
import com.example.twitterbackend.entity.Tweet;
import com.example.twitterbackend.entity.User;

public class TweetUtil {
    public final static boolean isLikedByReqUser(User reqUser, Tweet tweet){

        for(Like like: tweet.getLikes()) {
            if(like.getUser().getId().equals(reqUser.getId())){
                return true;
            }
        }
        return false;
    }

    public final static boolean isRetweetByReqUser(User reqUser, Tweet tweet){

        for(User user: tweet.getRetweetUsers()){
            if(user.getId().equals(reqUser.getId())) {
                return true;
            }
        }
        return false;
    }
}
