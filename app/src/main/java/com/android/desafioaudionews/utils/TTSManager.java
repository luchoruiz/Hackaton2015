package com.android.desafioaudionews.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.interfaces.onSynthesizeFinish;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Lucho on 26/09/2015.
 */
public class TTSManager implements TextToSpeech.OnInitListener {
    private static TTSManager mInstance;
    private static Context mCtx;
    private TextToSpeech myTTS;
    onSynthesizeFinish mListener;
    private static final String TAG = TTSManager.class.getSimpleName();
    private String envPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/Download";
    private Uri fileUri;
    String destFileName = envPath + "/" + "tts_file.wav";
    //status check code
    private int MY_DATA_CHECK_CODE = 0;

    public TTSManager(Context context) {
        mCtx = context;
        myTTS = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale loc = new Locale("bs", "#latn");
            myTTS.setLanguage(loc);
        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(mCtx, R.string.error_on_init_tts, Toast.LENGTH_LONG).show();
        }
    }

    private void setListener() {
        myTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                File fileTTS = new File(destFileName);

                if (fileTTS.exists()) {
                    Log.d(TAG, "successfully created fileTTS");
                    fileUri = Uri.fromFile(fileTTS);
                    if(mListener!=null)
                        mListener.onFinish(fileUri);

                } else {
                    Log.d(TAG, "failed while creating fileTTS");
                    if(mListener!=null)
                        mListener.onFinish(fileUri);
                }

            }

            @Override
            public void onError(String utteranceId) {
                if(mListener!=null)
                    mListener.onFinish(fileUri);;
            }

        });
    }

   /* public static synchronized TTSManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TTSManager(context);
        }
        return mInstance;
    }
*/
    public void synthesizeToFIle(String text, final onSynthesizeFinish listener) {
        this.mListener = listener;
        setListener();
        HashMap<String, String> myHashRender = new HashMap<String, String>();
        myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, text);
        myTTS.synthesizeToFile(text, myHashRender, destFileName);
    }

    public void stop(){
        myTTS.stop();
        myTTS.shutdown();
    }

}



