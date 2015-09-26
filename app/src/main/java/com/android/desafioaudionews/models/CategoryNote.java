package com.android.desafioaudionews.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ferjuarez on 25/09/15.
 */
@DatabaseTable(tableName = "categorynote")
public class CategoryNote {
    @DatabaseField(generatedId = true)
    public Integer id;

    @DatabaseField()
    public int categoryId;

    @DatabaseField(unique = true)
    public int noteId;

    public CategoryNote(){

    }

    public CategoryNote(int categoryId, int noteId){
        this.categoryId = categoryId;
        this.noteId = noteId;
    }


}
