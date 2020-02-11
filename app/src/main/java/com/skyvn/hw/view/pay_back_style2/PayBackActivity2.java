package com.skyvn.hw.view.pay_back_style2;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;

/**
 * 还款详情风格2
 */
public class PayBackActivity2 extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.act_pay_back2;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.huankuanxiangqing));
        rightButton();
    }
}
