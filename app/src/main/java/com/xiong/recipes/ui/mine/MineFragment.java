package com.xiong.recipes.ui.mine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiong.recipes.IndexActivity;
import com.xiong.recipes.R;
import com.xiong.recipes.base.BaseFragment;
import com.xiong.recipes.constant.Constant;
import com.xiong.recipes.databinding.FragmentMineBinding;
import com.xiong.recipes.entity.User;
import com.xiong.recipes.ui.home.HomeFragment;
import com.xiong.recipes.utils.PreferencesUtils;

public class MineFragment extends BaseFragment {
    private FragmentMineBinding binding;
    private MineViewModel mineViewModel;
    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mine, container, false);
        mineViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MineViewModel.class);
        binding.setVm(mineViewModel);
        binding.setClick(new ProxyClick());
        binding.setLifecycleOwner(this);
        boolean isLogin = PreferencesUtils.getBoolean(getContext(), Constant.IS_LOGIN,false);
        String userString = PreferencesUtils.getString(getContext(), Constant.USER,"");
        if(userString!=""){
            Gson gson = new Gson();
            User user = gson.fromJson(userString,User.class);
            mineViewModel.getNikeName().setValue(user.getNikeName());
            mineViewModel.getUserImage().setValue(user.getUserImage());
        }
        mineViewModel.getLoginStatus().setValue(isLogin);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel.getIsLogin().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mineViewModel.getLoginStatus().setValue(aBoolean);
                String userString = PreferencesUtils.getString(getContext(), Constant.USER,"");
                if(userString!=""){
                    Gson gson = new Gson();
                    User user = gson.fromJson(userString,User.class);
                    mineViewModel.getNikeName().setValue(user.getNikeName());
                    mineViewModel.getUserImage().setValue(user.getUserImage());
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((IndexActivity)getActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
    }

    public class ProxyClick {

        public void toLogin(){
            NavHostFragment.findNavController(MineFragment.this).navigate(R.id.mime_to_login_fragment);
        }
        public void updateUser(){
            NavHostFragment.findNavController(MineFragment.this).navigate(R.id.mime_to_update_user_fragment);
        }
    }
}
