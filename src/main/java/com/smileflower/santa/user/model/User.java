package com.smileflower.santa.user.model;

import java.time.LocalDateTime;

public class User {

    //Member Field
    private final Long userIdx;
    private Email emailId;
    private String passwd;
    private boolean isKakao;
    private boolean isApple;
    private String userImageUrl;
    private LocalDateTime updateAt;
    private LocalDateTime createAt;
    private String name;

    //Constructor
    public User(Long userIdx, Email emailId, String passwd, String userImageUrl, LocalDateTime updateAt, LocalDateTime createAt, String name) {
        this.userIdx = userIdx;
        this.emailId = emailId;
        this.passwd = passwd;
        this.isKakao = false;
        this.isApple = false;
        this.userImageUrl = userImageUrl;
        this.updateAt = null;
        this.createAt = null;
        this.name = name;
    }

    public User(Email emailId, String passwd,String name){
        this(null,emailId,passwd,"url",null,null,name);
    }

    //Getter
    public Long getUserIdx() {
        return userIdx;
    }

    public boolean isKakao() {
        return isKakao;
    }

    public boolean isApple() {
        return isApple;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public String getName() {
        return name;
    }

    public Email getEmailId() {
        return emailId;
    }

    public String getPasswd() {
        return passwd;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
