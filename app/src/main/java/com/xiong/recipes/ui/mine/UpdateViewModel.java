package com.xiong.recipes.ui.mine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class UpdateViewModel extends AndroidViewModel {
    private MutableLiveData<String> nikeName ;
    private MutableLiveData<String> age ;
    private MutableLiveData<String> userImage ;
    public UpdateViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getNikeName() {
        if(nikeName ==null){
            nikeName = new MutableLiveData<>();
        }
        return nikeName;
    }

    public MutableLiveData<String> getAge() {
        if(age ==null){
            age = new MutableLiveData<>();
        }
        return age;
    }

    public MutableLiveData<String> getUserImage() {
        if(userImage ==null){
            userImage = new MutableLiveData<>();
        }
        return userImage;
    }
}
