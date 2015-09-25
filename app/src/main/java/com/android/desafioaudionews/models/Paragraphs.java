package com.android.desafioaudionews.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ferjuarez on 25/09/15.
 */
@DatabaseTable(tableName = "paragraph")
public class Paragraphs {

    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField
    public String valor;


    public Paragraphs(){
        // needed by ormlite
    }





}
