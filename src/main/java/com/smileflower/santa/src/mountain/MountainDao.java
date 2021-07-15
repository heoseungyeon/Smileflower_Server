package com.smileflower.santa.src.mountain;


import com.smileflower.santa.src.mountain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MountainDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetMountainRes> getMountain() {
        return this.jdbcTemplate.query("select m.mountainIdx, m.imageUrl as mountainImg, m.name as mountainName, round(avg(d.difficulty)) as difficulty\n" +
                        ",concat('(',m.high,'m)') as high\n" +
                        "from mountain m\n" +
                        "         inner join difficulty d on m.mountainIdx = d.mountainIdx\n" +
                        "group by m.mountainIdx order by m.mountainIdx;",
                (rs, rowNum) -> new GetMountainRes(
                        rs.getInt("mountainIdx"),
                        rs.getString("mountainImg"),
                        rs.getString("mountainName"),
                        rs.getInt("difficulty"),
                        rs.getString("high"))
        );
    }

    public List<GetRankRes> getRank(int mountainIdx){
        return this.jdbcTemplate.query("select row_number() over (order by COUNT(f.userIdx) desc, f.createdAt desc) as ranking,\n" +
                        "                       f.userIdx,\n" +
                        "                       u.name as userName,\n" +
                        "                       u.userImageUrl   as userImage,\n" +
                        "                       COUNT(f.userIdx) as flagCount\n" +
                        "                      ,case\n" +
                        "           when minute(timediff(now(), max(f.createdAt))) < 1 then concat(minute(timediff(now(), max(f.createdAt))), '분전')\n" +
                        "           when hour(timediff(now(), max(f.createdAt))) < 24 then concat(hour(timediff(now(), max(f.createdAt))), '시간전')\n" +
                        "           else concat(day(timediff(now(), max(f.createdAt))), '일전') end       agoTime\n" +
                        "\n" +
                        "                from flag f\n" +
                        "                         inner join mountain m on f.mountainIdx = m.mountainIdx\n" +
                        "                         inner join user u on f.userIdx = u.userIdx\n" +
                        "                where f.mountainIdx = ?" +
                        "\n" +
                        "                group by f.userIdx\n" +
                        "                order by ranking;",
                (rs, rowNum) -> new GetRankRes(
                        rs.getInt("ranking"),
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("userImage"),
                        rs.getInt("flagCount"),
                        rs.getString("agoTime")),
                mountainIdx);
    }
    public GetRankRes getmyRank(int userIdx, int mountainIdx){
        return this.jdbcTemplate.queryForObject("select * from (select row_number() over (order by COUNT(f.userIdx) desc, f.createdAt desc) as ranking,\n" +
                        "                       f.userIdx,\n" +
                        "                       u.name as userName,\n" +
                        "                       u.userImageUrl   as userImage,\n" +
                        "                       COUNT(f.userIdx) as flagCount\n" +
                        "                      ,case\n" +
                        "           when minute(timediff(now(), max(f.createdAt))) < 1 then concat(minute(timediff(now(), max(f.createdAt))), '분전')\n" +
                        "           when hour(timediff(now(), max(f.createdAt))) < 24 then concat(hour(timediff(now(), max(f.createdAt))), '시간전')\n" +
                        "           else concat(day(timediff(now(), max(f.createdAt))), '일전') end       agoTime\n" +
                        "\n" +
                        "                from flag f\n" +
                        "                         inner join mountain m on f.mountainIdx = m.mountainIdx\n" +
                        "                         inner join user u on f.userIdx = u.userIdx\n" +
                        "                where f.mountainIdx =?\n" +
                        "                group by f.userIdx\n" +
                        "                order by ranking)a where userIdx=?\n",
                (rs, rowNum) -> new GetRankRes(
                        rs.getInt("ranking"),
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("userImage"),
                        rs.getInt("flagCount"),
                        rs.getString("agoTime")),
                mountainIdx,userIdx);
    }
    public int checkMyRank(int userIdx,int mountainIdx){
        return this.jdbcTemplate.queryForObject("select exists(select * from (select row_number() over (order by COUNT(f.userIdx) desc, f.createdAt desc) as ranking,\n" +
                "                       f.userIdx,\n" +
                "                       u.name as userName,\n" +
                "                       u.userImageUrl   as userImage,\n" +
                "                       COUNT(f.userIdx) as flagCount\n" +
                "                      ,case when timediff(now(),f.createdAt)<1 then concat(minute(timediff(now(),f.createdAt)),'분전')\n" +
                "when timediff(now(),f.createdAt)<24 then concat(hour(timediff(now(),f.createdAt)),'시간전') else concat(day(timediff(now(),f.createdAt)),'일전') end agoTime\n" +
                "\n" +
                "                from flag f\n" +
                "                         inner join mountain m on f.mountainIdx = m.mountainIdx\n" +
                "                         inner join user u on f.userIdx = u.userIdx\n" +
                "                where f.mountainIdx = ?\n" +
                "                group by f.userIdx\n" +
                "                order by ranking)a where userIdx=?)",int.class,mountainIdx,userIdx);
    }
}