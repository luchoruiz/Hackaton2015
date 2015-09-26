package com.android.desafioaudionews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.desafioaudionews.models.Category;
import com.android.desafioaudionews.models.CategoryNote;
import com.android.desafioaudionews.models.Note;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static String database_name = "audionews_bd";
    private RuntimeExceptionDao<Note, Integer> noteDao = null;
    private RuntimeExceptionDao<Category, Integer> categoryDao = null;
    private RuntimeExceptionDao<CategoryNote, Integer> categorynoteDao = null;

    public DatabaseHelper(Context context) {
        super(context, database_name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Note.class);
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, CategoryNote.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Note.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, CategoryNote.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public RuntimeExceptionDao<Note, Integer> getNoteDao() {
        if (noteDao == null) {
            noteDao = getRuntimeExceptionDao(Note.class);
        }
        return noteDao;
    }


    public RuntimeExceptionDao<Category, Integer> getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = getRuntimeExceptionDao(Category.class);
        }
        return categoryDao;
    }

    public RuntimeExceptionDao<CategoryNote, Integer> getCategoryNoteDao() {
        if (categorynoteDao == null) {
            categorynoteDao = getRuntimeExceptionDao(CategoryNote.class);
        }
        return categorynoteDao;
    }

    public void deleteAllNotes(){
        List<Note> allNotes = getNoteDao().queryForAll();
        for (int i = 0; i < allNotes.size(); i++) {
            getNoteDao().deleteById(allNotes.get(i).id);
        }
    }


    public  List<Note> getNotesByCategoryID(int categoryID){
        String query = "Select note.id, note.titulo, note.bajada, note.url, note.imageSrc, note.epigrafe,note.imageHeight,note.imageWidth FROM note JOIN categorynote where note.id = categorynote.noteId AND categorynote.categoryId = " + categoryID + " order by note.fecha";
        List<Note> results = new ArrayList<Note>();
        GenericRawResults<Note> rawResults = getNoteDao().queryRaw(query,
                new RawRowMapper<Note>() {
                    public Note mapRow(String[] columnNames,
                                       String[] resultColumns) {

                        Note note = new Note();
                        note.id = Integer.valueOf((resultColumns[0]));
                        note.titulo = resultColumns[1];
                        note.bajada = resultColumns[2];
                        note.url = resultColumns[3];
                        note.imageSrc = resultColumns[4];
                        note.epigrafe = resultColumns[5];
                        note.imageHeight = Integer.valueOf(resultColumns[6]);
                        note.imageWidth = Integer.valueOf(resultColumns[7]);

                        return note;
                    }
                });

        try {
            results = rawResults.getResults();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return results;
    }

    public  List<Note> getFavouritesNotes(){
        String query = "Select note.id, note.titulo, note.bajada, note.url, note.imageSrc, note.epigrafe,note.imageHeight,note.imageWidth FROM note where note.isFavorite = 1 order by note.fecha";
        List<Note> results = new ArrayList<Note>();
        GenericRawResults<Note> rawResults = getNoteDao().queryRaw(query,
                new RawRowMapper<Note>() {
                    public Note mapRow(String[] columnNames,
                                       String[] resultColumns) {

                        Note note = new Note();
                        note.id = Integer.valueOf((resultColumns[0]));
                        note.titulo = resultColumns[1];
                        note.bajada = resultColumns[2];
                        note.url = resultColumns[3];
                        note.imageSrc = resultColumns[4];
                        note.epigrafe = resultColumns[5];
                        note.imageHeight = Integer.valueOf(resultColumns[6]);
                        note.imageWidth = Integer.valueOf(resultColumns[7]);

                        return note;
                    }
                });

        try {
            results = rawResults.getResults();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }


    public  void deleteAllFavourites(List<Note> notes){
        for (Note note:notes){
            setAsFavourite(note.id, false);
        }
    }

    public  void setAsFavourite(int noteId){
        UpdateBuilder<Note, Integer> updateBuilder = getNoteDao().updateBuilder();
        try {

            updateBuilder.updateColumnValue("isFavorite", true);
            updateBuilder.where().eq("id", noteId);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void setAsFavourite(int noteId, boolean value){
        UpdateBuilder<Note, Integer> updateBuilder = getNoteDao().updateBuilder();
        try {

            updateBuilder.updateColumnValue("isFavorite", value);
            updateBuilder.where().eq("id", noteId);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
	 * Close database and null all daos
	 *
	 * */
    @Override
    public void close() {
        super.close();
        noteDao = null;
        categoryDao = null;
        categorynoteDao = null;
    }

}