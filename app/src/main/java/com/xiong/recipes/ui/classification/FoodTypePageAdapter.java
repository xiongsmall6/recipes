package com.xiong.recipes.ui.classification;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class FoodTypePageAdapter extends FragmentStatePagerAdapter {
    private List<FoodTypeFragment> list;

    public FoodTypePageAdapter(FragmentManager fm,List<FoodTypeFragment> list){
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
