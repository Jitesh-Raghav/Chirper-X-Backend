package com.example.twitterbackend.dto.mapper;

import com.example.twitterbackend.dto.TweetDto;
import com.example.twitterbackend.dto.UserDto;
import com.example.twitterbackend.entity.Tweet;
import com.example.twitterbackend.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDtoMapper {


    private ModelMapper mapper;

    @Autowired
    public UserDtoMapper(ModelMapper mapper) {
        this.mapper=mapper;
    }


    public static UserDto toUserDto(User user) {

       UserDto userDto = new UserDto();
       userDto.setId(user.getId());
       userDto.setEmail(user.getEmail());
       userDto.setFullName(user.getFullName());
       userDto.setImage(user.getImage());
       userDto.setBackgroundImage(user.getBackgroundImage());
       userDto.setBio(user.getBio());
       userDto.setBirthDate(user.getBirthDate());
       userDto.setFollowers(toUserDtos(user.getFollowers()));
        return userDto;
    }

    private static List<UserDto> toUserDtos(List<User> followers){
        List<UserDto> userDtos= new ArrayList<>();
        for(User user: followers){
            UserDto userDto=new UserDto();
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setFullName(user.getFullName());
            userDto.setImage(user.getImage());
            userDtos.add(userDto);
        }

        return userDtos;
    }

    public TweetDto toTweetDto(Tweet tweet, User reqUser){

        TweetDto tweetDto= mapper.map(tweet, TweetDto.class);
        return tweetDto;
    }

}
