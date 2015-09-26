package com.android.desafioaudionews.models;

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

@DatabaseTable(tableName = "tag")
public class Tag {
    @DatabaseField(id = true)
    public Integer id;
    @DatabaseField
    public String valor;
    @DatabaseField
    public String tipo_id;
    @DatabaseField
    public String formato_id;


    public Tag(){
        // needed by ormlite
    }


    public static List<Tag> parseTags(JSONArray arrayTag) {
        List<Tag> tags = new ArrayList<Tag>();
        for(int i = 0; i < arrayTag.length(); i++){
            Tag tag = new Tag();
            JSONObject jsonObjectTag = null;
            try {
                jsonObjectTag = arrayTag.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                tag.id = jsonObjectTag.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                tag.valor = jsonObjectTag.getString("valor");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                tag.tipo_id = jsonObjectTag.getString("tipo_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                tag.formato_id = jsonObjectTag.getString("formato_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tags.add(tag);

        }
        return tags;
    }



}
