package com.skyvn.hw.view.main.order;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.skyvn.hw.R;
import com.skyvn.hw.mvp.MVPBaseFragment;
import com.skyvn.hw.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.hw.widget.lgrecycleadapter.LGViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OrderFragment extends MVPBaseFragment<OrderContract.View, OrderPresenter>
        implements OrderContract.View {


    @BindView(R.id.bt_daiqueren)
    TextView btDaiqueren;
    @BindView(R.id.bt_daihuankuan)
    TextView btDaihuankuan;
    @BindView(R.id.bt_yijieshu)
    TextView btYijieshu;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;
    @BindView(R.id.animotion_view)
    View animotionView;

    private int viewInType = 0; // view 在第几个选项

    private TextView[] titles;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_order, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        ViewGroup.LayoutParams params = animotionView.getLayoutParams();
        params.width = ScreenUtils.getScreenWidth() / 3;
        animotionView.setLayoutParams(params);

        titles = new TextView[]{btDaiqueren, btDaihuankuan, btYijieshu};
        setAdapter();
    }


    @OnClick({R.id.bt_daiqueren, R.id.bt_daihuankuan, R.id.bt_yijieshu})
    public void clickTitle(View view) {
        switch (view.getId()) {
            case R.id.bt_daiqueren:   //待确认
                if (viewInType == 0) {
                    return;
                }
                animationStart(0);
                setTitles(0);
                viewInType = 0;
                break;
            case R.id.bt_daihuankuan:   //待还款
                if (viewInType == 1) {
                    return;
                }
                animationStart(1);
                setTitles(1);
                viewInType = 1;
                break;
            case R.id.bt_yijieshu:   //已结束
                if (viewInType == 2) {
                    return;
                }
                animationStart(2);
                setTitles(2);
                viewInType = 2;
                break;
        }
    }

    /**
     * 设置title字体颜色
     */
    private void setTitles(int position) {
        for (int i = 0; i < titles.length; i++) {
            if (i == position) {
                titles[i].setTextColor(Color.parseColor("#3296FA"));
            } else {
                titles[i].setTextColor(Color.parseColor("#A3A5A8"));
            }
        }
    }


    private void animationStart(int position) {
        Animation animation = new TranslateAnimation(ScreenUtils.getScreenWidth() / 3 * viewInType, ScreenUtils.getScreenWidth() / 3 * position, 0, 0);
        animation.setDuration(300);
        animation.setRepeatCount(0);//动画的反复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        animotionView.startAnimation(animation);//開始动画
    }


    /**
     * 设置数据适配器
     */
    private void setAdapter() {
        List<String> ss = new ArrayList<>();
        ss.add("1");
        ss.add("1");
        ss.add("1");
        ss.add("1");
        ss.add("1");
        ss.add("1");
        ss.add("1");
        ss.add("1");
        LGRecycleViewAdapter<String> adapter = new LGRecycleViewAdapter<String>(ss) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_order;
            }

            @Override
            public void convert(LGViewHolder holder, String s, int position) {

            }
        };
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
