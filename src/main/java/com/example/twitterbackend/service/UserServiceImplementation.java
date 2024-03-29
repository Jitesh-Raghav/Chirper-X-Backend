package com.example.twitterbackend.service;

import com.example.twitterbackend.config.JwtProvider;
import com.example.twitterbackend.entity.User;
import com.example.twitterbackend.exception.UserException;
import com.example.twitterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserById(Long userId) throws UserException {
       User user= userRepository.findById(userId)
               .orElseThrow(()->new UserException("user not found with id " + userId));
        return user;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email= jwtProvider.getEmailFromToken(jwt);
        User user= userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User not found with email "+ email);
        }
        return user;
    }

    @Override
    public User updateUser(Long userId, User user) throws UserException {
       User existingUser= findUserById(userId);

       if(user.getFullName()!=null){
           existingUser.setFullName(user.getFullName());
       }
       if(user.getImage()!=null){
           existingUser.setImage(user.getImage());
       }
       if(user.getBackgroundImage()!=null){
           existingUser.setBackgroundImage(user.getBackgroundImage());
       }
        if(user.getBirthDate()!=null){
            existingUser.setBirthDate(user.getBirthDate());
        }
        if(user.getLocation()!=null){
            existingUser.setLocation(user.getLocation());
        }
        if(user.getBio()!=null){
            existingUser.setBio(user.getBio());
        }
        if(user.getWebsite()!=null){
            existingUser.setWebsite(user.getWebsite());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public User followUser(Long userId, User user) throws UserException {
        //here the User user, wants to follow the user with user id as 'userId'.

        User followThisUser= findUserById(userId);

        if(user.getFollowing().contains(followThisUser) && followThisUser.getFollowers().contains(user)){
            user.getFollowing().remove(followThisUser);
            followThisUser.getFollowers().remove(user);
        } else{
            user.getFollowing().add(followThisUser);
            followThisUser.getFollowers().add(user);
        }
        userRepository.save(followThisUser);
        userRepository.save(user);

        return followThisUser;
    }

    @Override
    public List<User> searchUser(String query) {

        return userRepository.searchUser(query);
    }
}
