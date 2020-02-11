package com.skyvn.hw.view.borrow_style1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;

import butterknife.BindView;

/**
 * 借款详情风格1
 */
public class BorrowActivity1 extends BaseActivity {

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
    @BindView(R.id.borrow_time)
    TextView borrowTime;
    @BindView(R.id.borrow_amount)
    TextView borrowAmount;
    @BindView(R.id.lixi_amount)
    TextView lixiAmount;
    @BindView(R.id.fuwu_amount)
    TextView fuwuAmount;
    @BindView(R.id.yihuan_amount)
    TextView yihuanAmount;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @Override
    protected int getLayout() {
        return R.layout.act_borrow_style1;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.jiekuanxiangqing));
        rightButton();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        recycleView.setNestedScrollingEnabled(false);
    }
}
