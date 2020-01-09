package cn.baibeiyun.attestation.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cn.baibeiyun.attestation.config.IConstant;
import cn.baibeiyun.attestation.util.language.LanguageType;
import cn.baibeiyun.attestation.util.language.LanguageUtil;

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
        if (TextUtils.isEmpty(spUtils.getString(IConstant.LANGUAGE_TYPE, ""))) {
        }
        spUtils.put(IConstant.LANGUAGE_TYPE, LanguageType.THAILAND.getLanguage());

        setLanguage();
    }




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }



    /**
     * 设置语言
     */
    private void setLanguage() {
        /**
         * 对于7.0以下，需要在Application创建的时候进行语言切换
         */
        String language = spUtils.getString(IConstant.LANGUAGE_TYPE, "");
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(getApplicationContext(), language);
//        }
    }
}
