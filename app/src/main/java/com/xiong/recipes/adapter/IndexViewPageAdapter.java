package com.xiong.recipes.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xiong.recipes.ui.classification.ClassificationFragment;
import com.xiong.recipes.ui.home.HomeFragment;
import com.xiong.recipes.ui.kitchen.KitchenFragment;
import com.xiong.recipes.ui.mine.MineFragment;


public class IndexViewPageAdapter extends FragmentPagerAdapter {
    private int[] datas ;

    public IndexViewPageAdapter(@NonNull FragmentManager fm, int[]datas){
        this(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.datas = datas;
    }

    public IndexViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (datas[position]){
            case 1:
                return HomeFragment.newInstance();
            case 2:
                return ClassificationFragment.newInstance();
            case 3:
                return KitchenFragment.newInstance();
            case 4:
                return MineFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return datas.length;
    }
}
