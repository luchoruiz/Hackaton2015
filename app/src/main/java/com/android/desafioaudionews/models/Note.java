package com.android.desafioaudionews.models;

import android.util.Log;

import com.arasthel.asyncjob.AsyncJob;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferjuarez on 25/09/15.
 */

@DatabaseTable(tableName = "note")
public class Note {
    @DatabaseField(generatedId = true)
    public Integer id;

    public Note(){
        // needed by ormlite
    }

    @SerializedName("origen")
    @DatabaseField
    public String origen;

    @SerializedName("prioridad")
    @DatabaseField
    public String prioridad;

    @SerializedName("fecha")
    @DatabaseField
    public String fecha;

    @SerializedName("fechaActualizacion")
    @DatabaseField
    public String fechaActualizacion;

    @SerializedName("url")
    @DatabaseField
    public String url;

    @SerializedName("abiertoComentarios")
    @DatabaseField
    public String abiertoComentarios;

    @SerializedName("titulo")
    @DatabaseField
    public String titulo;

    @SerializedName("bajada")
    @DatabaseField
    public String bajada;

    @SerializedName("contenido")
    @DatabaseField
    public String contenido;

    @SerializedName("entrada_id")
    @DatabaseField
    public String entrada_id;

    @SerializedName("_t")
    @DatabaseField
    public String _t;

    @SerializedName("tags")
    @ForeignCollectionField(eager = false)
    ForeignCollection<Tag> tags;

    @SerializedName("categories")
    @ForeignCollectionField(eager = false)
    ForeignCollection<Category> categories;

    @SerializedName("images")
    @ForeignCollectionField(eager = false)
    ForeignCollection<Image> images;


    public static List<Note> asyncParseNotes(JSONObject strNotes){
        List<Note> notes = new ArrayList<>();
        JSONArray notesJSONArray = null;
        try {
            if (strNotes.has("items"))
                notesJSONArray = strNotes.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (notesJSONArray.length() > 0) {
            //To prevent errors when json is empty
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Type listType = new TypeToken<List<Note>>(){}.getType();
            notes = gson.fromJson(String.valueOf(notesJSONArray), listType);

        }
        return notes;

    }

    public static void parseAsync(final JSONObject strNotes) {

        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {
                        List<Note> notes = asyncParseNotes(strNotes);
                        return true;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                        Log.e("asdasda", "asdasdas");
                        //Toast.makeText(context, "Result was: " + result, Toast.LENGTH_SHORT).show();
                    }
                }).create().start();



    }






}

