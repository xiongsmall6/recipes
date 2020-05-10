package com.xiong.recipes.ui.mine;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wildma.pictureselector.FileUtils;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;
import com.xiong.recipes.R;
import com.xiong.recipes.base.BaseFragment;
import com.xiong.recipes.constant.Constant;
import com.xiong.recipes.databinding.FragmentUpdateUserBinding;
import com.xiong.recipes.entity.User;
import com.xiong.recipes.entity.UserData;
import com.xiong.recipes.imageload.app.ImageLoaderManager;
import com.xiong.recipes.repository.HttpRequestClient;
import com.xiong.recipes.ui.view.HeadView;
import com.xiong.recipes.utils.PreferencesUtils;

import java.io.File;

import static android.app.Activity.RESULT_OK;


public class UpdateUserFragment extends BaseFragment{

    private FragmentUpdateUserBinding binding;
    private UpdateViewModel updateViewModel;
    private ImageView userImage;
    private HeadView loginHead;
    private Spinner spinner;
    private boolean changeImage = false;
    private String userId=null;

    public UpdateUserFragment() {
    }

    public static UpdateUserFragment newInstance() {
        UpdateUserFragment fragment = new UpdateUserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_update_user, container, false);
        updateViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(UpdateViewModel.class);
        binding.setClick(new ProxyClick());
        binding.setVm(updateViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userImage = view.findViewById(R.id.user_image);
        loginHead = view.findViewById(R.id.login_head);
        loginHead.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        loginHead.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nikeName = updateViewModel.getNikeName().getValue();
                String age = updateViewModel.getAge().getValue();
                if(TextUtils.isEmpty(nikeName)){
                    Toast toast =  Toast.makeText(getContext(),null,Toast.LENGTH_SHORT);
                    toast.setText("昵称不能为空");
                    toast.show();
                    return;
                }
                if(TextUtils.isEmpty(age)){
                    Toast toast =  Toast.makeText(getContext(),null,Toast.LENGTH_SHORT);
                    toast.setText("年龄不能为空");
                    toast.show();
                    return;
                }
                String imagePath = null;
                int gender = spinner.getSelectedItemPosition()+1;
                if(changeImage){
                    imagePath = updateViewModel.getUserImage().getValue();
                }
                HttpRequestClient.getInstance().update(userId, nikeName, imagePath, gender+"", age, new HttpRequestClient.CallBack() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        UserData userData = (UserData)responseObj;
                        if(userData.getCode()==0){
                            User user = userData.getData();
                            PreferencesUtils.putString(getContext(), Constant.USER,user.toJson());
                            FileUtils.deleteAllCacheImage(getContext());
                            getActivity().onBackPressed();
                        }else{
                            //解决弹框带应用信息
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
        });
        spinner = view.findViewById(R.id.spinner);
        String userString = PreferencesUtils.getString(getContext(), Constant.USER,"");
        if(userString!=""){
            Gson gson = new Gson();
            User user = gson.fromJson(userString,User.class);
            updateViewModel.getNikeName().setValue(user.getNikeName());
            updateViewModel.getUserImage().setValue(user.getUserImage());
            updateViewModel.getAge().setValue(user.getAge()+"");
            userId = user.getId()+"";
            if(user.getGender()==1){
                spinner.setSelection(0,true);
            }else{
                spinner.setSelection(1,true);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class ProxyClick {

        public void selectPic(){
            PictureSelector
                    .create(UpdateUserFragment.this, PictureSelector.SELECT_REQUEST_CODE)
                    .selectPicture(true, 200, 200, 1, 1);
        }
        public void quitLogin(){
            PreferencesUtils.putString(getContext(), Constant.USER,"");
            PreferencesUtils.putBoolean(getContext(), Constant.IS_LOGIN,false);
            sharedViewModel.getIsLogin().setValue(false);
            NavHostFragment.findNavController(UpdateUserFragment.this).navigate(R.id.update_user_to_login_fragment);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                String picturePath =  pictureBean.isCut() ? pictureBean.getPath() : pictureBean.getUri().getPath();
                updateViewModel.getUserImage().setValue(picturePath);
                changeImage = true;
            }
        }
    }
}
