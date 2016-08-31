package com.example.meveloper.mapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Meveloper on 8/9/2016.
 */
public class dbhelper extends SQLiteOpenHelper {


    public static final  String dbname = "Emp";
    public static final String table = "emptable";
    public static final String id = "ID";
    public static final String names = "name";
    public static final String contact = "contact";
    public static final String designation = "designation";


    dbhelper(Context context)
    {
        super(context, dbname, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists " + table + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + names + " TEXT," + contact + " TEXT," + designation + " TEXT);");


    }

    public boolean insert(String Name, String con, String desig)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(names, Name);
        values.put(contact, con);
        values.put(designation, desig);

        long check = db.insert(table, null, values);
        if(check == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    public Cursor GetEmployees()
    {
        Cursor data;
        SQLiteDatabase db = this.getReadableDatabase();
        data = db.rawQuery("SELECT * FROM " + table, null);
        return data;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
