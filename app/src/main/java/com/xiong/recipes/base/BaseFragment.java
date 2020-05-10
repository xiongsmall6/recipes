package com.xiong.recipes.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.xiong.recipes.MyApplication;
import com.xiong.recipes.constant.Constant;
import com.xiong.recipes.share.SharedViewModel;
import com.xiong.recipes.utils.PreferencesUtils;

public class BaseFragment  extends Fragment {
    protected AppCompatActivity mActivity;
    public SharedViewModel sharedViewModel;  // 使用共享

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = getAppViewModelProvider().get(SharedViewModel.class);
        boolean isLogin = PreferencesUtils.getBoolean(getContext(), Constant.IS_LOGIN,false);
        sharedViewModel.getIsLogin().setValue(isLogin);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    // 给当前BaseFragment用的
    protected ViewModelProvider getAppViewModelProvider() {
        return ((MyApplication) mActivity.getApplicationContext()).getAppViewModelProvider(mActivity);
    }

}
