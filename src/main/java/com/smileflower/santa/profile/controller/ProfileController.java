package com.smileflower.santa.profile.controller;

import com.smileflower.santa.apple.utils.AppleJwtUtils;
import com.smileflower.santa.exception.ApiResult;
import com.smileflower.santa.profile.model.domain.Email;
import com.smileflower.santa.profile.model.dto.UploadImageResponse;
import com.smileflower.santa.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ProfileController {

    @Autowired
    AppleJwtUtils appleJwtUtils;
    @Autowired
    ProfileService profileService;

    @PatchMapping("/api/profile/upload")
    public ApiResult<UploadImageResponse> uploadImage(@RequestHeader(value = "REFRESH-TOKEN")
    String token,@RequestPart(required = false) MultipartFile file){
        String email = appleJwtUtils.getEmailByRefreshToken(token).getEmail();
        if(file != null){
            return ApiResult.OK(
                    profileService.uploadImage(file,email)
            );
        }
        else {
            return ApiResult.OK(
                    profileService.deleteImage(email)
            );
        }
    }
    @GetMapping("/api/profile/upload")
    public ApiResult<UploadImageResponse> uploadImage(@RequestHeader(value = "REFRESH-TOKEN")
                                                              String token){
        String email = appleJwtUtils.getEmailByRefreshToken(token).getEmail();
        return ApiResult.OK(
                profileService.getUploadImage(email)
        );

    }

}
