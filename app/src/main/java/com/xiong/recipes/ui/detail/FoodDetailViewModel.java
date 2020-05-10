package com.xiong.recipes.ui.detail;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.xiong.lib_network.okhttp.CommonOkHttpClient;
import com.xiong.lib_network.okhttp.listener.DisposeDataHandle;
import com.xiong.lib_network.okhttp.listener.DisposeDataListener;
import com.xiong.lib_network.okhttp.request.CommonRequest;
import com.xiong.lib_network.okhttp.request.RequestParams;
import com.xiong.recipes.constant.Constant;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.entity.IndexFood;
import com.xiong.recipes.repository.HttpRequestClient;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FoodDetailViewModel extends AndroidViewModel {

    private MutableLiveData<String> foodInfo ;
    private MutableLiveData<String> foodName ;
    private MutableLiveData<Food> food ;
    public FoodDetailViewModel(@NonNull Application application) {
        super(application);
        foodInfo = new MutableLiveData<>();
        foodName = new MutableLiveData<>();
        food = new MutableLiveData<>();
    }

    public MutableLiveData<String> getFoodInfo() {
        return foodInfo;
    }

    public MutableLiveData<String> getFoodName() {
        return foodName;
    }

    public MutableLiveData<Food> getFood() {
        return food;
    }

    public void queryFoodDetail(int food_id){
        HttpRequestClient.getInstance().queryFoodDetail(food_id,getFood());
    }

}
