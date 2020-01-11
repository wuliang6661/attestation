package com.skyvn.hw.view.attentionziliao;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvn.hw.R;
import com.skyvn.hw.mvp.MVPBaseActivity;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AttentionZiliaoActivity extends MVPBaseActivity<AttentionZiliaoContract.View, AttentionZiliaoPresenter> implements AttentionZiliaoContract.View {

    @BindView(R.id.shiming_type)
    TextView shimingType;
    @BindView(R.id.shiming_layout)
    LinearLayout shimingLayout;
    @BindView(R.id.ziliao_type)
    TextView ziliaoType;
    @BindView(R.id.ziliao_layout)
    LinearLayout ziliaoLayout;
    @BindView(R.id.shipin_type)
    TextView shipinType;
    @BindView(R.id.shipin_layout)
    LinearLayout shipinLayout;
    @BindView(R.id.shualian_type)
    TextView shualianType;
    @BindView(R.id.shuanian_layout)
    LinearLayout shuanianLayout;
    @BindView(R.id.lianxiren_type)
    TextView lianxirenType;
    @BindView(R.id.lianxiren_layout)
    LinearLayout lianxirenLayout;
    @BindView(R.id.gongsi_type)
    TextView gongsiType;
    @BindView(R.id.gongsi_layout)
    LinearLayout gongsiLayout;
    @BindView(R.id.yinghangka_type)
    TextView yinghangkaType;
    @BindView(R.id.yinghangka_layout)
    LinearLayout yinghangkaLayout;
    @BindView(R.id.duanxin_type)
    TextView duanxinType;
    @BindView(R.id.duanxin_layout)
    LinearLayout duanxinLayout;
    @BindView(R.id.jiaozhao_type)
    TextView jiaozhaoType;
    @BindView(R.id.jiazhao_layout)
    LinearLayout jiazhaoLayout;

    @Override
    protected int getLayout() {
        return R.layout.act_attention_ziliao;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.renzhengziliao));
        rightButton();

    }
}
