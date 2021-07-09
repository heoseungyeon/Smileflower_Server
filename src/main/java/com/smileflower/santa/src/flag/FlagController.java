package com.smileflower.santa.src.flag;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.smileflower.santa.config.BaseException;
import com.smileflower.santa.config.BaseResponse;
import com.smileflower.santa.src.flag.model.*;
import com.smileflower.santa.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.smileflower.santa.config.BaseResponseStatus.*;
import static com.smileflower.santa.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/flags")
public class FlagController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final FlagProvider flagProvider;
    @Autowired
    private final FlagService flagService;
    @Autowired
    private final JwtService jwtService;


    public FlagController(FlagProvider flagProvider, FlagService flagService, JwtService jwtService) {
        this.flagProvider = flagProvider;
        this.flagService = flagService;
        this.jwtService = jwtService;
    }

    //Query String
    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetFlagRes> getFlag(@RequestParam(required = true) String mountain) {
        try{
            if(jwtService.getJwt()==null){
                return new BaseResponse<>(EMPTY_JWT);
            }

            else{
                int userIdx=jwtService.getUserIdx();
                GetFlagRes getFlagRes = flagProvider.getFlag(userIdx,mountain);
                return new BaseResponse<>(getFlagRes);
            }
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("{mountainIdx}")
    public BaseResponse<PostFlagPictureRes> createFlag(@RequestBody PostFlagPictureReq postFlagPictureReq,@PathVariable("mountainIdx")int mountainIdx ) throws BaseException {
        try{
            if(jwtService.getJwt()==null){
                return new BaseResponse<>(EMPTY_JWT);
            }

            else{
                int userIdx=jwtService.getUserIdx();
                PostFlagPictureRes postFlagPictureRes = flagService.createFlag(postFlagPictureReq,mountainIdx,userIdx);
                return new BaseResponse<>(postFlagPictureRes);
            }

        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("{mountainIdx}/rank")
    public BaseResponse<GetFlagRankRes> getFlagRank(@PathVariable("mountainIdx")int mountainIdx) {
        try{
            if(jwtService.getJwt()==null){
                return new BaseResponse<>(EMPTY_JWT);
            }

            else{
                int userIdx=jwtService.getUserIdx();
                GetFlagRankRes getFlagRankRes = flagProvider.getFlagRank(userIdx,mountainIdx);
                return new BaseResponse<>(getFlagRankRes);
            }
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}