package com.android.desafioaudionews.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.android.desafioaudionews.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ScrWebViewActivity extends AppCompatActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.inject(this);
        setToolbar();
        String url = getIntent().getExtras().getString("url");
        drawWebView(url);
    }

    private void drawWebView(String url){
        webview.loadUrl(url);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_new));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_favorite, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id ==  android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }




}