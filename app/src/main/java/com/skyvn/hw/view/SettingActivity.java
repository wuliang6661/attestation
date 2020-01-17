package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.base.MyApplication;
import com.skyvn.hw.util.AppManager;
import com.skyvn.hw.widget.AlertDialog;
import com.skyvn.hw.widget.PopXingZhi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {


    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.lanunger_text)
    TextView lanungerText;

    @Override
    protected int getLayout() {
        return R.layout.act_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.setting));

        userPhone.setText(MyApplication.userBO.getPhone());
        Glide.with(this).load(MyApplication.userBO.getHeadPortrait())
                .error(R.drawable.user_img_defalt).
                placeholder(R.drawable.user_img_defalt).into(userImg);
    }


    @OnClick(R.id.switch_lanunger)
    public void switchLanunger() {
        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.china));
        list.add(getResources().getString(R.string.yuelanwen));
        PopXingZhi popXingZhi = new PopXingZhi(this, getResources().getString(R.string.switch_langnger), list);
        popXingZhi.setListener((position, item) -> {
//            statisticType = position;
            lanungerText.setText(item);
//            changeType(mChartType);
        });
//        popXingZhi.setSelectPosition(statisticType);
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }


    @OnClick(R.id.bt_logout)
    public void logout() {
        new AlertDialog(this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
                .setMsg(getResources().getString(R.string.is_logout))
                .setNegativeButton(getResources().getString(R.string.cancle), null)
                .setPositiveButton(getResources().getString(R.string.commit), v -> {
                    exitLogin();
                }).show();
    }

    /**
     * 退出登录
     */
    private void exitLogin() {
        showProgress();
        HttpServerImpl.exitLogin().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                AppManager.getAppManager().finishAllActivity();
                gotoActivity(LoginActivity.class, true);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }
}
