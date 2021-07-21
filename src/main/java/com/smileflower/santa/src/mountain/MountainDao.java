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

    public List<GetMountainRes> getMountain(int userIdx) {
        return this.jdbcTemplate.query("select m.mountainIdx,\n" +
                        "                       m.imageUrl                as mountainImg,\n" +
                        "                       m.name                    as mountainName,\n" +
                        "                       round(avg(d.difficulty))  as difficulty\n" +
                        "                        ,\n" +
                        "                       concat('(', m.high, 'm)') as high,\n" +
                        "                       case when a.hot > 2 then '인기' else null end as hot,\n" +
                        "                       case when b.status = 'T' then 'T' else 'F' end as pick\n" +
                        "                from mountain m\n" +
                        "                         left join difficulty d on m.mountainIdx = d.mountainIdx\n" +
                        "                         left join (select mountainIdx, count(picklistIdx) as hot from picklist group by mountainIdx) a\n" +
                        "                                   on a.mountainIdx = m.mountainIdx\n" +
                        "                left join (select mountainIdx,status from picklist where userIdx =?) b on b.mountainIdx=m.mountainIdx\n" +
                        "                group by m.mountainIdx\n" +
                        "                order by m.mountainIdx",
                (rs, rowNum) -> new GetMountainRes(
                        rs.getInt("mountainIdx"),
                        rs.getString("mountainImg"),
                        rs.getString("mountainName"),
                        rs.getInt("difficulty"),
                        rs.getString("high"),
                        rs.getString("hot"),
                        rs.getString("pick")),
                userIdx);
    }
    public GetMountainIdxRes getMountainIdx(String mountain) {
        return this.jdbcTemplate.queryForObject("select mountainIdx from mountain where mountain.name=?\n",
                (rs, rowNum) -> new GetMountainIdxRes(
                        rs.getInt("mountainIdx")),
                mountain);
    }
    public int checkMountain(String mountain){
        return this.jdbcTemplate.queryForObject("select exists(select mountainIdx from mountain where mountain.name=?)",int.class,mountain);
    }

    public List<GetRoadRes> getRoad(int mountainIdx){
        return this.jdbcTemplate.query("select roadIdx,\n" +
                        "\n" +
                        "       concat(roadIdx,'코스')              as courseNum,\n" +
                        "       road.difficulty,\n" +
                        "       concat(road.length, 'km') as length,\n" +
                        "       road.time as time ,\n" +
                        "       course,\n" +
                        "       road.latitude,\n" +
                        "       road.longitude\n" +
                        "from road\n" +
                        "where mountainIdx = ?;",
                (rs, rowNum) -> new GetRoadRes(
                        rs.getInt("roadIdx"),
                        rs.getString("courseNum"),
                        rs.getInt("difficulty"),
                        rs.getString("length"),
                        rs.getString("time"),
                        rs.getString("course"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")),
                mountainIdx);
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

    public GetInfoRes getInfo(int userIdx, int mountainIdx){
        return this.jdbcTemplate.queryForObject("select m.mountainIdx,\n" +
                        "                       m.imageUrl                as mountainImg,\n" +
                        "                       m.name                    as mountainName,\n" +
                        "       m.address,\n" +
                        "                       round(avg(d.difficulty))  as difficulty\n" +
                        "                        ,\n" +
                        "                       concat(m.high, 'm') as high,\n" +
                        "                       case when a.hot > 2 then '인기' else null end as hot,\n" +
                        "                       case when b.status = 'T' then 'T' else 'F' end as pick\n" +
                        "                from mountain m\n" +
                        "                         inner join difficulty d on m.mountainIdx = d.mountainIdx\n" +
                        "                         left join (select mountainIdx, count(picklistIdx) as hot from picklist group by mountainIdx) a\n" +
                        "                                   on a.mountainIdx = m.mountainIdx\n" +
                        "                left join (select mountainIdx,status from picklist where userIdx =?) b on b.mountainIdx=m.mountainIdx\n" +
                        "                where m.mountainIdx=?;",
                (rs, rowNum) -> new GetInfoRes(
                        rs.getInt("mountainIdx"),
                        rs.getString("mountainImg"),
                        rs.getString("mountainName"),
                        rs.getString("address"),
                        rs.getInt("difficulty"),
                        rs.getString("high"),
                        rs.getString("hot"),
                        rs.getString("pick")),
                userIdx,mountainIdx);
    }

    public GetMapRes getMap(int mountainIdx){
        return this.jdbcTemplate.queryForObject("select  latitude , longitude from mountain where mountainIdx =?",
                (rs, rowNum) -> new GetMapRes(
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")),
                mountainIdx);
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

    public int updateMountainImg(String name,String imageUrl) {
        String query = "update mountain set imageUrl = ? where name = ?";
        Object[] params = new Object[]{imageUrl, name};
        return this.jdbcTemplate.update(query,params);
    }
}