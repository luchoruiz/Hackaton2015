package com.android.desafioaudionews.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.models.Note;
import com.android.desafioaudionews.models.RequestResponse;
import com.android.desafioaudionews.utils.Const;
import com.android.desafioaudionews.volley.RequestConnector;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class SplashActivity extends AppCompatActivity implements Response.Listener<RequestResponse>, Response.ErrorListener{
        // Splash screen timer
        private static int SPLASH_TIME_OUT = 3000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            RequestConnector requestConnector = new RequestConnector(SplashActivity.this);
            requestConnector.getNotes(this, this, Const.REQUEST_NOTES);

            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    // close this activity
                    //finish();
                }
            }, SPLASH_TIME_OUT);*/
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Response ", error.toString());
    }

    @Override
    public void onResponse(RequestResponse response) {
        Log.e("Response ", response.getJsonResponse().toString());
        Note.parseAsync(response.getJsonResponse());
        //Intent i = new Intent(SplashActivity.this, MainActivity.class);
        //startActivity(i);
    }
}
