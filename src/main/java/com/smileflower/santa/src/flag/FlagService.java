package com.smileflower.santa.src.flag;


import com.smileflower.santa.config.BaseException;
import com.smileflower.santa.config.secret.Secret;
import com.smileflower.santa.src.flag.model.*;
import com.smileflower.santa.src.flag.FlagDao;
import com.smileflower.santa.src.flag.FlagProvider;
import com.smileflower.santa.utils.AES128;
import com.smileflower.santa.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;

import static com.smileflower.santa.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class FlagService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FlagDao flagDao;
    private final FlagProvider flagProvider;
    private final JwtService jwtService;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public FlagService(FlagDao flagDao, FlagProvider flagProvider, JwtService jwtService) {
        this.flagDao = flagDao;
        this.flagProvider = flagProvider;
        this.jwtService = jwtService;

    }

    public PostFlagPictureRes createFlag(PostFlagPictureReq postFlagPictureReq, int mountainIdx, int userIdx) throws BaseException {
        if (postFlagPictureReq.getPictureUrl().length() > 0) {
            int flagIdx = flagDao.createFlag(postFlagPictureReq, mountainIdx, userIdx);

            return new PostFlagPictureRes(flagIdx);
        } else {
            throw new BaseException(RESPONSE_ERROR);
        }
    }
}