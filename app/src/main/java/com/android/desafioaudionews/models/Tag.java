package com.android.desafioaudionews.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ferjuarez on 25/09/15.
 */

@DatabaseTable(tableName = "tag")
public class Tag {
    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField
    public String valor;
    @DatabaseField
    public String tipo_id;
    @DatabaseField
    public String formato_id;
    @DatabaseField
    public String categoriaPeso;

    public Tag(){
        // needed by ormlite
    }

}
