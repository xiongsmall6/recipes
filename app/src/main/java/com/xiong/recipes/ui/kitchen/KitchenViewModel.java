package com.xiong.recipes.ui.kitchen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.xiong.recipes.entity.Food;

import java.util.List;

public class KitchenViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> hasData;
    private MutableLiveData<List<Food>> foodList;
    public MutableLiveData<Boolean> getHasData() {
        if(hasData==null){
            hasData = new MutableLiveData<>();
            hasData.setValue(true);
        }
        return hasData;
    }
    public KitchenViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Food>> getFoodList() {
        if(foodList==null){
            foodList = new MutableLiveData<>();
        }
        return foodList;
    }
}
