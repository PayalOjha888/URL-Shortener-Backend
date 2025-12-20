package com.shortener.URLShortener.Service;

import com.shortener.URLShortener.DTO.UrlDto;
import com.shortener.URLShortener.DTO.UserDto;
import com.shortener.URLShortener.Model.URL;
import com.shortener.URLShortener.Model.User;
import com.shortener.URLShortener.Repository.URLRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class URLService {

    @Autowired
    URLRepo repo;


    public List<URL> findByUser(User user) {
        return repo.findAllByUser(user);
    }

    public URL createShortUrl(String originalUrl, User user) {
           URL url = new URL();
           url.setOriginalUrl(originalUrl);
           url.setShortCode(generateShortCode());
           url.setUser(user);
           url.setCreatedAt(LocalDateTime.now());
           return repo.save(url);
    }

    private String generateShortCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for(int i=0 ; i < 6 ; i++){
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }


    public URL getByShortCode(String code) {
        return repo.findByShortCode(code);
    }

    private UserDto toUserDto(User user){
        UserDto dto = new UserDto();
        dto.setUserid(user.getUserid());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        return dto;
    }

    public UrlDto toUrlDto(URL url){
        UrlDto dto = new UrlDto();
        dto.setUrlId(url.getUrlId());
        dto.setOriginalUrl(url.getOriginalUrl());
        dto.setShortCode(url.getShortCode());
        dto.setCreatedAt(url.getCreatedAt());
        dto.setUser(toUserDto(url.getUser()));
        return dto;
    }

    public List<UrlDto> toUrlDtoList(List<URL> urls){
        return urls.stream().map(this::toUrlDto).collect(Collectors.toList());
    }

    public void deleteUrl(int urlId, User user) {
        URL url = repo.findByUrlIdAndUser(urlId, user);
        if (url != null) {
            repo.delete(url);
        } else {
            throw new RuntimeException("URL not found or you do not have permission to delete it");
        }
    }
}
