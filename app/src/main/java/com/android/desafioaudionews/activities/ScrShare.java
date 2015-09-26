package com.android.desafioaudionews.activities;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.interfaces.OnItemSelected;
import com.android.desafioaudionews.utils.Const;
import com.android.desafioaudionews.widgets.SharedBar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Lucho on 25/09/2015.
 */
public class ScrShare extends AppCompatActivity implements OnItemSelected, SweetAlertDialog.OnSweetClickListener {
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
    private String noteTitle;
    private String noteUrl;
    private  String sharedText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scr_share_layout);
        ButterKnife.inject(this);
        sharedBar = new SharedBar(this,this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //TODO:check != null noteTitle && noteUrl
            noteTitle = extras.getString(Const.NOTE_TITLE);
            noteUrl = extras.getString(Const.NOTE_URL);
        }
        txtNewsTitle.setText(noteTitle);
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

    @OnClick(R.id.btnSend)
    public void send(ImageButton button) {
         sharedText = txtShareNote.getText().toString();
        switch (selectedshareItem) {
            case (R.id.btnMail):
                validEmailAddress(txtMails.getText().toString());
                break;
            case R.id.btnfacebook:
                 showError(getString(R.string.error_to_share_with_fb));
                break;
            case R.id.btnTwitter:
                showError(getString(R.string.error_to_show_with_tw));
                break;
            case R.id.btnWhatsapp:
                shareWhatsapp();
                break;
            default:
                showError(getString(R.string.select_at_least_one_option));
        }
    }

    private void shareWhatsapp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT,sharedText+"\n"+"\n"+"\n"+noteUrl );
        try {
            ScrShare.this.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            showError(getString(R.string.error_ws_isnot_installed));
        }
    }
    private void sendMail(String[] emailsAccounts){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , emailsAccounts);
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.news_lanacion_label));
        i.putExtra(Intent.EXTRA_TEXT   , sharedText+"\n"+"\n"+"\n"+noteUrl);
        try {
            startActivity(Intent.createChooser(i, getString(R.string.sharing_label)));
        } catch (android.content.ActivityNotFoundException ex) {
            showError(getString(R.string.error_gmail_isnot_available));
        }
    }

    private void showError(String cause){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(R.string.error_to_show_note))
                .setContentText(cause)
                .setConfirmClickListener(this)
                .show();
    }
    public void validEmailAddress(String mailList) {
        if (mailList.isEmpty()) {

            //the user should add at least one email
            showError(getString(R.string.error_invalid_email_format));
        } else {
            mailList = mailList.replaceAll(",", " ");
            String[] mails = mailList.split(" ");
            ArrayList<String> invalidAddress = invalidMailAddresses(mails);
            if (invalidAddress.isEmpty()) {
                sendMail(mails);
            } else {
                showError(getString(R.string.error_invalid_email_format));
            }
        }
    }

    private ArrayList<String> invalidMailAddresses(String[] mailList) {

        ArrayList<String> invalidEmails = new ArrayList<>();

        for (String mail : mailList) {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                invalidEmails.add(mail);
            }
        }
        return invalidEmails;
    }

    @Override
    public void onClick(SweetAlertDialog sweetAlertDialog) {
        sweetAlertDialog.dismiss();
    }


}

