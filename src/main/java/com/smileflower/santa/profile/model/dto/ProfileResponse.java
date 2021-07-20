package com.smileflower.santa.profile.model.dto;

import com.smileflower.santa.profile.model.domain.Picture;

import java.util.List;

public class ProfileResponse {
    private int userIdx;
    private String name;
    private int level;
    private int flagCount;
    private int postCount;
    private List<ProfilePostsResponse> profilePostsResponses;

    public ProfileResponse(int userIdx, String name, int level, int flagCount, int postCount, List<ProfilePostsResponse> profilePostsResponses) {
        this.userIdx = userIdx;
        this.name = name;
        this.level = level;
        this.flagCount = flagCount;
        this.postCount = postCount;
        this.profilePostsResponses = profilePostsResponses;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public List<ProfilePostsResponse> getProfilePostsResponses() {
        return profilePostsResponses;
    }

    public void setProfilePostsResponses(List<ProfilePostsResponse> profilePostsResponses) {
        this.profilePostsResponses = profilePostsResponses;
    }
}
