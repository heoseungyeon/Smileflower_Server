package com.smileflower.santa.flag.repository;

public interface FlagRepository {
    int updateImageUrlByIdx(int userIdx, Long mountainIdx, String filename);
}
