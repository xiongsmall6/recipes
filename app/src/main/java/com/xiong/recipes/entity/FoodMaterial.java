package com.xiong.recipes.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodMaterial implements Serializable {
    private Integer id;
    @SerializedName("food_id")
    private Integer foodId;
    @SerializedName("material_name")
    private String materialName;
    @SerializedName("material_unit")
    private String materialUnit;

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

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }
}
