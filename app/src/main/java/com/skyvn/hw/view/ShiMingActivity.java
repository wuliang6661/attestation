package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1315:48
 * desc   : 实名认证
 * version: 1.0
 */
public class ShiMingActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.shiming_layout;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.shimingrenzheng));
        rightButton();
    }
}
