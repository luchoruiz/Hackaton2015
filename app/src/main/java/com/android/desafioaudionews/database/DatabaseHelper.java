package com.android.desafioaudionews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.desafioaudionews.models.Category;
import com.android.desafioaudionews.models.CategoryNote;
import com.android.desafioaudionews.models.Note;
import com.android.desafioaudionews.models.Tag;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    public static String database_name = "audionews_bd";
    private RuntimeExceptionDao<Note, Integer> noteDao = null;
    private RuntimeExceptionDao<Tag, Integer> tagDao = null;
    private RuntimeExceptionDao<Category, Integer> categoryDao = null;
    private RuntimeExceptionDao<CategoryNote, Integer> categorynoteDao = null;

    public DatabaseHelper(Context context) {
        super(context, database_name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Note.class);
            TableUtils.createTable(connectionSource, Tag.class);
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
            TableUtils.dropTable(connectionSource, Tag.class, true);
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

    public RuntimeExceptionDao<Tag, Integer> getTagDao() {
        if (tagDao == null) {
            tagDao = getRuntimeExceptionDao(Tag.class);
        }
        return tagDao;
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
        String query = "Select note.id," +
                "note.origen," +
                "note.prioridad," +
                "note.fecha, " +
                "note.fechaActualizacion," +
                "note.url," +
                "note.abiertoComentarios, " +
                "note.titulo, " +
                "note.bajada," +
                "note.contenido, " +
                "note.entrada_id," +
                "note._t, " +
                "note.epigrafe" +
                "note.imageSrc" +
                "note.isFavorite" +
                " FROM note JOIN categorynote where categorynote.id = " + categoryID + " ";


        List<Note> results = new ArrayList<Note>();
        GenericRawResults<Note> rawResults = getNoteDao().queryRaw(query,
                new RawRowMapper<Note>() {
                    public Note mapRow(String[] columnNames,
                                       String[] resultColumns) {

                        Note note = new Note();
                        note.id = Integer.valueOf((resultColumns[0]));
                        note.origen = resultColumns[1];
                        note.prioridad = resultColumns[2];
                        note.fecha = resultColumns[3];
                        note.fechaActualizacion = resultColumns[4];
                        note.url = resultColumns[5];
                        note.abiertoComentarios = resultColumns[6];
                        note.titulo = resultColumns[7];
                        note.bajada = resultColumns[8];
                        note.contenido = resultColumns[9];
                        note.entrada_id = resultColumns[10];
                        note._t = resultColumns[11];
                        note.epigrafe = resultColumns[12];
                        note.imageSrc = resultColumns[13];
                        note.isFavorite = Boolean.valueOf(resultColumns[14]);
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

    /*
	 * Close database and null all daos
	 *
	 * */
    @Override
    public void close() {
        super.close();
        noteDao = null;
        tagDao = null;
        categoryDao = null;
        categorynoteDao = null;
    }

}