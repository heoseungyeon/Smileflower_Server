package com.smileflower.santa.profile.repository;

import com.smileflower.santa.profile.model.domain.Email;
import com.smileflower.santa.profile.model.domain.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {
    Optional<Profile> findByEmail(String email);
    int updateImageUrlByEmail(String email,String filename);
    int deleteImageUrlByEmail(String email);
}