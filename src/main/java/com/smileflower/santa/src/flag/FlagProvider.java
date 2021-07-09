package com.smileflower.santa.src.flag;


import com.smileflower.santa.config.BaseException;
import com.smileflower.santa.config.secret.Secret;
import com.smileflower.santa.src.flag.model.*;
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
    public GetFlagRes getFlag(int userIdx, String mountain) throws BaseException {
        if (checkMountain(mountain)==1){
            GetFlagRes getFlagRes = new GetFlagRes();
            getFlagRes.setMountain(mountain);
            return getFlagRes;
        }
        else{
            throw new BaseException(NON_EXIST_MOUNTAIN);
        }

    }

    public int checkMountain(String mountain){
        int exist = flagDao.checkMountain(mountain);
        return exist;
    }
}