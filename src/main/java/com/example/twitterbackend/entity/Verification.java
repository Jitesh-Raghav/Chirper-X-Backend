package com.example.twitterbackend.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Verification {

    private boolean Status= false;
    private LocalDateTime startedAt;
    private LocalDateTime endsAt;
    private String planType;
}
