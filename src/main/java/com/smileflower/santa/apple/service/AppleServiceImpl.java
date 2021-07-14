package com.smileflower.santa.apple.service;

import com.smileflower.santa.apple.model.AppleToken;
import com.smileflower.santa.apple.model.TokenResponse;
import com.smileflower.santa.apple.utils.AppleJwtUtils;
import com.smileflower.santa.apple.utils.AppleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class AppleServiceImpl implements AppleService {

    @Autowired
    AppleUtils appleUtils;
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
//    @Override
//    public String getAppleClientSecret(String id_token) {
//
//        if (appleUtils.verifyIdentityToken(id_token)) {
//            try {
//                return appleUtils.makeClientSecret();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }

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
//    @Override
//    public TokenResponse requestCodeValidations(String client_secret, String code, String refresh_token) {
//
//        TokenResponse tokenResponse = new TokenResponse();
//
//        if (client_secret != null && code != null && refresh_token == null) {
//            tokenResponse = appleUtils.validateAuthorizationGrantCode(client_secret, code);
//        } else if (client_secret != null && code == null && refresh_token != null) {
//            tokenResponse = appleUtils.validateAnExistingRefreshToken(client_secret, refresh_token);
//        }
//
//        return tokenResponse;
//    }

    /**
     * Apple login page 호출을 위한 Meta 정보 가져오기
     *
     * @return
     */
    @Override
    public Map<String, String> getLoginMetaInfo() {
        return appleUtils.getMetaInfo();
    }

    /**
     * id_token에서 payload 데이터 가져오기
     *
     * @return
     */
    @Override
    public String getPayload(String id_token) {
        return appleUtils.decodeFromIdToken(id_token).toString();
    }
}
