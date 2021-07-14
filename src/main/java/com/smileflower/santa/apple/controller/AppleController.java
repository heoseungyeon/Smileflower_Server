package com.smileflower.santa.apple.controller;

import com.smileflower.santa.apple.model.*;
import com.smileflower.santa.apple.service.AppleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AppleController {

    private Logger logger = LoggerFactory.getLogger(AppleController.class);

    @Autowired
    AppleService appleService;

    /**
     * Sign in with Apple
     *
     * @param appleLoginRequest
     * @return AppleLoginResponse
     */
    @PostMapping(value = "/apple/login")
    @ResponseBody
    public AppleToken.Response appleLogin(@RequestBody AppleLoginRequest appleLoginRequest){
        AppleLoginResponse appleLoginResponse = null;
        String client_secret = appleService.getAppleClientSecret(appleLoginRequest.getIdentifyToken());
        return appleService.requestCodeValidations(client_secret, appleLoginRequest.getAuthorizationCode(), appleLoginRequest.getRefreshToken());
    }


}
