package com.xiong.recipes;


import android.os.Bundle;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xiong.recipes.base.BaseActivity;


public class IndexActivity extends BaseActivity {
    private BottomNavigationView bottomNavigationView;
    private NavHostFragment navHostFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();

    }
    private void initView(){
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.home_navigate_fragment);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setItemIconTintList(null);
        NavigationUI.setupWithNavController(bottomNavigationView,navHostFragment.getNavController());

    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }
}
