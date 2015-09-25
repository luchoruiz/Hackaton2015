package com.android.desafioaudionews.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

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

    @ForeignCollectionField(eager = false)
    ForeignCollection<Tag> tags;

    @ForeignCollectionField(eager = false)
    ForeignCollection<Category> categories;

    @ForeignCollectionField(eager = false)
    ForeignCollection<Image> images;
}

