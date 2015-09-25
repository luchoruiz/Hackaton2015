package com.android.desafioaudionews.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ferjuarez on 25/09/15.
 */

@DatabaseTable(tableName = "image")
public class Image {
    @DatabaseField(generatedId = true)
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


}
