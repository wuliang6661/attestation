package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;

/**
 * 展期说明
 */
public class ZhanQiActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.act_zhanqi;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.zhanqi_title));
        rightButton();
    }
}
