package com.xiong.recipes.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class User {
    private int id;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("nike_name")
    private String nikeName;
    @SerializedName("user_image")
    private String userImage;
    private String password;
    private int gender;
    private int age;
    @SerializedName("create_time")
    private String  createTime ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String toJson(){
        Gson gson = new Gson();
        if(this!=null){
            return gson.toJson(this);
        }
        return "";
    }
}
