package com.smileflower.santa.src.mountain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRankRes {
    private int ranking;
    private int userIdx;
    private String userName;
    private String userImage;
    private int flagCount;
    private String agoTime;
}