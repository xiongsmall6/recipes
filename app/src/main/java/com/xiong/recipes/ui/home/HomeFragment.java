package com.xiong.recipes.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.xiong.recipes.IndexActivity;
import com.xiong.recipes.R;
import com.xiong.recipes.base.BaseFragment;
import com.xiong.recipes.databinding.FragmentHomeBinding;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.repository.LocalRoomRequestManager;
import com.xiong.recipes.ui.home.adapter.HomeFoodAdapter;
import com.xiong.recipes.utils.CommonUtils;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private RecyclerView foodRecycle;
    private List<Food> foodList;
    private HomeFoodAdapter homeFoodAdapter;
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private EditText searchEdit;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(HomeViewModel.class);
        View view = binding.getRoot();
        binding.setHvm(homeViewModel);
        binding.setLifecycleOwner(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((IndexActivity)getActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
    }
    private void initView( View view){
        foodRecycle = view.findViewById(R.id.food_recycle);
        foodList = homeViewModel.getFoodList().getValue();
        homeViewModel.getFoodList().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                foodList = foods;
                homeFoodAdapter.setFoods(foods);
                homeFoodAdapter.notifyDataSetChanged();
                Food[] foodArr = new Food[foods.size()];
                foods.toArray(foodArr);
                //将数据放到本地数据库
                LocalRoomRequestManager.getInstance(getContext()).clearAndInsertFood(foodArr);
            }
        });
        homeViewModel.getHasFocus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.home_fragment_to_search_fragment);
                }
            }
        });
        homeFoodAdapter  = new HomeFoodAdapter(foodList);
        homeFoodAdapter.setOnItemClickListener(new HomeFoodAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Food food =  foodList.get(position);
                Gson gson = new Gson();
                NavHostFragment.findNavController(HomeFragment.this).navigate(HomeFragmentDirections.homeFragmentToFoodDetailFragment(gson.toJson(food)));
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0 || position==3 || position ==6){
                    return 2;
                }else {
                    return 1;
                }
            }
        });
        foodRecycle.setLayoutManager(gridLayoutManager);
        foodRecycle.setAdapter(homeFoodAdapter);
    }


    private void initData(){
        //从本地数据库中获取 如果没有去网上查 每天变一次
        LiveData<List<Food>> foodLive = LocalRoomRequestManager.getInstance(getContext()).getFoodLive();
        foodLive.observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if(foods !=null && foods.size()>0){
                    Date dataTime = foods.get(0).getTime();
                    if(!CommonUtils.isOneDay(dataTime,new Date())){
                        homeViewModel.queryDayData();
                    }else{
                        foodList = foods;
                        homeFoodAdapter.setFoods(foods);
                        homeFoodAdapter.notifyDataSetChanged();
                    }
                }else{
                    homeViewModel.queryDayData();
                }
            }
        });
      //  homeViewModel.queryDayData();
    }
}
