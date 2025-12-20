package com.shortener.URLShortener.Controller;

import com.shortener.URLShortener.Model.User;
import com.shortener.URLShortener.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shortify/")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userservice;

    @PostMapping("/register")
    public void RegisterUser(@RequestBody User user){
        userservice.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        return userservice.verifyUser(user);
    }
    
}
