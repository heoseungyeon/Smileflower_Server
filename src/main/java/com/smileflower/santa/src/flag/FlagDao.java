package com.smileflower.santa.src.flag;


import com.smileflower.santa.src.flag.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FlagDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public int checkMountain(String mountain){
        return this.jdbcTemplate.queryForObject("select exists(select * from mountain where name=?)",int.class,mountain);
    }
    public int checkMountainIdx(String mountain){
        return this.jdbcTemplate.queryForObject("select mountainIdx from mountain where name = ?",int.class,mountain);
    }
    public int createFlag(PostFlagPictureReq postFlagPictureReq,int mountainIdx,int userIdx){
        this.jdbcTemplate.update("insert into flag (userIdx,mountainIdx,pictureUrl) Values (?,?,?)",
                userIdx,mountainIdx,postFlagPictureReq.getPictureUrl()
        );
        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }
}