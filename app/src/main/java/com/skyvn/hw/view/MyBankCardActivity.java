package com.skyvn.hw.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的银行卡页面
 */
public class MyBankCardActivity extends BaseActivity {

    @BindView(R.id.yinghang_img)
    RoundedImageView yinghangImg;
    @BindView(R.id.yinghang_name)
    TextView yinghangName;
    @BindView(R.id.yinghang_num)
    TextView yinghangNum;
    @BindView(R.id.card_layout)
    RelativeLayout cardLayout;
    @BindView(R.id.no_card_layout)
    LinearLayout noCardLayout;
    @BindView(R.id.bt_img)
    ImageView btImg;
    @BindView(R.id.bt_name)
    TextView btName;
    @BindView(R.id.btn_layout)
    LinearLayout btnLayout;

    private String strBankCardNum;   //我的银行卡号


    @Override
    protected int getLayout() {
        return R.layout.act_bank_card;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.yinghangka));
        rightButton();

        getBankCard();
    }


    /**
     * 获取我的银行卡
     */
    private void getBankCard() {
        HttpServerImpl.getBankCard().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    @OnClick(R.id.btn_layout)
    public void clickBankCard() {

    }

}
