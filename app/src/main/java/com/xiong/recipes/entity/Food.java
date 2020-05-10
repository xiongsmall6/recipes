package com.xiong.recipes.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Entity
public class Food implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int lid;//本地数据库id
    private Integer id;
    @ColumnInfo(name = "food_name")
    @SerializedName("food_name")
    private String foodName;
    @ColumnInfo(name = "food_info")
    @SerializedName("food_info")
    private String foodInfo;
    @ColumnInfo(name = "food_image")
    @SerializedName("food_image")
    private String foodImage;
    @ColumnInfo(name = "food_type")
    @SerializedName("food_type")
    private int foodType;
    private int collections;
    private int praise;
    @ColumnInfo(name = "create_user")
    @SerializedName("create_user")
    private String createUser;
    @ColumnInfo(name = "create_time")
    @SerializedName("create_time")
    private String createTime;
    @ColumnInfo(name = "title_name")
    private String titleName;
    private Date time;
    @Ignore
    @SerializedName("food_step")
    private List<FoodStep> foodStep;
    @Ignore
    @SerializedName("food_material")
    private List<FoodMaterial> foodMaterial;



    public Food(){};

    @Ignore
    public Food(int foodType,String titleName){
        this.foodType = foodType;
        this.titleName = titleName;
    };

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodInfo() {
        return foodInfo;
    }

    public void setFoodInfo(String foodInfo) {
        this.foodInfo = foodInfo;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }


    public int getFoodType() {
        return foodType;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public int getCollections() {
        return collections;
    }

    public void setCollections(int collections) {
        this.collections = collections;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public List<FoodStep> getFoodStep() {
        return foodStep;
    }

    public void setFoodStep(List<FoodStep> foodStep) {
        this.foodStep = foodStep;
    }

    public List<FoodMaterial> getFoodMaterial() {
        return foodMaterial;
    }

    public void setFoodMaterial(List<FoodMaterial> foodMaterial) {
        this.foodMaterial = foodMaterial;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        if(time ==null){
            this.time = new Date();
        }else {
            this.time = time;
        }
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodName='" + foodName + '\'' +
                ", foodInfo='" + foodInfo + '\'' +
                ", foodImage='" + foodImage + '\'' +
                ", foodType=" + foodType +
                ", collections=" + collections +
                ", praise=" + praise +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                ", titleName='" + titleName + '\'' +
                '}';
    }
}
