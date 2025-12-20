package com.shortener.URLShortener.Repository;

import com.shortener.URLShortener.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String name);

}
