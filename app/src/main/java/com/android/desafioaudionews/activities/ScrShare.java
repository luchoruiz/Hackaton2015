package com.android.desafioaudionews.activities;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.interfaces.OnItemSelected;
import com.android.desafioaudionews.widgets.SharedBar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Lucho on 25/09/2015.
 */
public class ScrShare extends AppCompatActivity implements OnItemSelected {
    SharedBar sharedBar;
    @InjectView(R.id.llMail)
    LinearLayout llMail;
    @InjectView(R.id.txtMailToShare)
    EditText txtMails;
    @InjectView(R.id.txtShareNote)
    EditText txtShareNote;
    @InjectView(R.id.btnSend)
    ImageButton btnSend;
    @InjectView(R.id.txtNewsTitle)
    TextView txtNewsTitle;
    private int selectedshareItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scr_share_layout);
        ButterKnife.inject(this);
        sharedBar = new SharedBar(this,this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("noteUrl");
            String title = extras.getString("noteTitle");
        }
        selectedshareItem = R.id.btnWhatsapp;
    }

    @Override
    public void itemSelected(int item) {
        selectedshareItem = item;
        if (item == R.id.btnMail) {
            llMail.setVisibility(View.VISIBLE);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(
                    this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(txtMails.getWindowToken(), 0);
            llMail.setVisibility(View.GONE);
        }
    }
}
