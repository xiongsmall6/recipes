package com.xiong.recipes.ui.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.xiong.recipes.IndexActivity;
import com.xiong.recipes.R;
import com.xiong.recipes.base.BaseFragment;
import com.xiong.recipes.databinding.FragmentFoodDetailBinding;
import com.xiong.recipes.databinding.FragmentHomeBinding;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.imageload.app.ImageLoaderManager;
import com.xiong.recipes.ui.detail.adapter.FoodMaterialAdapter;
import com.xiong.recipes.ui.detail.adapter.FoodStepAdapter;
import com.xiong.recipes.ui.home.HomeViewModel;
import com.xiong.recipes.ui.view.HeadView;

import java.util.List;


public class FoodDetailFragment extends BaseFragment {
    private FragmentFoodDetailBinding binding;
    private FoodDetailViewModel foodDetailViewModel;
    private ImageView foodDetailImage;
    private RecyclerView materialRecycle;
    private RecyclerView stepRecycle;
    private FoodStepAdapter foodStepAdapter;
    private FoodMaterialAdapter foodMaterialAdapter;

    private HeadView detailHead;
    public FoodDetailFragment() {

    }

    public static FoodDetailFragment newInstance() {
        FoodDetailFragment fragment = new FoodDetailFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((IndexActivity)getActivity()).getBottomNavigationView().setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_food_detail,container,false);
        foodDetailViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(FoodDetailViewModel.class);
        View view = binding.getRoot();
        binding.setFoodVm(foodDetailViewModel);
        binding.setLifecycleOwner(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(view);
    }

    private void initView(View view){
        String foodStr = FoodDetailFragmentArgs.fromBundle(getArguments()).getFood();
        Gson gson = new Gson();
        final Food food = gson.fromJson(foodStr, Food.class);
        foodDetailViewModel.queryFoodDetail(food.getId());
        detailHead = view.findViewById(R.id.detail_head);
        foodDetailImage = view.findViewById(R.id.food_detail_image);
        materialRecycle = view.findViewById(R.id.material_recycle);
        stepRecycle =  view.findViewById(R.id.step_recycle);
        foodDetailViewModel.getFoodInfo().setValue(food.getFoodInfo());
        foodDetailViewModel.getFoodName().setValue(food.getFoodName());
        detailHead.setHeadCenterText(food.getFoodName());
        ImageLoaderManager.getInstance().displayImageForView(foodDetailImage,food.getFoodImage());

        foodStepAdapter = new FoodStepAdapter(null);
        foodMaterialAdapter = new FoodMaterialAdapter(null);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
       // LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        materialRecycle.setLayoutManager(gridLayoutManager);

        materialRecycle.setAdapter(foodMaterialAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        stepRecycle.setLayoutManager(linearLayoutManager);
        stepRecycle.setAdapter(foodStepAdapter);
        detailHead.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();//销毁自己
                ((IndexActivity)getActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
            }
        });
        foodDetailViewModel.getFood().observe(getViewLifecycleOwner(), new Observer<Food>() {
            @Override
            public void onChanged(Food newFood) {
                foodStepAdapter.setData(newFood.getFoodStep());
                foodStepAdapter.notifyDataSetChanged();
                foodMaterialAdapter.setData(newFood.getFoodMaterial());
                foodMaterialAdapter.notifyDataSetChanged();
            }
        });
    }
}
