package com.xiong.recipes.ui.classification;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.xiong.recipes.entity.Food;

import java.util.List;

public class FoodTypeViewModel extends AndroidViewModel {
    private MutableLiveData<List<Food>> foodList;

    public MutableLiveData<List<Food>> getFoodList() {
        if(foodList==null){
            foodList = new MutableLiveData<>();
        }
        return foodList;
    }
    public FoodTypeViewModel(@NonNull Application application) {
        super(application);
    }

}
