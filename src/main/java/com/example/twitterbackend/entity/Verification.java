package com.example.twitterbackend.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Verification {

    private boolean Status;        //default value is false.
    private LocalDateTime startedAt;
    private LocalDateTime endsAt;
    private String planType;
}
