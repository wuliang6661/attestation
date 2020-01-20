package com.skyvn.hw.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.bean.AttentionSourrssBO;
import com.skyvn.hw.util.AuthenticationUtils;

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

    private String base64;

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

        base64 = getIntent().getExtras().getString("base64");
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    CountDownTimer timer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long l) {
            downTimeText.setText((l / 1000) + "");
        }

        @Override
        public void onFinish() {
            updateLiveAuth();
        }
    };


    private void updateLiveAuth() {
        showProgress();
        HttpServerImpl.addClientActiveAuth(base64).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                showToast(getResources().getString(R.string.commit_sourss_toast));
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), LiveAuthSouressActivity.this);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }
}
