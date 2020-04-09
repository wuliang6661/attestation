package com.skyvn.hw.view.main.mine;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.base.MyApplication;
import com.skyvn.hw.bean.AttentionSourrssBO;
import com.skyvn.hw.mvp.MVPBaseFragment;
import com.skyvn.hw.util.AuthenticationUtils;
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

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initAttention();
        userPhone.setText(MyApplication.userBO.getPhone());
        Glide.with(getActivity()).load(MyApplication.userBO.getHeadPortrait())
                .error(R.drawable.person_defalt).
                placeholder(R.drawable.person_defalt).into(userImg);
    }


    private void initAttention() {
        HttpServerImpl.getFirstAuth().subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                if ("-1".equals(s.getCode())) {
                    goAttention.setText(R.string.yirenzheng);
                } else {
                    goAttention.setText(R.string.qurenzheng);
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
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


    @OnClick(R.id.go_attention)
    public void clickGoAttention() {
        showProgress();
        HttpServerImpl.getFirstAuth().subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                stopProgress();
                AuthenticationUtils.goAuthNextPageByHome(s.getCode(), s.getNeedStatus(), false, getActivity());
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
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
