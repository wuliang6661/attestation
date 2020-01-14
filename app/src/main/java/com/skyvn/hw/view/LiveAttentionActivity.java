package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1411:04
 * desc   : 开始人脸扫描界面
 * version: 1.0
 */
public class LiveAttentionActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.act_live_attention;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.shuanianrenzheng));
        rightButton();
    }


}
