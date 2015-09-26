package com.android.desafioaudionews.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.fragments.CategoryFragment;
import com.android.desafioaudionews.models.Category;
import com.android.desafioaudionews.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class CustomTabPagerAdapter  extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private List<Category> mCategoryList;

    public CustomTabPagerAdapter(FragmentManager fm, List<Category> categoryList) {
        super(fm);
        mCategoryList = categoryList;
        for (Category category : mCategoryList) {
            Bundle args = new Bundle();
            args.putInt(Const.CATEGORY_ID, category.id);
            CategoryFragment categoryFragment = new CategoryFragment();
            categoryFragment.setArguments(args);
            mFragmentList.add(categoryFragment);
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
        return (mCategoryList.get(position)).valor;
    }

    public View drawTabView(int i,Context ctx) {
        View customView = LayoutInflater.from(ctx).inflate(R.layout.tab_header_view, null);
        //set resources
        TextView categoryName = (TextView) customView.findViewById(R.id.txtCategoryName);
        categoryName.setText(mCategoryList.get(i).valor);
        return customView;
    }
}