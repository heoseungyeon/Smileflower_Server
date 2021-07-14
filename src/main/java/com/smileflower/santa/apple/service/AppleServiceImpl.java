package com.smileflower.santa.apple.service;

import com.smileflower.santa.apple.model.AppleToken;
import com.smileflower.santa.apple.utils.AppleJwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AppleServiceImpl implements AppleService {

    @Autowired
    AppleJwtUtils appleJwtUtils;

    /**
     * 유효한 id_token인 경우 client_secret 생성
     *
     * @param id_token
     * @return
     */
    @Override
    public String getAppleClientSecret(String id_token) {

        if (appleJwtUtils.getClaimsBy(id_token)!=null) {
            try {
                return appleJwtUtils.makeClientSecret();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * code 또는 refresh_token가 유효한지 Apple Server에 검증 요청
     *
     * @param client_secret
     * @param code
     * @param refresh_token
     * @return
     */
    @Override
    public AppleToken.Response requestCodeValidations(String client_secret, String code, String refresh_token) {

        AppleToken.Response tokenResponse = new AppleToken.Response();

        if (client_secret != null && code != null && refresh_token == null) {
            try {
                tokenResponse = appleJwtUtils.getTokenByCode(client_secret, code);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (client_secret != null && code == null && refresh_token != null) {
            try {
                tokenResponse = appleJwtUtils.getTokenByRefreshToken(client_secret, refresh_token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return tokenResponse;
    }
}
