package com.skyvn.hw.view.bindbankcard;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.skyvn.hw.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  绑定银行卡
 */

public class BindBankCardActivity extends MVPBaseActivity<BindBankCardContract.View, BindBankCardPresenter>
        implements BindBankCardContract.View {

    @Override
    protected int getLayout() {
        return 0;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
