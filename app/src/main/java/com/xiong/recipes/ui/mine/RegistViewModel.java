package com.xiong.recipes.ui.mine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class RegistViewModel extends AndroidViewModel {
    private MutableLiveData<String> userName ;
    private MutableLiveData<String> password ;
    private MutableLiveData<String> confirmPassword ;
    public RegistViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getConfirmPassword() {
        if(confirmPassword ==null){
            confirmPassword = new MutableLiveData<>();
        }
        return confirmPassword;
    }

    public MutableLiveData<String> getPassword() {
        if(password ==null){
            password = new MutableLiveData<>();
        }
        return password;
    }

    public MutableLiveData<String> getUserName() {
        if(userName ==null){
            userName = new MutableLiveData<>();
        }
        return userName;
    }
}
