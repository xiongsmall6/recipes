package com.xiong.recipes.entity;

import java.io.Serializable;
import java.util.List;

public class IndexFood implements Serializable {
    private List<Food> morning;
    private List<Food> noon;
    private List<Food> night;

    public List<Food> getMorning() {
        return morning;
    }

    public void setMorning(List<Food> morning) {
        this.morning = morning;
    }

    public List<Food> getNoon() {
        return noon;
    }

    public void setNoon(List<Food> noon) {
        this.noon = noon;
    }

    public List<Food> getNight() {
        return night;
    }

    public void setNight(List<Food> night) {
        this.night = night;
    }
}
