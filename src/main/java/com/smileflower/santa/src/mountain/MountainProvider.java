package com.smileflower.santa.src.mountain;


import com.smileflower.santa.config.BaseException;
import com.smileflower.santa.config.secret.Secret;
import com.smileflower.santa.src.mountain.model.*;
import com.smileflower.santa.utils.AES128;
import com.smileflower.santa.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import javax.sql.DataSource;
import java.util.List;

import static com.smileflower.santa.config.BaseResponseStatus.*;


//Provider : Read의 비즈니스 로직 처리
@Service
public class MountainProvider {

    private final MountainDao mountainDao;
    private final JwtService jwtService;

    private JdbcTemplate jdbcTemplate;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MountainProvider(MountainDao mountainDao, JwtService jwtService) {

        this.mountainDao = mountainDao;
        this.jwtService = jwtService;
    }

    public List<GetMountainRes> getMountain(int userIdx) throws BaseException {
        List<GetMountainRes> getMountainRes = mountainDao.getMountain(userIdx);
        return getMountainRes;
    }
    public GetMountainIdxRes getMountainIdx(int userIdx,String mountain) throws BaseException {
        if(mountainDao.checkMountain(mountain)==1){
            GetMountainIdxRes getMountainIdxRes = mountainDao.getMountainIdx(mountain);
            return getMountainIdxRes;
        }else{
            throw new BaseException(NON_EXIST_MOUNTAIN);
        }
    }
    public GetMapRes getMap(int userIdx,int mountainIdx) throws BaseException {

        GetMapRes getMapRes = mountainDao.getMap(mountainIdx);
        return getMapRes;

    }

    public GetMountainRankRes getMountainRank(int userIdx,int mountainIdx) throws BaseException {
        List<GetRankRes> getRankRes = mountainDao.getRank(mountainIdx);
        GetMountainRankRes getMountainRankRes = new GetMountainRankRes();
        if(mountainDao.checkMyRank(userIdx,mountainIdx)==1){
             GetRankRes getmyRankRes = mountainDao.getmyRank(userIdx,mountainIdx);
             getMountainRankRes.setMyRank(getmyRankRes);
        }
        getMountainRankRes.setAllRank(getRankRes);

        return getMountainRankRes;

    }
    public int checkMyRank(int userIdx,int mountainIdx){
        int exist = mountainDao.checkMyRank(userIdx,mountainIdx);
        return exist;
    }
}