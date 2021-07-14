package com.smileflower.santa.apple.service;

import com.smileflower.santa.apple.model.AppleToken;
import com.smileflower.santa.apple.model.TokenResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface AppleService {

    String getAppleClientSecret(String id_token);

    AppleToken.Response requestCodeValidations(String client_secret, String code, String refresh_token);

    Map<String, String> getLoginMetaInfo();

    String getPayload(String id_token);



}
