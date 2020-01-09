package cn.baibeiyun.attestation.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;

import java.util.Locale;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cn.baibeiyun.attestation.util.LanguageUtil;

/**
 * 作者 by wuliang 时间 16/10/26.
 * <p>
 * 程序的application
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    public static SPUtils spUtils;

    public static String token;

    public static boolean AppInBack = false;  //App 是否在后台

    @Override
    public void onCreate() {
        super.onCreate();
        CustomActivityOnCrash.install(this);
//        CustomActivityOnCrash.setErrorActivityClass(CustomErrorActivity.class);
        /***初始化工具类*/
        Utils.init(this);
        spUtils = SPUtils.getInstance(TAG);
//        Fragmentation.getDefault().setMode(Fragmentation.BUBBLE);

        registerActivityLifecycleCallbacks(new AppLifecycleHandler());
        languageWork();
    }




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        languageWork();
    }



    private void languageWork() {
        //自己写的工具包（如下）
        Locale locale = LanguageUtil.getLocale(this);
        LanguageUtil.updateLocale(this, locale);
    }
}
