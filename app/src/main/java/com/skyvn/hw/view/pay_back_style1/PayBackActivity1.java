package com.skyvn.hw.view.pay_back_style1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.view.ZhanQiActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 还款详情风格1
 */
public class PayBackActivity1 extends BaseActivity {

    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.yuqi_day)
    TextView yuqiDay;
    @BindView(R.id.pay_num)
    TextView payNum;
    @BindView(R.id.pay_back_time)
    TextView payBackTime;
    @BindView(R.id.zhanqi_layout)
    LinearLayout zhanqiLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @Override
    protected int getLayout() {
        return R.layout.act_pay_back1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.huankuanxiangqing));
        rightButton();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        recycleView.setNestedScrollingEnabled(false);
    }


    @OnClick(R.id.zhanqi_layout)
    public void clickZhanQi() {
        gotoActivity(ZhanQiActivity.class, false);
    }
}
