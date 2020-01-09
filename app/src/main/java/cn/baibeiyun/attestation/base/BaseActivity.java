package cn.baibeiyun.attestation.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.blankj.utilcode.util.KeyboardUtils;
import com.gyf.barlibrary.ImmersionBar;

import java.util.Locale;

import butterknife.ButterKnife;
import cn.baibeiyun.attestation.util.AppManager;
import cn.baibeiyun.attestation.util.LanguageUtil;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * 作者 by wuliang 时间 16/10/31.
 * <p>
 * 所有activity的基类，此处建立了一个activity的栈，用于管理activity
 */

public abstract class BaseActivity extends SupportActivity {


    private SVProgressHUD svProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayout());
        ButterKnife.bind(this);
        ImmersionBar.with(this).keyboardEnable(true).init();   //解决虚拟按键与状态栏沉浸冲突
        AppManager.getAppManager().addActivity(this);
        svProgressHUD = new SVProgressHUD(this);
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.hideSoftInput(this);
        ImmersionBar.with(this).destroy();
        AppManager.getAppManager().removeActivity(this);
    }

    /**
     * 常用的跳转方法
     */
    public void gotoActivity(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

    public void gotoActivity(Class<?> cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }


    /**
     * 显示加载进度弹窗
     */
    protected void showProgress() {
        svProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
    }

    /**
     * 停止弹窗
     */
    protected void stopProgress() {
        if (svProgressHUD.isShowing()) {
            svProgressHUD.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopProgress();
    }

//    /**
//     * 设置返回
//     */
//    protected void goBack() {
//        LinearLayout imageView = findViewById(R.id.back);
//        imageView.setVisibility(View.VISIBLE);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//    /**
//     * 设置标题
//     *
//     * @param title
//     */
//    protected void setTitleText(String title) {
//        TextView titleTex = findViewById(R.id.title_text);
//        titleTex.setText(title);
//    }



//    public void onClick(View view) {
//        boolean need = false;
//        switch (view.getId()) {
//            case R.id.chinese:
//                need = LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_CHINESE);
//                if (need) {
//                    //自己写的常用activity管理工具
//                    ActivityManager.getInstance().recreateAllOtherActivity(this);
//                    Toast.makeText(this, "change to chinese", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "no need", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.english:
//                need = LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_ENGLISH);
//                if (need) {
//                    ActivityManager.getInstance().recreateAllOtherActivity(this);
//                    Toast.makeText(this, "change to english", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "no need", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.russian:
//                need = LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_RUSSIAN);
//                if (need) {
//                    ActivityManager.getInstance().recreateAllOtherActivity(this);
//                    Toast.makeText(this, "change to russian", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "no need", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }


    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    //调用隐藏系统默认的输入法
    public static void showOrHide(Context context, Activity activity) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }


    //获取输入法打开的状态
    public static boolean isShowing(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();//isOpen若返回true，则表示输入法打开
    }


    protected abstract int getLayout();

    public void onRequestEnd() {

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = languageWork(newBase);
        super.attachBaseContext(context);

    }

    private Context languageWork(Context context) {
        // 8.0及以上使用createConfigurationContext设置configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return updateResources(context);
        } else {
            return context;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Context updateResources(Context context) {
        Resources resources = context.getResources();
        Locale locale = LanguageUtil.getLocale(context);
        if (locale==null) {
            return context;
        }
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }
}
