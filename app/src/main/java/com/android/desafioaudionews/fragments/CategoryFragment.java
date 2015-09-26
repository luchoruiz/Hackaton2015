package com.android.desafioaudionews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.desafioaudionews.utils.Const;

/**
 * Created by Lucho on 23/09/2015.
 */
public class CategoryFragment extends Fragment {
   private  int categoryID;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        categoryID = args.getInt(Const.CATEGORY_ID, 0);
    }
}
