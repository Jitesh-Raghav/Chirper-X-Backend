package com.example.twitterbackend.controller;

import com.example.twitterbackend.config.JwtProvider;
import com.example.twitterbackend.entity.User;
import com.example.twitterbackend.entity.Verification;
import com.example.twitterbackend.exception.UserException;
import com.example.twitterbackend.repository.UserRepository;
import com.example.twitterbackend.response.AuthResponse;
import com.example.twitterbackend.service.CustomUserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://chirper-x.vercel.app")
@RequestMapping("/auth")
public class AuthController {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;
    private CustomUserDetailsServiceImplementation customUserDetails;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, CustomUserDetailsServiceImplementation customUserDetails) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.customUserDetails = customUserDetails;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

        String fullName= user.getFullName();
        String email = user.getEmail();
        String password= user.getPassword();
        String birthDate = user.getBirthDate();

        User isEmailExist = userRepository.findByEmail(email);

        if(isEmailExist!=null){
           throw new UserException("Email id is already in use, try using some other email.");
        }

        User createdUser= new User();
        createdUser.setFullName(fullName);
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setBirthDate(birthDate);
        createdUser.setVerification(new Verification());
       User savedUser= userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse response= new AuthResponse(token, true);

        return new ResponseEntity<AuthResponse>(response, HttpStatus.CREATED);
    }


   @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody User user) throws UserException{

       String username= user.getEmail();
       String password= user.getPassword();

       Authentication authentication = Authenticate(username, password);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse response= new AuthResponse(token, true);

        return new ResponseEntity<AuthResponse>(response, HttpStatus.ACCEPTED);
    }

    private Authentication Authenticate(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("Invaild Username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invaild Username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
