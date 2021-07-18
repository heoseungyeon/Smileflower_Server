package com.smileflower.santa.src.home.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetHomeMountainRes {
    private int mountainIdx;
    private String mountainName;
    private String mountainImage;
    private int difficulty;
    private int userIdx;
    private String userName;
    private String userImage;
    private int flagCount;
}
