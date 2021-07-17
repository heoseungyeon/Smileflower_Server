package com.smileflower.santa.profile.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.smileflower.santa.profile.model.domain.Email;
import com.smileflower.santa.profile.model.domain.Profile;
import com.smileflower.santa.profile.model.dto.UploadImageResponse;
import com.smileflower.santa.profile.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final S3Service s3Service;

    public ProfileService(ProfileRepository profileRepository, S3Service s3Service) {
        this.profileRepository = profileRepository;
        this.s3Service = s3Service;
    }

    public UploadImageResponse deleteImage(String email){
        //delete file
        Profile user = profileRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("cannot find user"));
        s3Service.deleteFile(user.getUserImageUrl());

        deleteImageUrlByEmail(email);

        return new UploadImageResponse("null");
    }


    public UploadImageResponse uploadImage(MultipartFile file,String email){

        //delete pre file
        Profile user = profileRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("cannot find user"));
        if(user.getUserImageUrl()!=null){
            s3Service.deleteFile(user.getUserImageUrl());
        }
        String fileName = createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()){
            s3Service.uploadFile(inputStream,objectMetadata,fileName);
            updateImageUrlByEmail(email,fileName);
        }catch(IOException e){
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생하였습니다 (%s)", file.getOriginalFilename()));
        }
        return new UploadImageResponse(s3Service.getFileUrl(fileName));
    }

    private int deleteImageUrlByEmail(String email){
        return profileRepository.deleteImageUrlByEmail(email);
    }

    private int updateImageUrlByEmail(String email,String fileName){
        return profileRepository.updateImageUrlByEmail(email,fileName);
    }

    private String createFileName(String originalFileName){
        return
            UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        }catch(StringIndexOutOfBoundsException e){
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다",fileName));
        }
    }

}