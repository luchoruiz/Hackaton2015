package com.android.desafioaudionews.activities;

<<<<<<< HEAD
import android.content.Intent;
=======
import android.media.MediaPlayer;
import android.net.Uri;
>>>>>>> e624d34d7ac0bd6d70d80758160afb342a300d54
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.adapters.NoteAdapter;
import com.android.desafioaudionews.api.UrlConstants;
import com.android.desafioaudionews.database.DatabaseHelper;
import com.android.desafioaudionews.interfaces.onSynthesizeFinish;
import com.android.desafioaudionews.models.Note;
import com.android.desafioaudionews.models.RequestResponse;
import com.android.desafioaudionews.utils.Const;
import com.android.desafioaudionews.utils.TTSManager;
import com.android.desafioaudionews.volley.RequestConnector;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NoteDetailActivity extends AppCompatActivity implements Response.Listener<RequestResponse>, Response.ErrorListener, onSynthesizeFinish {
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
    @InjectView(R.id.seekBarProgress)
    SeekBar mSeekBar;

    private Handler durationHandler ;
    @InjectView(R.id.fabFavourite)
    FloatingActionButton fabFavourite;

    @InjectView(R.id.buttonShare)
    Button buttonShare;

    @InjectView(R.id.buttonViewMore)
    Button buttonViewMore;

    @InjectView(R.id.buttonPlay)
    FloatingActionButton buttonPlay;
    Picasso picasso;
    Note mNote;
    private TTSManager tts;

    private DatabaseHelper databaseHelper;
    private NoteAdapter mAdapter;
    private MediaPlayer mMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        ButterKnife.inject(this);
        setToolbar();
        tts = TTSManager.getInstance(this);
        picasso = Picasso.with(this);
        int noteId = getIntent().getExtras().getInt("noteId");

        RequestConnector requestConnector = new RequestConnector(NoteDetailActivity.this);
        requestConnector.getNoteWithId(noteId, this, this, Const.REQUEST_NOTE);

    }

    private void drawDetail(final Note note) {
        textViewTitle.setText(note.titulo);
        textViewSubtitle.setText(note.bajada);
        textViewCategory.setText(note.category.valor);
        textViewCategory.setVisibility(View.VISIBLE);
        textViewTitle.setText(note.titulo);

        fabFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NoteDetailActivity.this, getString(R.string.msg_added), Toast.LENGTH_SHORT).show();
                getHelper().setAsFavourite(note.id);
            }
        });
        if (note.imageSrc != null) {
            loadPhoto(UrlConstants.BASE_IMAGE_URL + note.imageSrc, imageViewNote);
        } else {
            // holder.noteImage.setImageResource(R.drawable.default_image);
        }

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toShare = new Intent(getApplicationContext(), ScrShare.class);
                toShare.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(toShare);
            }
        });

        buttonViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webView = new Intent(getApplicationContext(), ScrWebViewActivity.class);
                webView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                webView.putExtra("url",UrlConstants.BASE_LANACION + note.url);
                startActivity(webView);
            }
        });
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


    private List<Note> getFavouritesNotes() {
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

        mNote = Note.parseNote(response.getJsonResponse());
        drawDetail(mNote);
    }

    @OnClick(R.id.buttonPlay)
    public void play(Button button) {
        tts.synthesizeToFIle(mNote.getAllNote(), this);

    }

    @Override
    public void onFinish(Uri uriFile) {
        if (uriFile != null) {
            playSoundFile(uriFile);
        }
    }

    private void playSoundFile(Uri uriFile) {
        mMediaPlayer = MediaPlayer.create(this, uriFile);
        // synchronizedSeekBar();
        mSeekBar.setMax((int) mMediaPlayer.getDuration());
        mSeekBar.setClickable(false);
        mMediaPlayer.start();
        mSeekBar.setMax(mMediaPlayer.getDuration());

    }

   


    private void synchronizedSeekBar() {
    }
}