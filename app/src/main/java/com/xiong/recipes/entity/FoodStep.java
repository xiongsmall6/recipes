package com.xiong.recipes.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodStep  implements Serializable {
    private Integer id;
    @SerializedName("food_id")
    private Integer foodId;
    @SerializedName("step_num")
    private String stepNum;
    @SerializedName("step_info")
    private String stepInfo;
    @SerializedName("step_image")
    private String stepImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getStepNum() {
        return stepNum;
    }

    public void setStepNum(String stepNum) {
        this.stepNum = stepNum;
    }

    public String getStepInfo() {
        return stepInfo;
    }

    public void setStepInfo(String stepInfo) {
        this.stepInfo = stepInfo;
    }

    public String getStepImage() {
        return stepImage;
    }

    public void setStepImage(String stepImage) {
        this.stepImage = stepImage;
    }
}
