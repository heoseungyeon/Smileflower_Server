package com.smileflower.santa.profile.repository;


import com.smileflower.santa.profile.model.domain.Email;
import com.smileflower.santa.profile.model.domain.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class ProfileJdbcRepository implements ProfileRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        String query = "select * from user where emailId =?";
        String param = email;
        List<Profile> user = this.jdbcTemplate.query(query,
                (rs, rowNum) -> new Profile(
                        rs.getLong("userIdx"),
                        new Email(rs.getString("emailId")),
                        null,
                        rs.getString("userImageUrl"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime(),
                        rs.getString("name")),
                param);

        return ofNullable(user.isEmpty() ? null : user.get(0));
    }

    @Override
    public int updateImageUrlByEmail(String email, String filename) {
        String query = "update user set userImageUrl = ? where emailId = ? ";
        Object[] params = new Object[]{filename, email};
        return this.jdbcTemplate.update(query,params);
    }

    @Override
    public int deleteImageUrlByEmail(String email) {
        String query = "update user set userImageUrl = null where emailId = ? ";
        String param = email;
        return this.jdbcTemplate.update(query,param);
    }
}
