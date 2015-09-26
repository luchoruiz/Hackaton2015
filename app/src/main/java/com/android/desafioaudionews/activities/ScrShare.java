package com.android.desafioaudionews.activities;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.interfaces.OnItemSelected;
import com.android.desafioaudionews.widgets.SharedBar;

/**
 * Created by Lucho on 25/09/2015.
 */
public class ScrShare extends AppCompatActivity implements OnItemSelected {
    SharedBar sharedBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scr_share_layout);
        sharedBar = new SharedBar(this,this);
    }

    @Override
    public void itemSelected(int item) {

    }
}
