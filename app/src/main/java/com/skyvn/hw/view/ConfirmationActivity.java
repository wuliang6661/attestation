package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;

/***
 * 立即提现界面
 */
public class ConfirmationActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.act_comfirmation;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.querenjiekuan));
        rightButton();

    }
}
