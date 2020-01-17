package com.skyvn.hw.view.bindbankcard;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.bean.BankBO;
import com.skyvn.hw.mvp.MVPBaseActivity;
import com.skyvn.hw.widget.PopXingZhi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 绑定银行卡
 */

public class BindBankCardActivity extends MVPBaseActivity<BindBankCardContract.View, BindBankCardPresenter>
        implements BindBankCardContract.View {

    @BindView(R.id.edit_user_name)
    EditText editUserName;
    @BindView(R.id.edit_yinghang_num)
    EditText editYinghangNum;
    @BindView(R.id.edit_yinghang_name)
    TextView editYinghangName;
    @BindView(R.id.yinghang_name_layout)
    LinearLayout yinghangNameLayout;
    @BindView(R.id.edit_suoshuzhihang)
    EditText editSuoshuzhihang;
    @BindView(R.id.bt_login)
    Button btLogin;

    private int selectPosition = 0;

    @Override
    protected int getLayout() {
        return R.layout.act_bind_bank_card;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.bangdingyinghangka));
        rightButton();

    }


    @OnClick(R.id.yinghang_name_layout)
    public void clickYinghang() {
        getBankNames();
    }


    /**
     * 获取所有银行名称
     */
    private void getBankNames() {
        showProgress();
        HttpServerImpl.getSysBanks().subscribe(new HttpResultSubscriber<List<BankBO>>() {
            @Override
            public void onSuccess(List<BankBO> s) {
                stopProgress();
                switchBanks(s);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private void switchBanks(List<BankBO> s) {
        List<String> list = new ArrayList<>();
        for (BankBO bankBO : s) {
            list.add(bankBO.getName());
        }
        PopXingZhi popXingZhi = new PopXingZhi(this, "", list);
        popXingZhi.setListener((position, item) -> {
            selectPosition = position;
            editYinghangName.setText(item);
        });
        popXingZhi.setSelectPosition(selectPosition);
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }

    /**
     * 提交
     */
    @OnClick(R.id.bt_login)
    public void commit() {
        String strName = editUserName.getText().toString().trim();
        if (StringUtils.isEmpty(strName)) {
            showToast(getResources().getString(R.string.user_name_toast));
            return;
        }
        String strBankNum = editYinghangNum.getText().toString().trim();
        if (StringUtils.isEmpty(strBankNum)) {
            showToast(getResources().getString(R.string.bank_num_toast));
            return;
        }
        String stryinhangName = editYinghangName.getText().toString().trim();
        if (StringUtils.isEmpty(stryinhangName)) {
            showToast(getResources().getString(R.string.yinghang_name_toast));
            return;
        }
        String suoshuzhihang = editSuoshuzhihang.getText().toString().trim();
        if (StringUtils.isEmpty(suoshuzhihang)) {
            showToast(getResources().getString(R.string.suoshuzhihang_toast));
            return;
        }
        HttpServerImpl.bindBankCard(stryinhangName, strBankNum, strName, suoshuzhihang)
                .subscribe(new HttpResultSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        showToast(getResources().getString(R.string.commit_sourss_toast));
                        finish();
                    }

                    @Override
                    public void onFiled(String message) {
                        showToast(message);
                    }
                });
    }

}
