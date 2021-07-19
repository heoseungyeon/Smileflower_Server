package com.smileflower.santa.profile.model.dto;

import java.time.LocalDateTime;

public class PictureResponse {
    private Long pictureIdx;
    private int userIdx;
    private String imageUrl;
    private LocalDateTime createdAt;

    public PictureResponse(Long pictureIdx, int userIdx, String imageUrl, LocalDateTime createdAt) {
        this.pictureIdx = pictureIdx;
        this.userIdx = userIdx;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public Long getPictureIdx() {
        return pictureIdx;
    }

    public void setPictureIdx(Long pictureIdx) {
        this.pictureIdx = pictureIdx;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
