package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.bean.HuanKuanBO;
import com.skyvn.hw.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.hw.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * 还款流水界面
 */
public class HuanKuanListActivty extends BaseActivity {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private String orderId;

    private List<HuanKuanBO.DataBean> list;

    @Override
    protected int getLayout() {
        return R.layout.act_yihuan_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.huankuanjilu));
        rightButton();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        recycleView.setNestedScrollingEnabled(false);

        orderId = getIntent().getExtras().getString("id");
        getHuanKuanList();
    }


    /**
     * 查询还款流水
     */
    private void getHuanKuanList() {
        HttpServerImpl.getMyRepaySerial(orderId).subscribe(new HttpResultSubscriber<HuanKuanBO>() {
            @Override
            public void onSuccess(HuanKuanBO s) {
                list = s.getData();
                setAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        LGRecycleViewAdapter<HuanKuanBO.DataBean> adapter = new LGRecycleViewAdapter<HuanKuanBO.DataBean>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_huankuan;
            }

            @Override
            public void convert(LGViewHolder holder, HuanKuanBO.DataBean dataBean, int position) {
                holder.setText(R.id.update_price, dataBean.getRepayAmount() + getResources().getString(R.string.danwei_yuan));
                holder.setText(R.id.update_time, getResources().getString(R.string.huankuanshijian) + dataBean.getAuditTime());
            }
        };
        recycleView.setAdapter(adapter);
    }
}
