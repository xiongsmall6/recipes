package com.xiong.recipes.share;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 共享区域
 */
public class SharedViewModel extends ViewModel {

    private MediatorLiveData<Boolean> isLogin;

    {
        isLogin = new MediatorLiveData<>();
        isLogin.setValue(false);
    }

    public MediatorLiveData<Boolean> getIsLogin() {
        return isLogin;
    }
}
