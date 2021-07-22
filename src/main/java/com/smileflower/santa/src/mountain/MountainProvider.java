package com.smileflower.santa.src.mountain;


import com.smileflower.santa.config.BaseException;
import com.smileflower.santa.src.mountain.model.*;
import com.smileflower.santa.utils.JwtService;
import com.smileflower.santa.utils.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.smileflower.santa.config.BaseResponseStatus.*;


//Provider : Read의 비즈니스 로직 처리
@Service
public class MountainProvider {

    private final MountainDao mountainDao;
    private final JwtService jwtService;
    private final S3Service s3Service;

    private JdbcTemplate jdbcTemplate;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MountainProvider(MountainDao mountainDao, JwtService jwtService, S3Service s3Service) {

        this.mountainDao = mountainDao;
        this.jwtService = jwtService;
        this.s3Service = s3Service;
    }

    public List<GetMountainRes> getMountain(int userIdx) throws BaseException {
        List<GetMountainRes> getMountainRes = mountainDao.getMountain(userIdx);
        for(int i=0;i<getMountainRes.size();i++){
            if(getMountainRes.get(i).getMountainImg()!=null)
                getMountainRes.get(i).setMountainImg(s3Service.getFileUrl(getMountainRes.get(i).getMountainImg()));
        }
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
    public GetMapRoadRes getMap(int userIdx,int mountainIdx) throws BaseException {
        List<GetRoadRes> getRoadRes = mountainDao.getRoad(mountainIdx);
        GetMapRes getMapRes = mountainDao.getMap(mountainIdx);
        GetMapRoadRes getMapRoadRes = new GetMapRoadRes();
        getMapRoadRes.setRoad(getRoadRes);
        getMapRoadRes.setMountain(getMapRes);
        return getMapRoadRes;

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

    public GetInfoPage getMountainInfo(int userIdx,int mountainIdx) throws BaseException {
        List<GetRoadRes> getRoadRes = mountainDao.getRoad(mountainIdx);
        GetInfoPage getInfoPage = new GetInfoPage();

        GetInfoRes getInfoRes = mountainDao.getInfo(userIdx,mountainIdx);
        if(getInfoRes.getMountainImg()!=null)
            getInfoRes.setMountainImg(s3Service.getFileUrl(getInfoRes.getMountainImg()));

        getInfoPage.setInfo(getInfoRes);

        getInfoPage.setRoad(getRoadRes);

        return getInfoPage;

    }
}