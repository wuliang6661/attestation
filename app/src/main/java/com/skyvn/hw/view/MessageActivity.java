package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.bean.GongGaoBO;
import com.skyvn.hw.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.hw.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * 系统消息界面
 */
public class MessageActivity extends BaseActivity {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    List<GongGaoBO> list;

    @Override
    protected int getLayout() {
        return R.layout.act_message;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.xitong_message));

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        getNoticeList();
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        LGRecycleViewAdapter<GongGaoBO> adapter = new LGRecycleViewAdapter<GongGaoBO>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_message;
            }

            @Override
            public void convert(LGViewHolder holder, GongGaoBO s, int position) {
                holder.setText(R.id.msg_txt, s.getContent());
            }
        };
        recycleView.setAdapter(adapter);
    }


    /**
     * 获取公告列表
     */
    public void getNoticeList() {
        HttpServerImpl.getNoticeList().subscribe(new HttpResultSubscriber<List<GongGaoBO>>() {
            @Override
            public void onSuccess(List<GongGaoBO> s) {
                list = s;
                setAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}
