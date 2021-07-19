package com.smileflower.santa.flag.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
@Repository
public class FlagJdbcRepository implements FlagRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int updateImageUrlByIdx(int userIdx, Long mountainIdx, String filename) {
        String query = "insert into flag (userIdx, mountainIdx,pictureUrl) VALUES (?,?,?)";
        Object[] params = new Object[]{userIdx,mountainIdx,filename};
        this.jdbcTemplate.update(query, params);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }
}
