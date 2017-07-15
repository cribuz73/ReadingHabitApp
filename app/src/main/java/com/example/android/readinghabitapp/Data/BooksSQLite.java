package com.example.android.readinghabitapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cristi on 7/14/2017.
 */

public class BooksSQLite extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "bookshelf.db";
    public static final int DATABASE_VERSION = 1;

    public BooksSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static final String TEXT = " TEXT ";
    public static final String INTEGER = " INTEGER ";
    public static final String COMMA = ", ";


    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + BooksContract.BooksEntry.TABLE_NAME + " (" +
            BooksContract.BooksEntry._ID + INTEGER + "PRIMARY KEY AUTOINCREMENT" + COMMA +
            BooksContract.BooksEntry.COLUMN_AUTHOR + TEXT + "NOT NULL" + COMMA +
            BooksContract.BooksEntry.COLUMN_TITLE + TEXT + "NOT NULL" + COMMA +
            BooksContract.BooksEntry.COLUMN_PUBLISHER + TEXT + COMMA +
            BooksContract.BooksEntry.COLUMN_YEAR + TEXT + COMMA +
            BooksContract.BooksEntry.COLUMN_STATUS + INTEGER + "NOT NULL DEFAULT 0" + ");";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BooksContract.BooksEntry.TABLE_NAME;


    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
