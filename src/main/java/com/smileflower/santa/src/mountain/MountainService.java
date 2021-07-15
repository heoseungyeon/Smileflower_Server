package com.smileflower.santa.src.mountain;


import com.smileflower.santa.config.BaseException;
import com.smileflower.santa.config.secret.Secret;
import com.smileflower.santa.src.mountain.model.*;
import com.smileflower.santa.src.mountain.MountainDao;
import com.smileflower.santa.src.mountain.MountainProvider;
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
public class MountainService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MountainDao mountainDao;
    private final MountainProvider mountainProvider;
    private final JwtService jwtService;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public MountainService(MountainDao mountainDao, MountainProvider mountainProvider, JwtService jwtService) {
        this.mountainDao = mountainDao;
        this.mountainProvider = mountainProvider;
        this.jwtService = jwtService;

    }
}