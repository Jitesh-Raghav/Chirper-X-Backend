package com.example.twitterbackend.dto.mapper;

import com.example.twitterbackend.dto.TweetDto;
import com.example.twitterbackend.dto.UserDto;
import com.example.twitterbackend.entity.Tweet;
import com.example.twitterbackend.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {


    private ModelMapper mapper;

    @Autowired
    public UserDtoMapper(ModelMapper mapper) {
        this.mapper=mapper;
    }


    public UserDto toUserDto(User user) {

        UserDto userDto =  mapper.map(user, UserDto.class);
        return userDto;
    }

    public TweetDto toTweetDto(Tweet tweet, User reqUser){

        TweetDto tweetDto= mapper.map(tweet, TweetDto.class);
        return tweetDto;
    }

}
