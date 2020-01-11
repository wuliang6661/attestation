package com.skyvn.hw.view.main.mine;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.hw.R;
import com.skyvn.hw.mvp.MVPBaseFragment;
import com.skyvn.hw.view.FanKuiActivity;
import com.skyvn.hw.view.KefuActivity;
import com.skyvn.hw.view.MyBankCardActivity;
import com.skyvn.hw.view.SettingActivity;
import com.skyvn.hw.view.attentionziliao.AttentionZiliaoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MineFragment extends MVPBaseFragment<MineContract.View, MinePresenter>
        implements MineContract.View {


    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.go_attention)
    TextView goAttention;
    @BindView(R.id.user_phone)
    TextView userPhone;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_mine, null);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @OnClick({R.id.layout_yinghangka, R.id.layout_ziliao, R.id.layout_kefu, R.id.layout_fankui})
    public void layoutClick(View view) {
        switch (view.getId()) {
            case R.id.layout_yinghangka:   //银行卡
                gotoActivity(MyBankCardActivity.class, false);
                break;
            case R.id.layout_ziliao:    //资料补充
                gotoActivity(AttentionZiliaoActivity.class, false);
                break;
            case R.id.layout_kefu:    //客服
                gotoActivity(KefuActivity.class, false);
                break;
            case R.id.layout_fankui:    //反馈
                gotoActivity(FanKuiActivity.class, false);
                break;
        }
    }


    @OnClick(R.id.btn_album)
    public void settingClick() {
        gotoActivity(SettingActivity.class, false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
