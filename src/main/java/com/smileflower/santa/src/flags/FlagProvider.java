package com.smileflower.santa.src.flags;


import com.smileflower.santa.config.BaseException;
import com.smileflower.santa.config.secret.Secret;
import com.smileflower.santa.src.flags.model.*;
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

import javax.transaction.Transactional;
//Provider : Read의 비즈니스 로직 처리
@Service
public class FlagProvider {

    private final FlagDao flagDao;
    private final JwtService jwtService;

    private JdbcTemplate jdbcTemplate;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FlagProvider(FlagDao flagDao, JwtService jwtService) {

        this.flagDao = flagDao;
        this.jwtService = jwtService;
    }
    public int checkMountain(String mountain){
        int exist = flagDao.checkMountain(mountain);
        return exist;
    }

    @Transactional
    public GetFlagRes getFlag(int userIdx, String mountain) throws BaseException {
        if (flagDao.checkMountain(mountain)==1){
            int mountainIdx = flagDao.checkMountain(mountain);
            GetFlagRes getFlagRes = new GetFlagRes();
            getFlagRes.setMountainIdx(mountainIdx);
            getFlagRes.setMountain(mountain);
            return getFlagRes;
        }
        else{
            throw new BaseException(NON_EXIST_MOUNTAIN);
        }

    }
    public GetFlagRankRes getFlagRank(int userIdx, int mountainIdx) throws BaseException {
        GetFlagRankRes getFlagRankRes =new GetFlagRankRes();
        for(int i=0;i<2;i++){
            if(i==0){
                GetRankRes getRankRes= flagDao.getfirstRank(mountainIdx);
                getFlagRankRes.setFirstRank(getRankRes);
            }
            else {
                GetRankRes getRankRes= flagDao.getmyRank(userIdx,mountainIdx);
                getFlagRankRes.setMyRank(getRankRes);
            }
        }
        return getFlagRankRes;
    }

    public List<GetPickRes> getPick(int userIdx) throws BaseException {
        List<GetPickRes> getPickRes= flagDao.getPick(userIdx);
        return getPickRes;
    }

    public char checkPick(int userIdx, int mountainIdx){
        return flagDao.checkPick(userIdx,mountainIdx);
    }
}