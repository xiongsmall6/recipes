package com.xiong.recipes.ui.kitchen;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wildma.pictureselector.PictureSelector;
import com.xiong.recipes.R;
import com.xiong.recipes.base.BaseFragment;
import com.xiong.recipes.constant.Constant;
import com.xiong.recipes.databinding.FragmentKitchenBinding;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.entity.User;
import com.xiong.recipes.repository.HttpRequestClient;
import com.xiong.recipes.ui.mine.UpdateUserFragment;
import com.xiong.recipes.ui.search.SearchFragment;
import com.xiong.recipes.ui.search.SearchFragmentDirections;
import com.xiong.recipes.ui.search.adapter.FoodListAdapter;
import com.xiong.recipes.utils.PreferencesUtils;

import java.util.List;


public class KitchenFragment extends BaseFragment {

    private FragmentKitchenBinding binding;
    private KitchenViewModel kitchenViewModel;
    private FoodListAdapter foodListAdapter;
    private RecyclerView myFoodView;

    public static KitchenFragment newInstance() {
        KitchenFragment fragment = new KitchenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_kitchen, container, false);
        kitchenViewModel = new  ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(KitchenViewModel.class);
        binding.setClick(new ProxyClick());
        binding.setVm(kitchenViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    public class ProxyClick {
        public void addFood(){
            String userString = PreferencesUtils.getString(getContext(), Constant.USER,"");
            if(userString==""){
                NavHostFragment.findNavController(KitchenFragment.this).navigate(R.id.kitchen_to_login_fragment);
            }else{
                NavHostFragment.findNavController(KitchenFragment.this).navigate(R.id.kitchen_to_add_food_fragment);
            }

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myFoodView = view.findViewById(R.id.my_food_view);
        foodListAdapter = new FoodListAdapter(kitchenViewModel.getFoodList().getValue());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        foodListAdapter.setOnItemClickListener(new FoodListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Food food =  kitchenViewModel.getFoodList().getValue().get(position);
                Gson gson = new Gson();
                NavHostFragment.findNavController(KitchenFragment.this).navigate(KitchenFragmentDirections.kitchenToFoodDetailFragment(gson.toJson(food)));
            }
        });
        myFoodView.setLayoutManager(gridLayoutManager);
        myFoodView.setAdapter(foodListAdapter);

        kitchenViewModel.getFoodList().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if(foods!=null&&foods.size()>0){
                    kitchenViewModel.getHasData().setValue(true);
                }else{
                    kitchenViewModel.getHasData().setValue(false);
                }
                foodListAdapter.setData(foods);
                foodListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String userString = PreferencesUtils.getString(getContext(), Constant.USER,"");
        if(userString!=""){
            Gson gson = new Gson();
            User user = gson.fromJson(userString,User.class);
            HttpRequestClient.getInstance().searchFood(null,user.getUserName(),null,1,20,kitchenViewModel.getFoodList());
        }

    }
}
