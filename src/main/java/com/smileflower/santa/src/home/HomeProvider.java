package com.smileflower.santa.src.home;


import com.smileflower.santa.config.BaseException;
import com.smileflower.santa.config.secret.Secret;
import com.smileflower.santa.src.home.model.*;
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
public class HomeProvider {

    private final HomeDao homeDao;
    private final JwtService jwtService;

    private JdbcTemplate jdbcTemplate;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public HomeProvider(HomeDao homeDao, JwtService jwtService) {

        this.homeDao = homeDao;
        this.jwtService = jwtService;
    }

    public GetHomeRes getHome(int userIdx) throws BaseException {
        GetmyflagMountainRes getmyflagMountainRes = new GetmyflagMountainRes();
        GetHomeRes getHomeRes = new GetHomeRes();
        getHomeRes.setUserIdx(userIdx);
        String userImage=homeDao.getUserImage(userIdx);
        getHomeRes.setUserImage(userImage);
        String homeStatus= "추천 산";
        for(int i=1; i<100; i++) {
            if (homeDao.checkFlagMountain(i)==1 && homeDao.checkFlagUser(userIdx,i)==1) {
                homeStatus= "내가 정복한 산";
                getHomeRes.setHomeStatus(homeStatus);
                int mountainIdx = i;
                List<GetHomeMountainRes> getHomeMountainRes = homeDao.getHomeMountain(userIdx, mountainIdx);
                getmyflagMountainRes.getMountain().add(getHomeMountainRes.get(0));
            }
        }
        for (int i =1; i<5;i++){
            if (homeDao.checkFlagMountain(i)==1 && homeStatus.equals("추천 산")){
                homeStatus= "추천 산";
                getHomeRes.setHomeStatus(homeStatus);
                int mountainIdx = i;
                List<GetHomeMountainRes> getHomeMountainRes = homeDao.getHomeMountain(userIdx, mountainIdx);
                getmyflagMountainRes.getMountain().add(getHomeMountainRes.get(0));
            }
        }

        getHomeRes.setMyflag(getmyflagMountainRes);

        return getHomeRes;

    }

    public int checkFlagMountain(int mountainIdx){
        int exist = homeDao.checkFlagMountain(mountainIdx);
        return exist;
    }

    public int checkFlagUser(int userIdx,int mountainIdx){
        int exist = homeDao.checkFlagUser(userIdx,mountainIdx);
        return exist;
    }

    public String getUserImage(int userIdx){
        String userImage = homeDao.getUserImage(userIdx);
        return userImage;
    }
}