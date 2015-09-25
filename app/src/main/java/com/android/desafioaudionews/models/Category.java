package com.android.desafioaudionews.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ferjuarez on 25/09/15.
 */

@DatabaseTable(tableName = "category")
public class Category {
    @DatabaseField(generatedId = true)
    public Integer id;

    @DatabaseField
    public String valor;

    public Category(){
        // needed by ormlite
    }

}
