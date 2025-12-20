package com.shortener.URLShortener.Service;

import com.shortener.URLShortener.Model.User;
import com.shortener.URLShortener.Repository.UserRepo;
import com.shortener.URLShortener.config.JwtUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
    }


    public ResponseEntity<String> verifyUser(User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(authentication.isAuthenticated()){
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(token);
        }
        return new ResponseEntity<>("Invalid Credentials!", HttpStatus.UNAUTHORIZED);
    }

    public User findByEmail(String name) {
        return repo.findByEmail(name);
    }
}
