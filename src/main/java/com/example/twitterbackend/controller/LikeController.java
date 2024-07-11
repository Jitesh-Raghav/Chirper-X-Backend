package com.example.twitterbackend.controller;

import com.example.twitterbackend.dto.LikeDto;
import com.example.twitterbackend.dto.mapper.LikeDtoMapper;
import com.example.twitterbackend.entity.Like;
import com.example.twitterbackend.entity.User;
import com.example.twitterbackend.exception.TweetException;
import com.example.twitterbackend.exception.UserException;
import com.example.twitterbackend.service.LikeService;
import com.example.twitterbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://chirper-x.vercel.app")
@RequestMapping("/api")
public class LikeController {

    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;

    @PostMapping("/{tweetId}/likes")
    public ResponseEntity<LikeDto> likeTweet(@PathVariable Long tweetId, @RequestHeader("Authorization")String jwt) throws UserException, TweetException {

        User user= userService.findUserProfileByJwt(jwt);
        Like like= likeService.likeTweet(tweetId, user);

        LikeDto likeDto= LikeDtoMapper.toLikeDto(like, user);

    return new ResponseEntity<LikeDto>(likeDto, HttpStatus.CREATED);
    }

    @PostMapping("/tweet/{tweetId}")
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long tweetId, @RequestHeader("Authorization")String jwt) throws UserException, TweetException {

        User user= userService.findUserProfileByJwt(jwt);
        List<Like> likes= likeService.getAllLikes(tweetId);

        List<LikeDto> likeDtos= LikeDtoMapper.toLikeDtos(likes, user);

        return new ResponseEntity<>(likeDtos, HttpStatus.CREATED);
    }
}
