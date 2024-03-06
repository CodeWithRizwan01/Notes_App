package com.example.quicknotes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Notes";
    public static final int DATABASE_VERSION = 3;

    protected static DbHelper instance;
    public static DbHelper getInstance(Context context){
        if (instance == null){
            instance = new DbHelper(context);
        }
        return instance;
    }

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Notes.CREATE_TABLE);
        db.execSQL(Notes.CREATE_DELETE_TABLE);
        db.execSQL(Notes.CREATE_ARCHIVE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL(Notes.DROP_TABLE);
            db.execSQL(Notes.CREATE_TABLE);

            db.execSQL(Notes.DROP_DELETE_TABLE);
            db.execSQL(Notes.CREATE_DELETE_TABLE);

            db.execSQL(Notes.DROP_ARCHIVE_TABLE);
            db.execSQL(Notes.CREATE_ARCHIVE_TABLE);
        }
    }

    // CRUD Method for Main Table
    public Boolean insertNote(String title, String message){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Notes.KEY_TITLE, title);
        values.put(Notes.KEY_MESSAGE, message);

        long effectRows = 0;
        try {
            effectRows = database.insert(Notes.TABLE_NAME, null, values);
        }
        catch (Exception ex){
            return false;
        }
        return effectRows == 1;
    }

    public Boolean updateNote(String id, String title, String message){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Notes.KEY_ID, id);
        values.put(Notes.KEY_TITLE, title);
        values.put(Notes.KEY_MESSAGE, message);

        long effectRows = 0;
        try {
            effectRows = database.update(Notes.TABLE_NAME, values, Notes.KEY_ID+"= ?", new String[]{id});
        }
        catch (Exception ex){
            return false;
        }
        return effectRows == 1;
    }

    public Boolean deleteNote(String id){
        SQLiteDatabase database = getWritableDatabase();

        long effectRows = 0;
        try {
            effectRows = database.delete(Notes.TABLE_NAME, Notes.KEY_ID+"= ?", new String[]{id});
        }
        catch (Exception ex){
            return false;
        }
        return effectRows == 1;
    }

    @SuppressLint("Range")
    public ArrayList<Notes> fetchNotes(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(Notes.SELECT_ALL, null);

        ArrayList<Notes> notesList = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()){
            do {
                Notes notes1 = new Notes();
                notes1.setId(cursor.getString(cursor.getColumnIndex(Notes.KEY_ID)));
                notes1.setTitle(cursor.getString(cursor.getColumnIndex(Notes.KEY_TITLE)));
                notes1.setMessage(cursor.getString(cursor.getColumnIndex(Notes.KEY_MESSAGE)));
                notesList.add(notes1);
            }while (cursor.moveToNext());
        }
        return notesList;
    }


    //     CRUD Method for Delete Table
    public Boolean insertDeleteNote(String id, String title, String message){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Notes.KEY_ID, id);
        values.put(Notes.KEY_TITLE, title);
        values.put(Notes.KEY_MESSAGE, message);

        long effectRows = 0;
        try {
            effectRows = database.insert(Notes.DELETE_TABLE, null, values);
        }
        catch (Exception ex){
            return false;
        }
        return effectRows == 1;
    }

    public Boolean removeDeleteNote(String id){
        SQLiteDatabase database = getWritableDatabase();

        long effectRows = 0;
        try {
            effectRows = database.delete(Notes.DELETE_TABLE, Notes.KEY_ID+"= ?", new String[]{id});
        }
        catch (Exception ex){
            return false;
        }
        return effectRows == 1;
    }

    @SuppressLint("Range")
    public ArrayList<Notes> fetchDeleteNotes(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(Notes.SELECT_DELETE_TABLE, null);

        ArrayList<Notes> notesList = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()){
            do {
                Notes notes1 = new Notes();
                notes1.setId(cursor.getString(cursor.getColumnIndex(Notes.KEY_ID)));
                notes1.setTitle(cursor.getString(cursor.getColumnIndex(Notes.KEY_TITLE)));
                notes1.setMessage(cursor.getString(cursor.getColumnIndex(Notes.KEY_MESSAGE)));
                notesList.add(notes1);
            }while (cursor.moveToNext());
        }
        return notesList;
    }

    //     CRUD Method for Delete Table
    public Boolean insertArchiveNote(String id, String title, String message){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Notes.KEY_ID, id);
        values.put(Notes.KEY_TITLE, title);
        values.put(Notes.KEY_MESSAGE, message);

        long effectRows = 0;
        try {
            effectRows = database.insert(Notes.ARCHIVE_TABLE, null, values);
        }
        catch (Exception ex){
            return false;
        }
        return effectRows == 1;
    }

    public Boolean removeArchiveNote(String id){
        SQLiteDatabase database = getWritableDatabase();

        long effectRows = 0;
        try {
            effectRows = database.delete(Notes.ARCHIVE_TABLE, Notes.KEY_ID+"= ?", new String[]{id});
        }
        catch (Exception ex){
            return false;
        }
        return effectRows == 1;
    }

    @SuppressLint("Range")
    public ArrayList<Notes> fetchArchiveNotes(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(Notes.SELECT_ARCHIVE_TABLE, null);

        ArrayList<Notes> notesList = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()){
            do {
                Notes notes1 = new Notes();
                notes1.setId(cursor.getString(cursor.getColumnIndex(Notes.KEY_ID)));
                notes1.setTitle(cursor.getString(cursor.getColumnIndex(Notes.KEY_TITLE)));
                notes1.setMessage(cursor.getString(cursor.getColumnIndex(Notes.KEY_MESSAGE)));
                notesList.add(notes1);
            }while (cursor.moveToNext());
        }
        return notesList;
    }

}
