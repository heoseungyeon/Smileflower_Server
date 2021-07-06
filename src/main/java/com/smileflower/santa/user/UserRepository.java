package com.smileflower.santa.user;

import com.smileflower.santa.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findByIdx(Long idx);
    List<User> findAll();
}