package com.android.desafioaudionews.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.adapters.NoteAdapter;
import com.android.desafioaudionews.database.DatabaseHelper;
import com.android.desafioaudionews.interfaces.OnRecyclerItemClick;
import com.android.desafioaudionews.models.Note;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FavouritesActivity extends AppCompatActivity implements OnRecyclerItemClick {
    @InjectView(R.id.contentViews)
    RecyclerView mRecyclerView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private DatabaseHelper databaseHelper;
    private NoteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ButterKnife.inject(this);
        setToolbar();
        drawNotes();
    }

    private void drawNotes(){
        List<Note> favouritesNotes = getFavouritesNotes();
        mAdapter = new NoteAdapter(this,favouritesNotes, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_favourites));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private List<Note> getFavouritesNotes(){
        return getHelper().getFavouritesNotes();
    }


    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onListItemClick(Object object) {
        Log.e("", "");
    }
}