package com.xiong.recipes.ui.mine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiong.recipes.R;
import com.xiong.recipes.base.BaseFragment;
import com.xiong.recipes.constant.Constant;
import com.xiong.recipes.databinding.FragmentRegistBinding;
import com.xiong.recipes.entity.User;
import com.xiong.recipes.entity.UserData;
import com.xiong.recipes.repository.HttpRequestClient;
import com.xiong.recipes.ui.view.HeadView;
import com.xiong.recipes.utils.CommonUtils;
import com.xiong.recipes.utils.PreferencesUtils;


public class RegistFragment extends BaseFragment {
    private HeadView loginHead;
    private FragmentRegistBinding binding;
    private RegistViewModel registViewModel;
    public RegistFragment() {

    }

    public static RegistFragment newInstance() {
        RegistFragment fragment = new RegistFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_regist, container, false);
        registViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(RegistViewModel.class);
        binding.setClick(new ProxyClick());
        binding.setVm(registViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginHead = view.findViewById(R.id.login_head);
        loginHead.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
//        registViewModel.getUserName().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                userName = s;
//            }
//        });
//        registViewModel.getPassword().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                password = s;
//            }
//        });
//        registViewModel.getConfirmPassword().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                confirmPassword = s;
//            }
//        });

    }

    public class ProxyClick {

        public void regist(){
            String userName = registViewModel.getUserName().getValue();
            String password= registViewModel.getPassword().getValue();
            String confirmPassword= registViewModel.getConfirmPassword().getValue();;
            if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(password)||TextUtils.isEmpty(confirmPassword)){
                Toast toast =  Toast.makeText(getContext(),null,Toast.LENGTH_SHORT);
                toast.setText("请输入正确的用户名和密码");
                toast.show();
                return;
            }
            if(!CommonUtils.match(userName)){
                Toast toast =  Toast.makeText(getContext(),null,Toast.LENGTH_SHORT);
                toast.setText("请输入正确的用户名和密码");
                toast.show();
                return;
            }
            if(!password.equals(confirmPassword)){
                Toast toast =  Toast.makeText(getContext(),null,Toast.LENGTH_SHORT);
                toast.setText("两次密码不一致");
                toast.show();
                return;
            }
            HttpRequestClient.getInstance().register(userName,password,new HttpRequestClient.CallBack(){
                @Override
                public void onSuccess(Object responseObj) {
                    UserData userData = (UserData)responseObj;
                    if(userData.getCode()==0){
                        User user = userData.getData();
                        PreferencesUtils.putString(getContext(), Constant.USER_NAME,user.getUserName());
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
