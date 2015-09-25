package com.android.desafioaudionews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.desafioaudionews.fragments.CategoryFragment;

import java.util.ArrayList;
import java.util.List;

public class CustomTabPagerAdapter  extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private String[] mCategoriesList;

    public CustomTabPagerAdapter(FragmentManager fm, String[] categoriesList) {
        super(fm);
        mCategoriesList = categoriesList;
        for (String category : mCategoriesList) {
            mFragmentList.add(new CategoryFragment());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoriesList[position];
    }
}