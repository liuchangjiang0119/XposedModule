package com.example.wechatxposed.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by dell on 2016/10/1.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "data_db";
    private static final String DATABASE_NAME = "data.db";
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table "+TABLE_NAME+"( _id INTEGER PRIMARY KEY AUTOINCREMENT, num INTEGER, morra_num INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);
    }
//    public Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder){
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        sqLiteDatabase.query()
//
//    }
    public void add(ContentValues contentValues){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int rowId = sqLiteDatabase.update(TABLE_NAME,contentValues,null,null);
        if (rowId == 0){
            sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        }
    }
}
