package com.skyvn.hw.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.blankj.utilcode.util.StringUtils;
import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.base.MyApplication;
import com.skyvn.hw.bean.LoginSuressBO;
import com.skyvn.hw.view.main.MainActivity;


/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/4/19:25
 * desc   : 引导页
 * version: 1.0
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.act_splash;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 2000);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String token = MyApplication.spUtils.getString("token");
        if (!StringUtils.isEmpty(token)) {
            MyApplication.token = token;
            getUserInfo();
        } else {
            gotoActivity(MainActivity.class, true);
        }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        HttpServerImpl.getUserInfo().subscribe(new HttpResultSubscriber<LoginSuressBO>() {
            @Override
            public void onSuccess(LoginSuressBO s) {
                MyApplication.userBO = s;
                //清空任务栈确保当前打开activity为前台任务栈栈顶
                Intent it = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            }

            @Override
            public void onFiled(String message) {
                MyApplication.spUtils.remove("token");
                gotoActivity(MainActivity.class, true);
            }
        });
    }


}
