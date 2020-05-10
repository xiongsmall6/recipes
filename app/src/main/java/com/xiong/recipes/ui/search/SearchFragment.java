package com.xiong.recipes.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiong.recipes.IndexActivity;
import com.xiong.recipes.R;
import com.xiong.recipes.base.BaseFragment;
import com.xiong.recipes.databinding.FragmentSearchLayoutBinding;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.entity.FoodList;
import com.xiong.recipes.ui.home.HomeFragment;
import com.xiong.recipes.ui.home.HomeFragmentDirections;
import com.xiong.recipes.ui.search.adapter.FoodListAdapter;

import java.util.List;

public class SearchFragment extends BaseFragment {
    private FragmentSearchLayoutBinding binding;
    private SearchViewModel searchViewModel;
    private String searchValue = "";
    private RecyclerView foodListView;
    private List<Food> foodList ;
    private FoodListAdapter foodListAdapter;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_layout, container, false);
        searchViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(SearchViewModel.class);
        binding.setSvm(searchViewModel);
        binding.setClick(new ProxyClick());
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void initView(View view){
        foodListView = view.findViewById(R.id.food_list_view);
        foodList = searchViewModel.getFoodList().getValue();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        foodListAdapter = new FoodListAdapter(foodList);
        foodListAdapter.setOnItemClickListener(new FoodListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Food food =  searchViewModel.getFoodList().getValue().get(position);
                Gson gson = new Gson();
                NavHostFragment.findNavController(SearchFragment.this).navigate(SearchFragmentDirections.searchFragmentToFoodDetailFragment(gson.toJson(food)));
            }
        });
        foodListView.setLayoutManager(gridLayoutManager);
        foodListView.setAdapter(foodListAdapter);
        searchViewModel.getFoodList().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                foodListAdapter.setData(foods);
                foodListAdapter.notifyDataSetChanged();
            }
        });
        searchViewModel.getSearchText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String searchText) {
                searchValue = searchText;
            }
        });
    }

    public class ProxyClick {
        public void onBack() {
            getActivity().onBackPressed();
        }

        public void search(){
            Toast.makeText(getContext(),searchValue,Toast.LENGTH_SHORT).show();
            searchViewModel.searchFood(searchValue,1,20);
        }
    }
}
