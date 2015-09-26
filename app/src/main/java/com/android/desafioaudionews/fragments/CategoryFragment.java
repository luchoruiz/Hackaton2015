package com.android.desafioaudionews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;


import com.android.desafioaudionews.utils.Const;

import com.android.desafioaudionews.database.DatabaseHelper;
import com.android.desafioaudionews.models.Note;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;


/**
 * Created by Lucho on 23/09/2015.
 */
public class CategoryFragment extends Fragment {
    private DatabaseHelper databaseHelper;
   private  int categoryID;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        categoryID = args.getInt(Const.CATEGORY_ID, 0);
    }
    private List<Note> getNotesByCategory(int categoryID){
        return getHelper().getNotesByCategoryID(categoryID);
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;

    }
}
