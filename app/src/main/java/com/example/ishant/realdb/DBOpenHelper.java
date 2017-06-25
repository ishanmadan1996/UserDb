package com.example.ishant.realdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11-06-2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "EXPLORECA";

    private static final String DATABASE_NAME = "UserInfo.db";
    private static final int DATABASE_VERSION = 14;

    public static final String TABLE_NAME = "Contact_Info";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHNO = "phno";
    public static final String COLUMN_GROUP = "groups";

    public static final String COLUMN_TEAM = "team";

    private static final String TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT , " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PHNO + " TEXT, " +
                    COLUMN_GROUP + " TEXT, " +
                    COLUMN_TEAM + " TEXT " +

                    ")";


    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL(TABLE);
            Log.i(LOGTAG, "Table has been Created");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData (String name,String email,String phno,String groups,String team)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_PHNO,phno);
        contentValues.put(COLUMN_GROUP,groups);
        contentValues.put(COLUMN_TEAM,team);
        Long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return  false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String name,String email,String phno,String groups,String team) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID,id);
        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_PHNO,phno);
        contentValues.put(COLUMN_GROUP,groups);
        contentValues.put(COLUMN_TEAM,team);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }
    public Cursor showAll(String []string )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME, new String[] {COLUMN_ID,COLUMN_NAME,COLUMN_EMAIL,COLUMN_PHNO,COLUMN_GROUP,COLUMN_TEAM},"name=?",string,null,null,null);
    }
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
    public List<ContactsConstructor> getAllContacts() {
        List<ContactsConstructor> contactList = new ArrayList<ContactsConstructor>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactsConstructor contact = new ContactsConstructor();

                contact.setPhoneNumber(cursor.getString(3));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

}

