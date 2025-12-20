package com.shortener.URLShortener.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlDto {
    private int urlId;
    private String originalUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private UserDto user;
}
