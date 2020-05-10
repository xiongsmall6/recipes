package com.xiong.recipes.ui.kitchen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wildma.pictureselector.FileUtils;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;
import com.xiong.recipes.R;
import com.xiong.recipes.base.BaseFragment;
import com.xiong.recipes.constant.Constant;
import com.xiong.recipes.databinding.FragmentAddFoodBinding;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.entity.FoodMaterial;
import com.xiong.recipes.entity.FoodStep;
import com.xiong.recipes.entity.ResultData;
import com.xiong.recipes.entity.User;
import com.xiong.recipes.repository.HttpRequestClient;
import com.xiong.recipes.ui.kitchen.adapter.MaterialAdapter;
import com.xiong.recipes.ui.kitchen.adapter.StepAdapter;
import com.xiong.recipes.ui.mine.UpdateUserFragment;
import com.xiong.recipes.ui.view.HeadView;
import com.xiong.recipes.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class AddFoodFragment extends BaseFragment {
    private HeadView detailHead;
    private RecyclerView materialView;
    private RecyclerView stepView;
    private Spinner spinner;
    private MaterialAdapter materialAdapter;
    private StepAdapter stepAdapter;
    private List<FoodStep>foodStepList;
    private List<FoodMaterial> foodMaterialList;
    private FragmentAddFoodBinding binding;
    private AddFoodViewModel addFoodViewModel;
    private int nowSelectPosition = 0;
    private  User user;
    private String foodServiceImage = "";

    public AddFoodFragment() {
    }

    public static AddFoodFragment newInstance() {
        AddFoodFragment fragment = new AddFoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_food, container, false);
        addFoodViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AddFoodViewModel.class);
        binding.setClick(new ProxyClick());
        binding.setVm(addFoodViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
    }
    private void initData(){
        String userString = PreferencesUtils.getString(getContext(), Constant.USER,"");
        Gson gson = new Gson();
        user = gson.fromJson(userString,User.class);
        foodStepList = new ArrayList<>();
        foodStepList.add(new FoodStep());
        foodMaterialList = new ArrayList<>();
        foodMaterialList.add(new FoodMaterial());
    }

    private void initView(View view){
        detailHead = view.findViewById(R.id.detail_head);
        spinner = view.findViewById(R.id.spinner);
        detailHead.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        materialView = view.findViewById(R.id.material_view);
        materialAdapter =  new MaterialAdapter(foodMaterialList, new MaterialAdapter.TextChange() {
            @Override
            public void nameText(int position, String text) {
                foodMaterialList.get(position).setMaterialName(text);
            }

            @Override
            public void unitText(int position, String text) {
                foodMaterialList.get(position).setMaterialUnit(text);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        materialView.setLayoutManager(linearLayoutManager);
        materialView.setAdapter(materialAdapter);
        stepAdapter = new StepAdapter(foodStepList, new StepAdapter.TextChange() {
            @Override
            public void addImage(int position, View v) {
                nowSelectPosition = position;
                PictureSelector
                        .create(AddFoodFragment.this, 123)
                        .selectPicture();
            }

            @Override
            public void stepText(int position, String text) {
                foodStepList.get(position).setStepInfo(text);
                foodStepList.get(position).setStepNum((position+1)+"");
            }
        });
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        stepView = view.findViewById(R.id.step_view);
        stepView.setLayoutManager(linearLayoutManager2);
        stepView.setAdapter(stepAdapter);

    }

    public class ProxyClick {
        public void addFood(){
            Toast toast =  Toast.makeText(getContext(),null,Toast.LENGTH_SHORT);
            if(TextUtils.isEmpty(foodServiceImage)){
                toast.setText("请上传食物图片");
                toast.show();
                return;
            }
            if(TextUtils.isEmpty(addFoodViewModel.getFoodName().getValue())){
                toast.setText("请填写食物名称");
                toast.show();
                return;
            }
            if(TextUtils.isEmpty(addFoodViewModel.getFoodInfo().getValue())){
                toast.setText("请填写食物简介");
                toast.show();
                return;
            }
            for(FoodMaterial foodMaterial :foodMaterialList){
                if(TextUtils.isEmpty(foodMaterial.getMaterialUnit())||TextUtils.isEmpty(foodMaterial.getMaterialName())){
                    toast.setText("请确保用料填写正确");
                    toast.show();
                    return;
                }
            }

            for(FoodStep foodStep :foodStepList){
                if(TextUtils.isEmpty(foodStep.getStepImage())||TextUtils.isEmpty(foodStep.getStepInfo())){
                    toast.setText("请确保步骤填写正确");
                    toast.show();
                    return;
                }
            }
            Food food = new Food();
            food.setFoodName(addFoodViewModel.getFoodName().getValue());
            food.setFoodImage(foodServiceImage);
            food.setCreateUser(user.getUserName());
            food.setFoodType(spinner.getSelectedItemPosition()+1);
            food.setFoodInfo(addFoodViewModel.getFoodInfo().getValue());
            food.setFoodMaterial(foodMaterialList);
            food.setFoodStep(foodStepList);
            Gson gson = new Gson();
            String foodJson = gson.toJson(food);
            HttpRequestClient.getInstance().foodAdd(foodJson, new HttpRequestClient.CallBack() {
                @Override
                public void onSuccess(Object responseObj) {
                    getActivity().onBackPressed();
                }

                @Override
                public void onFailure(Object reasonObj) {
                    Toast toast =  Toast.makeText(getContext(),null,Toast.LENGTH_SHORT);
                    toast.setText("添加失败");
                    toast.show();
                }
            });

        }
        public void addMaterial(){
            foodMaterialList.add(new FoodMaterial());
            materialAdapter.notifyDataSetChanged();
        }
        public void addStep(){
            foodStepList.add(new FoodStep());
            stepAdapter.notifyDataSetChanged();
        }
        public void addFoodImage(){
            PictureSelector
                    .create(AddFoodFragment.this, PictureSelector.SELECT_REQUEST_CODE)
                    .selectPicture(false, 200, 200, 1, 1);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                String picturePath =  pictureBean.isCut() ? pictureBean.getPath() : pictureBean.getUri().getPath();
                addFoodViewModel.getFoodImage().setValue(picturePath);
                addFoodViewModel.getAddImage().setValue(true);
                HttpRequestClient.getInstance().uploadImage(user.getId()+"",picturePath,new HttpRequestClient.CallBack(){
                    @Override
                    public void onSuccess(Object responseObj) {
                        ResultData resultData = (ResultData)responseObj;
                        //这里如果使用这样加载的话会有等待的过程
                       // addFoodViewModel.getFoodImage().setValue(resultData.getData());
                        foodServiceImage = resultData.getData();
                    }
                    @Override
                    public void onFailure(Object reasonObj) {
                    }
                });
            }
        }else {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                String picturePath =  pictureBean.isCut() ? pictureBean.getPath() : pictureBean.getUri().getPath();
                foodStepList.get(nowSelectPosition).setStepImage(picturePath);
                stepAdapter.notifyDataSetChanged();
                HttpRequestClient.getInstance().uploadImage(user.getId()+"",picturePath,new HttpRequestClient.CallBack(){
                    @Override
                    public void onSuccess(Object responseObj) {
                        ResultData resultData = (ResultData)responseObj;
                        foodStepList.get(nowSelectPosition).setStepImage(resultData.getData());
                    }
                    @Override
                    public void onFailure(Object reasonObj) {
                    }
                });
            }
        }
    }
}
