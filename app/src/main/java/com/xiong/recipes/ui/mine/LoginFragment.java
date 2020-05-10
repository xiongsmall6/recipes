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
import com.xiong.recipes.databinding.FragmentLoginBinding;
import com.xiong.recipes.entity.User;
import com.xiong.recipes.entity.UserData;
import com.xiong.recipes.repository.HttpRequestClient;
import com.xiong.recipes.ui.view.HeadView;
import com.xiong.recipes.utils.CommonUtils;
import com.xiong.recipes.utils.PreferencesUtils;


public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding loginBinding;
    private LoginViewModel viewModel;
    private HeadView loginHead;
    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((IndexActivity)getActivity()).getBottomNavigationView().setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_login, container, false);
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(LoginViewModel.class);
        loginBinding.setVm(viewModel);
        loginBinding.setClick(new ProxyClick());
        loginBinding.setLifecycleOwner(this);
        return loginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginHead = view.findViewById(R.id.login_head);
        loginHead.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this).navigateUp();
            }
        });
//        viewModel.getUserName().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                userName = s;
//            }
//        });
//        viewModel.getPassword().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                password = s;
//            }
//        });
    }


    @Override
    public void onResume() {
        super.onResume();
        String userName= PreferencesUtils.getString(getContext(), Constant.USER_NAME,"");
        viewModel.getUserName().setValue(userName);

    }


    public class ProxyClick {
        public void toRegister(){
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.login_to_regist_fragment);
        }
        public void toLogin(){
            String userName = viewModel.getUserName().getValue();
            String password = viewModel.getPassword().getValue();
            if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(password)){
                Toast toast =  Toast.makeText(getContext(),null,Toast.LENGTH_SHORT);
                toast.setText("请输入正确的用户名和密码");
                toast.show();
                return;
            }
            if(!CommonUtils.match(userName)||!CommonUtils.match(password)){
                Toast toast =  Toast.makeText(getContext(),null,Toast.LENGTH_SHORT);
                toast.setText("请输入正确的用户名和密码");
                toast.show();
                return;
            }
            HttpRequestClient.getInstance().login(userName, password,new HttpRequestClient.CallBack(){
                @Override
                public void onSuccess(Object responseObj) {
                    UserData userData = (UserData)responseObj;
                    if(userData.getCode()==0){
                        User user = userData.getData();
                        PreferencesUtils.putString(getContext(), Constant.USER,user.toJson());
                        PreferencesUtils.putBoolean(getContext(), Constant.IS_LOGIN,true);
                        sharedViewModel.getIsLogin().setValue(true);
                        getActivity().onBackPressed();
                    }else{
                        Toast toast =  Toast.makeText(getContext(),null,Toast.LENGTH_SHORT);
                        toast.setText(userData.getMsg());
                        toast.show();
                    }
                }
                @Override
                public void onFailure(Object reasonObj) {
                }
            });
        }

    }

}
