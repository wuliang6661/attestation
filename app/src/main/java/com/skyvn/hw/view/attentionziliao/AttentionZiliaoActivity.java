package com.skyvn.hw.view.attentionziliao;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.bean.AuthTypeBO;
import com.skyvn.hw.config.IConstant;
import com.skyvn.hw.mvp.MVPBaseActivity;
import com.skyvn.hw.view.CommonMsgActivity;
import com.skyvn.hw.view.JiaZhaoActivity;
import com.skyvn.hw.view.LiveAttentionActivity;
import com.skyvn.hw.view.Msg14Activity;
import com.skyvn.hw.view.VideoActivity;
import com.skyvn.hw.view.bindbankcard.BindBankCardActivity;
import com.skyvn.hw.view.emergencycontact.EmergencyContactActivity;
import com.skyvn.hw.view.person_msg_style.PersonMsgActivity;
import com.skyvn.hw.view.person_msg_style.PersonMsgActivity2;
import com.skyvn.hw.view.shiming_style.ShiMingActivity;
import com.skyvn.hw.view.shiming_style.ShiMingActivity2;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AttentionZiliaoActivity extends MVPBaseActivity<AttentionZiliaoContract.View,
        AttentionZiliaoPresenter> implements AttentionZiliaoContract.View {

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
    @BindView(R.id.layout1_text)
    TextView layout1Text;
    @BindView(R.id.layout2_text)
    TextView layout2Text;

    private List<AuthTypeBO> typeBOS;

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

        if (IConstant.STYLE == 1) {  //风格1
            layout1Text.setText(getResources().getString(R.string.shimingrenzheng));
            layout2Text.setText(getResources().getString(R.string.gerenziliao));
        } else {
            layout1Text.setText(getResources().getString(R.string.gerenziliao));
            layout2Text.setText(getResources().getString(R.string.shenfenzhengyanzheng));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAuthList();
    }

    /**
     * 获取所有认证项的认证状态
     */
    private void getAuthList() {
        HttpServerImpl.getAuthList().subscribe(new HttpResultSubscriber<List<AuthTypeBO>>() {
            @Override
            public void onSuccess(List<AuthTypeBO> typeBOS) {
                AttentionZiliaoActivity.this.typeBOS = typeBOS;
                showType();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    /**
     * 设置界面状态显示
     */
    private void showType() {
        for (AuthTypeBO typeBO : typeBOS) {
            switch (typeBO.getCode()) {
                case "0":   //个人资料
                    setTextShow(typeBO.getNeedStatus(), typeBO.getStatus(), ziliaoType, ziliaoLayout);
                    break;
                case "1":  //身份证验证
                    setTextShow(typeBO.getNeedStatus(), typeBO.getStatus(), shimingType, shimingLayout);
                    break;
                case "2":  // 活体验证
                    setTextShow(typeBO.getNeedStatus(), typeBO.getStatus(), shualianType, shuanianLayout);
                    break;
                case "3":  // 紧急联系人验证
                    setTextShow(typeBO.getNeedStatus(), typeBO.getStatus(), lianxirenType, lianxirenLayout);
                    break;
                case "4":  // 驾照验证
                    setTextShow(typeBO.getNeedStatus(), typeBO.getStatus(), jiaozhaoType, jiazhaoLayout);
                    break;
                case "5": // 运营商验证

                    break;
                case "6":  // 通讯录验证

                    break;
                case "7":  // 绑定银行卡验证
                    setTextShow(typeBO.getNeedStatus(), typeBO.getStatus(), yinghangkaType, yinghangkaLayout);
                    break;
                case "8":  //短信记录验证

                    break;
                case "9":   //1414短信验证
                    setTextShow(typeBO.getNeedStatus(), typeBO.getStatus(), duanxinType, duanxinLayout);
                    break;
                case "10":  //手持身份证小视频
                    setTextShow(typeBO.getNeedStatus(), typeBO.getStatus(), shipinType, shipinLayout);
                    break;
                case "11":  // 公司资料验证
                    setTextShow(typeBO.getNeedStatus(), typeBO.getStatus(), gongsiType, gongsiLayout);
                    break;
            }
        }
    }

    private void setTextShow(int needStatus, int status, TextView showMsg, LinearLayout clickLayout) {
        if (needStatus == 0) {   //不必填
            if (status == 0) {   //未完成
                showMsg.setTextColor(Color.parseColor("#0077EA"));
                showMsg.setText(getResources().getString(R.string.weiwancheng));
                clickLayout.setEnabled(true);
            } else {
                showMsg.setTextColor(Color.parseColor("#666666"));
                showMsg.setText(getResources().getString(R.string.yiwancheng));
//                clickLayout.setEnabled(false);
            }
        } else {
            if (status == 0) {   //未完成
                showMsg.setTextColor(Color.parseColor("#FF6860"));
                showMsg.setText(getResources().getString(R.string.weiwancheng));
                clickLayout.setEnabled(true);
            } else {
                showMsg.setTextColor(Color.parseColor("#666666"));
                showMsg.setText(getResources().getString(R.string.yiwancheng));
//                clickLayout.setEnabled(false);
            }
        }
    }


    @OnClick(R.id.yinghangka_layout)
    public void clickYinghangka() {
        gotoActivity(BindBankCardActivity.class, false);
    }

    @OnClick(R.id.lianxiren_layout)
    public void clickLianxiren() {
        gotoActivity(EmergencyContactActivity.class, false);
    }

    @OnClick(R.id.ziliao_layout)
    public void clickZiliao() {
        if (IConstant.STYLE == 1) {
            gotoActivity(ShiMingActivity.class, false);
        } else {
            gotoActivity(ShiMingActivity2.class, false);
        }
    }

    @OnClick(R.id.gongsi_layout)
    public void clickGongsi() {
        gotoActivity(CommonMsgActivity.class, false);
    }

    @OnClick(R.id.duanxin_layout)
    public void clickDuanxin() {
        gotoActivity(Msg14Activity.class, false);
    }

    @OnClick(R.id.jiazhao_layout)
    public void clickJiazhao() {
        gotoActivity(JiaZhaoActivity.class, false);
    }

    @OnClick(R.id.shipin_layout)
    public void clickShiPin() {
        gotoActivity(VideoActivity.class, false);
    }

    @OnClick(R.id.shiming_layout)
    public void clickShiming() {
        if (IConstant.STYLE == 1) {
            gotoActivity(PersonMsgActivity.class, false);
        } else {
            gotoActivity(PersonMsgActivity2.class, false);
        }
    }


    @OnClick(R.id.shuanian_layout)
    public void clickShuaLian() {
        gotoActivity(LiveAttentionActivity.class, false);
    }
}
