package com.xiong.recipes.ui.mine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MineViewModel extends AndroidViewModel {
    private MutableLiveData<String> nikeName ;
    private MutableLiveData<String> userImage ;

    private MutableLiveData<Boolean> loginStatus ;
    public MineViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getNikeName() {
        if(nikeName ==null){
            nikeName = new MutableLiveData<>();
        }
        return nikeName;
    }

    public MutableLiveData<Boolean> getLoginStatus() {
        if(loginStatus ==null){
            loginStatus = new MutableLiveData<>();
        }
        return loginStatus;
    }
    public MutableLiveData<String> getUserImage() {
        if(userImage ==null){
            userImage = new MutableLiveData<>();
        }
        return userImage;
    }
}
