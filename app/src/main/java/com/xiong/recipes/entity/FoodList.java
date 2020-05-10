package com.xiong.recipes.entity;

import java.io.Serializable;
import java.util.List;

public class FoodList implements Serializable {
    private int code;
    private String msg;
    private List<Food> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Food> getData() {
        return data;
    }

    public void setData(List<Food> data) {
        this.data = data;
    }
}
