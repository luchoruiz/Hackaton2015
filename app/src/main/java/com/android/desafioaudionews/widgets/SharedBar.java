package com.android.desafioaudionews.widgets;

import android.app.Activity;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.interfaces.OnItemSelected;

/**
 * Created by Lucho on 25/09/2015.
 */
public class SharedBar implements SharedButton.OnCheckedChangeListener {
    private Activity activity;
    private SharedButton btnWhatsapp;
    private SharedButton btnMail;
    private SharedButton btnfacebook;
    private SharedButton btnTwitter;
    int lastSelected;
    private OnItemSelected listener;


    public SharedBar(Activity activity, OnItemSelected listener) {
        this.activity = activity;
        this.listener = listener;
        init(activity);
    }

    private void init(Activity activity) {
        btnWhatsapp = (SharedButton) activity.findViewById(R.id.btnWhatsapp);
        btnMail = (SharedButton) activity.findViewById(R.id.btnMail);
        btnfacebook = (SharedButton) activity.findViewById(R.id.btnfacebook);
        btnTwitter = (SharedButton) activity.findViewById(R.id.btnTwitter);
        //set icons
        btnWhatsapp.setText((char) 0xf095);
        btnfacebook.setText((char) 0xf09a);
        btnMail.setText((char) 0xf0e0);
        btnTwitter.setText((char) 0xf099);
        //set Listeners
        btnWhatsapp.setOnCheckedChangeListener(this);
        btnfacebook.setOnCheckedChangeListener(this);
        btnMail.setOnCheckedChangeListener(this);
        btnTwitter.setOnCheckedChangeListener(this);
        //set first option selected
        //setChecked(btnNewsRush.getId(),true);
        setChecked(btnWhatsapp.getId(), true);
    }


    @Override
    public void onCheckedChanged(SharedButton buttonView, boolean isChecked) {
        if (lastSelected != buttonView.getId()) {
            setChecked(lastSelected, !isChecked);
            setChecked(buttonView.getId(), isChecked);
            listener.itemSelected(buttonView.getId());
        }


    }

    private void setChecked(int buttonId, Boolean isChecked) {
        if (isChecked) {
            lastSelected = buttonId;
        }
        switch (buttonId) {
            case R.id.btnWhatsapp:
                btnWhatsapp.setChecked(isChecked);
                break;
            case R.id.btnMail:
                btnMail.setChecked(isChecked);
                break;
            case R.id.btnfacebook:
                btnfacebook.setChecked(isChecked);
                break;
            case R.id.btnTwitter:
                btnTwitter.setChecked(isChecked);
                break;
            default:
                break;
        }


    }
}