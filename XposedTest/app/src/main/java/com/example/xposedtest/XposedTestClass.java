package com.example.xposedtest;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by dell on 2016/9/25.
 */
public class XposedTestClass implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam param) throws Throwable {
        if(!param.packageName.contains("com.appbyme"))
            return;
        XposedBridge.log("app name:"+param.packageName);

        findAndHookMethod("com.mobcent.share.model.PlatformLoginInfoModel", param.classLoader,"setDzUserName",String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("账号："+(String) param.args[0]);


            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("账号："+param.args[0]);

            }

        });
        findAndHookMethod("com.mobcent.share.model.PlatformLoginInfoModel", param.classLoader, "setDzPassword", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("密码："+param.args[0]);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("密码："+param.args[0]);
            }
        });
    }
}
