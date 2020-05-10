package com.xiong.recipes.ui.classification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.xiong.recipes.R;
import com.xiong.recipes.base.BaseFragment;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.repository.HttpRequestClient;
import com.xiong.recipes.ui.search.SearchFragment;
import com.xiong.recipes.ui.search.SearchFragmentDirections;
import com.xiong.recipes.ui.search.adapter.FoodListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FoodTypeFragment extends BaseFragment {
    private RecyclerView foodListRecycle;
    private FoodListAdapter foodListAdapter;
    private FoodTypeViewModel foodTypeViewModel;
    private List<Food> foodList;
    private String type;
    public FoodTypeFragment(String type) {
        this.type = type;
    }
    public FoodTypeFragment() {
    }

    public static FoodTypeFragment newInstance() {
        FoodTypeFragment fragment = new FoodTypeFragment();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        foodTypeViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(FoodTypeViewModel.class);
        return inflater.inflate(R.layout.fragment_food_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodListRecycle = view.findViewById(R.id.food_list_recycle);
        foodList = new ArrayList<>();
        foodListAdapter = new FoodListAdapter(foodList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        foodListRecycle.setLayoutManager(gridLayoutManager);
        foodListRecycle.setAdapter(foodListAdapter);
        foodListAdapter.setOnItemClickListener(new FoodListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Food food =  foodTypeViewModel.getFoodList().getValue().get(position);
                Gson gson = new Gson();
                NavHostFragment.findNavController(getParentFragment()).navigate(ClassificationFragmentDirections.classFragmentToFoodDetailFragment(gson.toJson(food)));
            }
        });
        foodTypeViewModel.getFoodList().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                foodListAdapter.setData(foods);
                foodListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpRequestClient.getInstance().searchFood(null,null,type,1,20,foodTypeViewModel.getFoodList());
    }

}
