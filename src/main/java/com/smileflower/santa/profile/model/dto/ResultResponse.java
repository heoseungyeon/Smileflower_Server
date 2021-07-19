package com.smileflower.santa.profile.model.dto;

public class ResultResponse {
    private int flagCount;

    public ResultResponse(int flagCount) {
        this.flagCount = flagCount;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }
}

