package com.shortener.URLShortener.Repository;

import com.shortener.URLShortener.Model.URL;
import com.shortener.URLShortener.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface URLRepo extends JpaRepository<URL, Integer> {

    List<URL> findAllByUser(User user);

    URL findByShortCode(String code);

    URL findByUrlIdAndUser(int urlId, User user);

}
