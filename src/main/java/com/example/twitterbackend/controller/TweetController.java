package com.example.twitterbackend.controller;

import com.example.twitterbackend.dto.TweetDto;
import com.example.twitterbackend.dto.mapper.TweetDtoMapper;
import com.example.twitterbackend.entity.Tweet;
import com.example.twitterbackend.entity.User;
import com.example.twitterbackend.exception.TweetException;
import com.example.twitterbackend.exception.UserException;
import com.example.twitterbackend.requests.TweetReplyRequest;
import com.example.twitterbackend.response.ApiResponse;
import com.example.twitterbackend.service.TweetService;
import com.example.twitterbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<TweetDto> createTweet(@RequestBody Tweet req, @RequestHeader("Authorization") String jwt) throws UserException, TweetException{

        User user= userService.findUserProfileByJwt(jwt);

        Tweet tweet= tweetService.createTweet(req, user);
        TweetDto tweetDto= TweetDtoMapper.toTweetDto(tweet, user);

       return new ResponseEntity<>(tweetDto, HttpStatus.CREATED);
    }

    @PostMapping("/reply")
    public ResponseEntity<TweetDto> replyTweet(@RequestBody TweetReplyRequest req, @RequestHeader("Authorization") String jwt) throws UserException, TweetException{

        User user= userService.findUserProfileByJwt(jwt);

        Tweet tweet= tweetService.createdReply(req, user);
        TweetDto tweetDto= TweetDtoMapper.toTweetDto(tweet, user);

        return new ResponseEntity<>(tweetDto, HttpStatus.CREATED);
    }

    @PutMapping("/{tweetId}/retweet")
    public ResponseEntity<TweetDto> retweet(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt) throws UserException, TweetException{

        User user= userService.findUserProfileByJwt(jwt);

        Tweet tweet= tweetService.retweet(tweetId, user);
        TweetDto tweetDto= TweetDtoMapper.toTweetDto(tweet, user);

        return new ResponseEntity<>(tweetDto, HttpStatus.OK);
    }

    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetDto> findTweetById(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt) throws UserException, TweetException{

        User user= userService.findUserProfileByJwt(jwt);

        Tweet tweet= tweetService.findById(tweetId);
        TweetDto tweetDto= TweetDtoMapper.toTweetDto(tweet, user);

        return new ResponseEntity<>(tweetDto, HttpStatus.OK);
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<ApiResponse> deleteTweet(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt) throws UserException, TweetException{

        User user= userService.findUserProfileByJwt(jwt);

        tweetService.deleteTweetById(tweetId, user.getId());

        ApiResponse res= new ApiResponse("Tweet deleted successfully", true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<TweetDto>> getAllTweets(@RequestHeader("Authorization") String jwt) throws UserException, TweetException{

        User user= userService.findUserProfileByJwt(jwt);

        List<Tweet> tweets= tweetService.findAllTweet();
        List<TweetDto> tweetDtos= TweetDtoMapper.toTweetDtos(tweets, user);

        return new ResponseEntity<>(tweetDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TweetDto>> getUsersAllTweets(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException, TweetException{

        User user= userService.findUserProfileByJwt(jwt);

        List<Tweet> tweets= tweetService.getUserTweet(user);
        List<TweetDto> tweetDtos= TweetDtoMapper.toTweetDtos(tweets, user);

        return new ResponseEntity<>(tweetDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<TweetDto>> findTweetByLikesContainesUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException, TweetException{

        User user= userService.findUserProfileByJwt(jwt);

        List<Tweet> tweets= tweetService.findByLikesContainsUser(user);
        List<TweetDto> tweetDtos= TweetDtoMapper.toTweetDtos(tweets, user);

        return new ResponseEntity<>(tweetDtos, HttpStatus.OK);
    }
}
