package com.smileflower.santa.flag.model;

public class UploadImageResponse {
    private String fileUrl;

    public UploadImageResponse(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
