package com.android.desafioaudionews.models;

import android.util.Log;

import com.arasthel.asyncjob.AsyncJob;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferjuarez on 25/09/15.
 */

@DatabaseTable(tableName = "note")
public class Note {
    @DatabaseField(id = true)
    public Integer id;

    public Note() {
        // needed by ormlite
    }

    @DatabaseField
    public String origen;

    @DatabaseField
    public String prioridad;

    @DatabaseField
    public String fecha;

    @DatabaseField
    public String fechaActualizacion;

    @DatabaseField
    public String url;

    @DatabaseField
    public String abiertoComentarios;

    @DatabaseField
    public String titulo;

    @DatabaseField
    public String bajada;

    @DatabaseField
    public String contenido;

    @DatabaseField
    public String entrada_id;

    @DatabaseField
    public String _t;

    @DatabaseField
    public String epigrafe;

    @DatabaseField
    public String imageSrc;

    @DatabaseField
    public boolean isFavorite;

    public Category category;


    @DatabaseField(foreign = true)
    Image image;


    public static List<Note> parseNotes(JSONObject strNotes){
        List<Note> notes = new ArrayList<>();
        JSONArray notesJSONArray = null;

        if (strNotes.has("items"))
            try {
                notesJSONArray = strNotes.getJSONArray("items");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        if (notesJSONArray.length() > 0) {
            for(int i = 0; i < notesJSONArray.length(); i++){
                Note aNote = new Note();
                try {
                    JSONObject jsonObj = (JSONObject) notesJSONArray.get(i);
                    aNote.id = jsonObj.getInt("id");
                    aNote.titulo = ((JSONObject) jsonObj.getJSONArray("titulo").get(0)).getString("valor");
                    aNote.bajada = ((JSONObject) jsonObj.getJSONArray("bajada").get(0)).getString("valor");
                    aNote.fecha = jsonObj.getString("fecha");
                    aNote.url = jsonObj.getString("url");
                    aNote.image = Image.parseImage(jsonObj.getJSONArray("imagenes"));
                    aNote.category = Category.parseCategory(jsonObj.getJSONObject("categoria"));
                    aNote.isFavorite = false;
                    notes.add(aNote);


                    //List<Tag> taskFieldsList = new ArrayList<Tag>(Tag.parseTags(jsonObj.getJSONArray("tags")));
                    //aNote.tags. = dao.getEmptyForeignCollection("entitiyCollection");
                    //for(Tag tag: taskFieldsList){
                    //    aNote.tags.add(tag);
                   // }

                } catch (JSONException e){

                }


            }
        }
        return notes;

    }

    public static void parseAsyncNotes(final JSONObject strNotes) {

        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {
                        List<Note> notes = parseNotes(strNotes);
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

