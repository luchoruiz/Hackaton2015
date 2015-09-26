package com.android.desafioaudionews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.database.DatabaseHelper;
import com.android.desafioaudionews.models.CategoryNote;
import com.android.desafioaudionews.models.Note;
import com.android.desafioaudionews.models.RequestResponse;
import com.android.desafioaudionews.utils.Const;
import com.android.desafioaudionews.volley.RequestConnector;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

public class SplashActivity extends AppCompatActivity implements Response.Listener<RequestResponse>, Response.ErrorListener{
    private DatabaseHelper databaseHelper;

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
        List<Note> notes =  Note.parseNotes(response.getJsonResponse());
        for (Note note: notes){
            getHelper().getNoteDao().createOrUpdate(note);
            getHelper().getCategoryDao().createOrUpdate(note.category);
            try {
                getHelper().getCategoryNoteDao().createOrUpdate(new CategoryNote(note.category.id, note.id));
            }catch (Exception e){

            }

        }
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
