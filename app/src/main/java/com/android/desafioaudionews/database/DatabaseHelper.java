package com.android.desafioaudionews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.desafioaudionews.models.Note;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static String database_name = "audionews_bd";
    private RuntimeExceptionDao<Note, Integer> noteDao = null;

    public DatabaseHelper(Context context) {
        super(context, database_name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Note.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Note.class, true);
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


    public void deleteAllNotes(){
        List<Note> allNotes = getNoteDao().queryForAll();
        for (int i = 0; i < allNotes.size(); i++) {
            getNoteDao().deleteById(allNotes.get(i).id);
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
    }

}