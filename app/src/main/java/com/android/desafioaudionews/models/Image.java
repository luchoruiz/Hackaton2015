package com.android.desafioaudionews.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ferjuarez on 25/09/15.
 */

@DatabaseTable(tableName = "image")
public class Image {
    @DatabaseField(id = true)
    public Integer id;
    @DatabaseField
    public String valor;
    @DatabaseField
    public String extension;
    @DatabaseField
    public String src;
    @DatabaseField
    public String alto;
    @DatabaseField
    public String ancho;
    @DatabaseField
    public String epigrafe;


    public Image(){
        // needed by ormlite
    }

    public static Image parseImage(JSONArray arrayImg) {
       Image image = new Image();
        if(arrayImg.length() > 0){
            try {
                JSONObject jsonObject = arrayImg.getJSONObject(0);
                image.src = jsonObject.getString("src");
                image.epigrafe = ((JSONObject)jsonObject.getJSONObject("epigrafe")).getString("valor");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

}
