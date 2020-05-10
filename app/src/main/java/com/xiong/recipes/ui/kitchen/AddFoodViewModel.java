package com.xiong.recipes.ui.kitchen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AddFoodViewModel extends AndroidViewModel {
    private MutableLiveData<String> foodImage ;
    private MutableLiveData<String> foodName ;
    private MutableLiveData<Boolean> addImage ;
    private  MutableLiveData<String> foodInfo ;
    public AddFoodViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getFoodImage() {
        if(foodImage==null){
            foodImage = new MutableLiveData<>();
        }
        return foodImage;
    }

    public MutableLiveData<Boolean> getAddImage() {
        if(addImage==null){
            addImage = new MutableLiveData<>();
            addImage.setValue(false);
        }
        return addImage;
    }

    public MutableLiveData<String> getFoodInfo() {
        if(foodInfo==null){
            foodInfo = new MutableLiveData<>();
        }
        return foodInfo;
    }
    public MutableLiveData<String> getFoodName() {
        if(foodName==null){
            foodName = new MutableLiveData<>();
        }
        return foodName;
    }
}
