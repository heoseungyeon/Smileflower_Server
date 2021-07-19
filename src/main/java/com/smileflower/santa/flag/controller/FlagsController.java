package com.smileflower.santa.flag.controller;

import com.smileflower.santa.apple.utils.AppleJwtUtils;
import com.smileflower.santa.exception.ApiResult;
import com.smileflower.santa.flag.model.UploadImageResponse;
import com.smileflower.santa.flag.service.FlagsService;
import com.smileflower.santa.profile.service.ProfileService;
import com.smileflower.santa.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.smileflower.santa.config.BaseResponseStatus.EMPTY_JWT;

@RequestMapping("/api/flag")
@RestController
public class FlagsController {

    @Autowired
    AppleJwtUtils appleJwtUtils;
    @Autowired
    FlagsService flagsService;
    @Autowired
    JwtService jwtService;

    @PostMapping("/upload/{mountainIdx}")
    public ApiResult<UploadImageResponse> uploadImage(@RequestPart(required = false) MultipartFile file, @PathVariable("mountainIdx") Long mountainIdx) {
        int userIdx = jwtService.getUserIdxV2();

        return ApiResult.OK(
                flagsService.uploadImage(file,userIdx, mountainIdx)
        );

    }
}