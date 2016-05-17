package com.sun.firewalldemo.blacklist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by S on 2016/5/4.
 */
public class BlackListDB extends SQLiteOpenHelper {

    public BlackListDB(Context context) {
        super(context, "blacklist.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table blacklist(_id integer primary key autoincrement,name text,phone text," +
                "mode integer)");
        db.execSQL("create table message(_id integer primary key autoincrement,name text ,phone text," +
                "mode integer,content text,time text)");
        db.execSQL("create table phonelog(_id integer primary key autoincrement,name text ,phone text," +
                "time text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
