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
    public int createHard(PostFlagHardReq postFlagHardReq,int mountainIdx,int userIdx){
        this.jdbcTemplate.update("insert into difficulty (userIdx,mountainIdx,difficulty) Values (?,?,?)",
                userIdx,mountainIdx,postFlagHardReq.getDifficulty()
        );
        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }

    public GetRankRes getmyRank(int userIdx,int mountainIdx) {
        return this.jdbcTemplate.queryForObject("select * from (select row_number() over (order by COUNT(f.userIdx) desc) as ranking,\n" +
                        "       m.mountainIdx,\n" +
                        "       f.userIdx,\n" +
                        "       u.name as userName,\n" +
                        "       u.userImageUrl   as userImage,\n" +
                        "       COUNT(f.userIdx) as flagCount\n" +
                        "from flag f\n" +
                        "         inner join mountain m on f.mountainIdx = m.mountainIdx\n" +
                        "         inner join user u on f.userIdx = u.userIdx\n" +
                        "where f.mountainIdx = ?\n" +
                        "group by f.userIdx\n" +
                        "order by flagCount desc)a where userIdx = ?;",
                (rs, rowNum) -> new GetRankRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("userImage"),
                        rs.getInt("ranking"),
                        rs.getInt("flagCount")),
                mountainIdx, userIdx);
    }

    public GetRankRes getfirstRank(int mountainIdx) {
        return this.jdbcTemplate.queryForObject("select * from (select row_number() over (order by COUNT(f.userIdx) desc) as ranking,\n" +
                        "       m.mountainIdx,\n" +
                        "       f.userIdx,\n" +
                        "       u.name as userName,\n" +
                        "       u.userImageUrl   as userImage,\n" +
                        "       COUNT(f.userIdx) as flagCount\n" +
                        "from flag f\n" +
                        "         inner join mountain m on f.mountainIdx = m.mountainIdx\n" +
                        "         inner join user u on f.userIdx = u.userIdx\n" +
                        "where f.mountainIdx = ?\n" +
                        "group by f.userIdx\n" +
                        "order by flagCount desc)a where ranking = 1;",
                (rs, rowNum) -> new GetRankRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("userImage"),
                        rs.getInt("ranking"),
                        rs.getInt("flagCount")),
                mountainIdx);
    }
}