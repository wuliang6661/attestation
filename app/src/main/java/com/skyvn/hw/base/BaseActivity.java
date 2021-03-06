package com.skyvn.hw.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.blankj.utilcode.util.KeyboardUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.skyvn.hw.R;
import com.skyvn.hw.config.IConstant;
import com.skyvn.hw.util.AppManager;
import com.skyvn.hw.util.ToastManager;
import com.skyvn.hw.util.language.LanguageUtil;
import com.skyvn.hw.view.KefuActivity;

import butterknife.ButterKnife;
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
        setContentView(getLayout());
        ButterKnife.bind(this);
        ImmersionBar.with(this).keyboardEnable(true).init();   //解决虚拟按键与状态栏沉浸冲突
        AppManager.getAppManager().addActivity(this);
        svProgressHUD = new SVProgressHUD(this);
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
        svProgressHUD.showWithStatus(getString(R.string.jiazaizhong), SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    /**
     * 显示识别中进度弹窗
     */
    protected void showShibieProgress() {
        svProgressHUD.showWithStatus(getString(R.string.shibiezhong), SVProgressHUD.SVProgressHUDMaskType.Black);
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

    /**
     * 设置返回
     */
    protected void goBack() {
        LinearLayout imageView = findViewById(R.id.back);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitleText(String title) {
        TextView titleTex = findViewById(R.id.title_text);
        titleTex.setText(title);
    }


    /**
     * 标题栏右图标显示并设置监听
     */
    protected void rightButton() {
        ImageView imageView = findViewById(R.id.right_img);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(v -> gotoActivity(KefuActivity.class, false));
    }


    protected void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        Toast toast = Toast.makeText(this, null, Toast.LENGTH_SHORT);
//        toast.setText(message);
//        toast.show();
        ToastManager.showShort(message);
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


    /**
     * 此方法先于 onCreate()方法执行
     *
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        //获取我们存储的语言环境 比如 "en","zh",等等
        //获取我们存储的语言环境 比如 "en","zh",等等
        String language = MyApplication.spUtils.getString(IConstant.LANGUAGE_TYPE, "zh");
        /**
         * attach对应语言环境下的context
         */
        super.attachBaseContext(LanguageUtil.attachBaseContext(newBase, language));
    }
}
