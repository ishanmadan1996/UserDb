package com.example.ishant.realdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Admin on 11-06-2017.
 */

public class TourHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "EXPLORECA";

    private static final String DATABASE_NAME = "tours.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_TOURS = "tours";
    public static final String COLUMN_ID = "tourId";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE = "image";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TOURS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_DESC + " TEXT, " +
                    COLUMN_IMAGE + " TEXT, " +
                    COLUMN_PRICE + " NUMERIC " +
                    ")";


    public TourHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
        Log.i(LOGTAG, "Table has been Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURS);
        onCreate(db);

    }
}