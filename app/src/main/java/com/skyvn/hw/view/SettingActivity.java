package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.util.AppManager;
import com.skyvn.hw.util.PhoneUtils;
import com.skyvn.hw.widget.AlertDialog;
import com.skyvn.hw.widget.PopXingZhi;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.act_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.setting));
    }


    @OnClick(R.id.switch_lanunger)
    public void switchLanunger() {
        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.china));
        list.add(getResources().getString(R.string.yuelanwen));
        PopXingZhi popXingZhi = new PopXingZhi(this, getResources().getString(R.string.switch_langnger), list);
        popXingZhi.setListener((position, item) -> {
//            statisticType = position;
//            statisticSelectText.setText(item);
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
                    AppManager.getAppManager().finishAllActivity();
                    gotoActivity(LoginActivity.class, true);
                }).show();
    }
}
