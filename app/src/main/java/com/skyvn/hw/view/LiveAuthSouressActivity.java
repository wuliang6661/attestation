package com.skyvn.hw.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;

import butterknife.BindView;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/159:52
 * desc   :  刷脸成功页面
 * version: 1.0
 */
public class LiveAuthSouressActivity extends BaseActivity {


    @BindView(R.id.down_time_text)
    TextView downTimeText;

    @Override
    protected int getLayout() {
        return R.layout.live_auth_souress;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.shuanianrenzheng));
        rightButton();
    }


    CountDownTimer timer = new CountDownTimer(1000, 3000) {
        @Override
        public void onTick(long l) {
            downTimeText.setText((l / 1000) + "");
        }

        @Override
        public void onFinish() {
            finish();
        }
    };
}
