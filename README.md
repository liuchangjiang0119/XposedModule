# Xposed模块开发(换个方式学Android)

	>有个进程叫做“Zygote ”。这是Android运行环境的的核心。每一个应用程序启动都是通过它fork出来的。当手机被启动就会执行/init.rc这个脚本，这个进程会启动/system/bin/app_process ，然后他会调加载所需的类并调用初始化函数。 现在说说Xposed什么时候开始启动。当您安装了Xposed，它会把修改的app_process可执行文件复制到/system/bin中。这个修改过的启动进程增加了一个额外的jar包到classpath路径，并在某些地方调用那里的方法。例如，在虚拟机刚刚创建后，在Zygote 的main方法调用后。我们的jar包也可以Zygote里面工作。这个jar包位于：/data/data/de.robv.android.xposed.installer/bin/XposedBridge.jar和它的源代码可以在这里找到（[here](https://github.com/rovo89/XposedBridge)）。综观类XposedBridge，你可以看到的main 方法。这就是我上面写的，这个类会在进程启动之前被调用。在那时候执行一些初始化和模块的加载（我会在后面讲解模块的加载）。

## AndroidManifest.xml

```xml
<meta-data     android:name="xposedmodule"    android:value="true"/> 
<meta-data     android:name="xposeddescription"     android:value="It's a xposedmodule test"/> <meta-data     android:name="xposedminversion"     android:value="54"/>
```





## TODO

* 登录劫持

```java
public class XposedTestClass implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam param) throws Throwable {
        if(!param.packageName.equals("com.example.login"))
        XposedBridge.log("app name"+param.packageName);
      /*@param 要hook的类名
      **@param classLoader
      **@param 要hook的方法名
      **@param 传入的参数类型
      **@param 回调方法
      */
      	 findAndHookMethod("com.example.login.MainActivity", param.classLoader,"login",String.class,String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("账号："+(String) param.args[0]);
                XposedBridge.log("密码："+(String) param.args[1]);
                Log.d("--------------》",""+param.args[0]);
                Log.d("--------------》",""+param.args[1]);

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("账号："+param.args[0]);
                XposedBridge.log("密码："+param.args[1]);
            }
        });
    }
}
```

* 工具apktool、Android killer
  * apktool 反编译
  * Android killer 工程内字段搜索，找到要hook的方法名

* 第一阶段Hook成果

  ​	由于其他App如QQ等加入了代码混淆，且代码量较大，所以选择了清水河畔客户端。。。。。

  版本为1.0.20

  ![image](.\Screenshot_2016-09-28-22-48-49_de.robv.android.xposed.installer.png)

  ​

* 接下来打算hook QQ的登录以及微信运动步数。
