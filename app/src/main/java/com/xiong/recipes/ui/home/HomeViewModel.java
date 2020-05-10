package com.xiong.recipes.ui.home;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.xiong.lib_network.okhttp.CommonOkHttpClient;
import com.xiong.lib_network.okhttp.listener.DisposeDataHandle;
import com.xiong.lib_network.okhttp.listener.DisposeDataListener;
import com.xiong.lib_network.okhttp.request.CommonRequest;
import com.xiong.recipes.constant.Constant;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.entity.IndexFood;
import com.xiong.recipes.repository.HttpRequestClient;
import com.xiong.recipes.repository.LocalRoomRequestManager;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<String> editData ;
    private MutableLiveData<List<Food>> foodList;
    private MutableLiveData<Boolean> hasFocus;
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getEditData() {
        if(editData ==null){
            editData = new MutableLiveData<>();
        }
        return editData;
    }


    public MutableLiveData<List<Food>> getFoodList(){
        if(foodList==null){
            foodList = new MutableLiveData<>();
        }
        return foodList;
    }

    public MutableLiveData<Boolean> getHasFocus() {
        if(hasFocus==null){
            hasFocus = new MutableLiveData<>();
        }
        return hasFocus;
    }

    public void queryDayData(){
        HttpRequestClient.getInstance().queryDayData(getFoodList());
    }

    public void listenerChange(View view,boolean onFocus){
        this.hasFocus.setValue(onFocus);
    }
}
