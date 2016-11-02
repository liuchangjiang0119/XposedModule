package com.example.wechatxposed;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wechatxposed.provider.DatabaseHelper;


public class MainActivity extends AppCompatActivity {


    Button num_btn,morra_btn;
    TextView num_text,morra_text;
    String []numList = new String[]{
            "1","2","3","4","5","6"
    };

    String []morraList = new String[]{
            "剪刀","石头","布"
    };

    private int num ;
    private int morra;

    private Uri mUri = Uri.parse("content://com.example.wechatxposed.provider.dataProvider/data_db");
    private DatabaseHelper mDatabaseHelper;
    private ContentValues mContentValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContentValues = new ContentValues();
        mDatabaseHelper = new DatabaseHelper(this);

        num_btn = (Button) findViewById(R.id.setNum_btn);
        morra_btn = (Button) findViewById(R.id.setMorra_btn);
        morra_text = (TextView) findViewById(R.id.morra_text);
        num_text = (TextView) findViewById(R.id.num_text);

        getTextFromDb();

        num_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("请选择点数：")
                        .setSingleChoiceItems(numList, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                num = which;

                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mContentValues.put("num",num);
                                mDatabaseHelper.add(mContentValues);
                                getTextFromDb();


                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();


            }
        });

        morra_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("请选择猜拳：")
                        .setSingleChoiceItems(morraList, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                morra = which;

                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mContentValues.put("morra_num",morra);
                                mDatabaseHelper.add(mContentValues);
                                getTextFromDb();

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();


            }

        });


    }

    public void getTextFromDb(){
        Cursor cursor =getContentResolver().query(mUri,null,null,null,null);
        if (cursor==null) {
            num_text.setText("当前未设置色子点数");
            morra_text.setText("当前未设置猜拳类型");
        }else {
            while (cursor.moveToNext()){
                int result_num = cursor.getInt(cursor.getColumnIndex("num"));
                int result_morra = cursor.getInt(cursor.getColumnIndex("morra_num"));
                num_text.setText("当前色子点数为:"+(result_num+1));
                morra_text.setText("当前猜拳为："+morraList[result_morra]);
            }


        }

    }


}
