package com.android.desafioaudionews.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.adapters.NoteAdapter;
import com.android.desafioaudionews.api.UrlConstants;
import com.android.desafioaudionews.database.DatabaseHelper;
import com.android.desafioaudionews.models.Note;
import com.android.desafioaudionews.models.RequestResponse;
import com.android.desafioaudionews.utils.Const;
import com.android.desafioaudionews.volley.RequestConnector;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NoteDetailActivity extends AppCompatActivity implements Response.Listener<RequestResponse>, Response.ErrorListener{
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.textViewTitle)
    TextView textViewTitle;
    @InjectView(R.id.textViewSubtitle)
    TextView textViewSubtitle;
    @InjectView(R.id.textViewCategory)
    TextView textViewCategory;
    @InjectView(R.id.textViewContent)
    TextView textViewContent;
    @InjectView(R.id.imageViewNote)
    ImageView imageViewNote;

    @InjectView(R.id.fabFavourite)
    FloatingActionButton fabFavourite;

    @InjectView(R.id.buttonPlay)
    Button buttonPlay;
    Picasso picasso;

    private DatabaseHelper databaseHelper;
    private NoteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        ButterKnife.inject(this);
        setToolbar();
        picasso = Picasso.with(this);
        int noteId = getIntent().getExtras().getInt("noteId");

        RequestConnector requestConnector = new RequestConnector(NoteDetailActivity.this);
        requestConnector.getNoteWithId(noteId,this, this, Const.REQUEST_NOTE);


    }

    private void drawDetail(final Note note){
        textViewTitle.setText(note.titulo);
        textViewSubtitle.setText(note.bajada);
        textViewCategory.setText(note.category.valor);
        textViewCategory.setVisibility(View.VISIBLE);
        textViewTitle.setText(note.titulo);

        fabFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHelper().setAsFavourite(note.id);
            }
        });
        if (note.imageSrc!=null){
            loadPhoto(UrlConstants.BASE_IMAGE_URL+note.imageSrc, imageViewNote);
        } else {
            // holder.noteImage.setImageResource(R.drawable.default_image);
        }
    }

    private void loadPhoto(String url, ImageView imageView) {

        picasso.load(url)
                .noFade()
                .into(imageView);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_note));
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
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(RequestResponse response) {

        Note note = Note.parseNote(response.getJsonResponse());
        drawDetail(note);
    }
}