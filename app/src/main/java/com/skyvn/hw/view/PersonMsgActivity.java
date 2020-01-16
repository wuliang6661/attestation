package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1310:50
 * desc   : 个人资料页面
 * version: 1.0
 */
public class PersonMsgActivity extends BaseActivity {

    @BindView(R.id.edit_xueli)
    TextView editXueli;
    @BindView(R.id.xueli_layout)
    LinearLayout xueliLayout;
    @BindView(R.id.edit_hunyin)
    TextView editHunyin;
    @BindView(R.id.hunyin_layout)
    LinearLayout hunyinLayout;
    @BindView(R.id.edit_zinv_num)
    TextView editZinvNum;
    @BindView(R.id.zinv_num_layout)
    LinearLayout zinvNumLayout;
    @BindView(R.id.edit_juzhu_time)
    TextView editJuzhuTime;
    @BindView(R.id.juzhu_time_layout)
    LinearLayout juzhuTimeLayout;
    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.edit_zalo)
    EditText editZalo;
    @BindView(R.id.edit_facebook)
    EditText editFacebook;
    @BindView(R.id.bt_login)
    Button btLogin;

    @Override
    protected int getLayout() {
        return R.layout.act_person_msg;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.gerenziliao));
        rightButton();
    }

    @OnClick(R.id.xueli_layout)
    public void selectXueli() {

    }
}
