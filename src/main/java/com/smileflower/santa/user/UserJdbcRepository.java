package com.smileflower.santa.user;


import com.smileflower.santa.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserJdbcRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {

    }

    @Override
    public Optional<User> findByIdx(Long idx) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
