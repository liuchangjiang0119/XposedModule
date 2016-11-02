package com.example.wechatxposed.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by dell on 2016/10/1.
 */

public class DataProvider extends ContentProvider {
    private DatabaseHelper mDatabaseHelper;
    private static final int NUM = 1;
    private static final int MORRA = 2;
    private static final String CONTENT = "com.example.wechatxposed.provider.dataProvider";
    private static  final Uri mUri = Uri.parse("content://com.example.wechatxposed.provider.dataProvider");
    private static  UriMatcher matcher;
    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT,"data_db",NUM);
        matcher.addURI(CONTENT,"data_db/#",NUM);
        matcher.addURI(CONTENT,"data_db",MORRA);
        matcher.addURI(CONTENT,"data_db/#",MORRA);
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper=new DatabaseHelper(getContext());
        return (mDatabaseHelper==null) ? false:true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {


        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
