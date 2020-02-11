package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.hw.widget.lgrecycleadapter.LGViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 系统消息界面
 */
public class MessageActivity extends BaseActivity {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

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
        setAdapter();
    }


    /**
     * 设置适配器
     */
    private void setAdapter(){
        List<String> lists = new ArrayList<>();
        lists.add("111");
        lists.add("111");
        lists.add("111");
        lists.add("111");
        LGRecycleViewAdapter<String> adapter = new LGRecycleViewAdapter<String>(lists) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_message;
            }

            @Override
            public void convert(LGViewHolder holder, String s, int position) {

            }
        };
        recycleView.setAdapter(adapter);
    }

}
