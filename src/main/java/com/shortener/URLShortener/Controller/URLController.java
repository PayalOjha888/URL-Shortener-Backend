package com.shortener.URLShortener.Controller;

import com.shortener.URLShortener.DTO.UrlDto;
import com.shortener.URLShortener.Model.URL;
import com.shortener.URLShortener.Model.User;
import com.shortener.URLShortener.Repository.URLRepo;
import com.shortener.URLShortener.Service.URLService;
import com.shortener.URLShortener.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/url")
@CrossOrigin(origins = "http://localhost:5173")
public class URLController {

    @Autowired
    URLService urlService;

    @Autowired
    UserService userService;

    //get all the urls of logged-in user
    @GetMapping("/all")
    public List<UrlDto> showAllUrls(Principal principal){
        System.out.println("Currently logged in: " + principal.getName());
        User user = userService.findByEmail(principal.getName());
        List<URL> byUser = urlService.findByUser(user);
        return urlService.toUrlDtoList(byUser);
    }

    //controller to get the shortened url
    @PostMapping("/shorten")
    public Map<String, String> shortenUrl(@RequestBody Map<String, String> request, Principal principal) {

        // Get logged-in user
        User user = userService.findByEmail(principal.getName());

        String originalUrl = request.get("originalUrl");

        URL url = urlService.createShortUrl(originalUrl, user);

        Map<String, String> response = new HashMap<>();
        response.put("shortUrl", "http://localhost:8080/api/url/" + url.getShortCode());

        return response;
    }

    //redirect the original url after shortening it
    @GetMapping("/{code}")
    public void redirectToOriginal(@PathVariable String code, HttpServletResponse response) throws IOException {

        URL url = urlService.getByShortCode(code);
        response.sendRedirect(url.getOriginalUrl());

    }

    @DeleteMapping("/{urlId}")
    public Map<String, String> deleteUrl(@PathVariable int urlId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        urlService.deleteUrl(urlId, user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "URL deleted successfully");
        return response;
    }
}
