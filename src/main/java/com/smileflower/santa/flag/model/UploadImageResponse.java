package com.smileflower.santa.flag.model;

public class UploadImageResponse {
    private boolean isFlag;
    private boolean isDoubleFlag;
    private String fileUrl;

    public UploadImageResponse(boolean isFlag, boolean isDoubleFlag, String fileUrl) {
        this.isFlag = isFlag;
        this.isDoubleFlag = isDoubleFlag;
        this.fileUrl = fileUrl;
    }

    public UploadImageResponse(boolean isFlag, String fileUrl) {
        this.isFlag = isFlag;
        this.fileUrl = fileUrl;
    }

    public UploadImageResponse(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean getIsDoubleFlag() {
        return isDoubleFlag;
    }

    public void setIsDoubleFlag(boolean isDoubleFlag) {
        this.isDoubleFlag = isDoubleFlag;
    }

    public boolean getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(boolean isFlag) {
        this.isFlag = isFlag;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
