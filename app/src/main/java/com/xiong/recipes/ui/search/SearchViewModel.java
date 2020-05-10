package com.xiong.recipes.ui.search;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.xiong.lib_network.okhttp.CommonOkHttpClient;
import com.xiong.lib_network.okhttp.listener.DisposeDataHandle;
import com.xiong.lib_network.okhttp.listener.DisposeDataListener;
import com.xiong.lib_network.okhttp.request.CommonRequest;
import com.xiong.lib_network.okhttp.request.RequestParams;
import com.xiong.recipes.constant.Constant;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.entity.FoodList;
import com.xiong.recipes.repository.HttpRequestClient;

import java.util.List;


public class SearchViewModel extends AndroidViewModel {

    private MutableLiveData<String> searchText;
    private MutableLiveData<List<Food>> foodList;

    public MutableLiveData<String> getSearchText() {
        if(searchText==null){
            searchText  = new MutableLiveData<>();
        }
        return searchText;
    }

    public MutableLiveData<List<Food>> getFoodList() {
        if(foodList==null){
            foodList = new MutableLiveData<>();
        }
        return foodList;
    }

    public SearchViewModel(@NonNull Application application) {
        super(application);

    }

    public void searchFood(String foodName,int page,int limit){
        HttpRequestClient.getInstance().searchFood(foodName,null,null,page,limit,getFoodList());
    }

}
