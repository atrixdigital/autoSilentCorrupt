package com.example.meveloper.autosilent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Meveloper on 8/22/2016.
 */
public class dbhelper extends SQLiteOpenHelper {


    public static final  String dbname = "Locations";
    public static final String table = "TotalLocations";
    public static final String id = "ID";
    public static final String name = "Name";
    public static final String longitude = "Longitude";
    public static final String latitude = "Latitude";


    dbhelper(Context context)
    {
        super(context, dbname, null, 1);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists " + table + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + name + " TEXT," + latitude + " TEXT," + longitude + " TEXT);");


    }

    public boolean insert(String Name, String lati, String longi)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(name, Name);
        values.put(latitude, lati);
        values.put(longitude, longi);

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


    public Cursor GetLocations()
    {
        Cursor data;
        SQLiteDatabase db = this.getReadableDatabase();
        data = db.rawQuery("SELECT * FROM " + table, null);
        return data;
    }

    public void delete(int i)
    {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table + " WHERE ID = " + i);
    }

    public void update(int i,String getname)
    {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(name, getname);
        db.update(table,values,id + " = ?", new String[]{String.valueOf(i)});
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
