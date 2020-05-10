package com.xiong.recipes.ui.mine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<String> userName ;
    private MutableLiveData<String> password ;
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<String> getUserName() {
        if(userName ==null){
            userName = new MutableLiveData<>();
        }
        return userName;
    }

    public MutableLiveData<String> getPassword() {
        if(password ==null){
            password = new MutableLiveData<>();
        }
        return password;
    }
}
