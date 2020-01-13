package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1310:50
 * desc   : 个人资料页面
 * version: 1.0
 */
public class PersonMsgActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.act_person_msg;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.gerenziliao));
        rightButton();
    }
}
