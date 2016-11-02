package com.example.wechatxposed;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import de.robv.android.xposed.IXposedHookLoadPackage;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by dell on 2016/9/29.
 */

public class WeChatXposedModule implements IXposedHookLoadPackage {

    private int num;
    private Context mContext;
    private static final Uri mUri = Uri.parse("content://com.example.wechatxposed.provider.dataProvider/data_db");
    private String []morraList = new String[]{
            "剪刀","石头","布"
    };

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam param) throws Throwable {
        if (!param.packageName.contains("com.tencent.mm"))
            return;
        XposedBridge.log("包名：" + param.packageName);

        findAndHookMethod("com.tencent.mm.ui.MMFragmentActivity", param.classLoader,"onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if (mContext==null){
                        mContext = (Context) param.thisObject;
                        XposedBridge.log("Get Context");
                    }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

            }
        });

        findAndHookMethod("com.tencent.mm.sdk.platformtools.be", param.classLoader, "tx",int.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                int type = (int)param.args[0];
                switch (type){
                    case 5:
                        Cursor cursor = mContext.getContentResolver().query(mUri,null,null,null,null);
                        if (cursor!=null){
                            while (cursor.moveToNext()){
                                num = cursor.getInt(cursor.getColumnIndex("num"));
                                XposedBridge.log("你选择的色子点数为："+num+1);
                            }
                        }
                        break;
                    case 2:
                        Cursor cursor2 = mContext.getContentResolver().query(mUri,null,null,null,null);
                        if (cursor2!=null){
                            while (cursor2.moveToNext()){
                                num = cursor2.getInt(cursor2.getColumnIndex("morra_num"));
                                XposedBridge.log("你选择猜拳为："+morraList[num]);
                            }
                        }
                        break;
                }
               return num;

            }
        });

    }







}
