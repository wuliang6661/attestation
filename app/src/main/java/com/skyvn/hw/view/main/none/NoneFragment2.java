package com.skyvn.hw.view.main.none;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.blankj.utilcode.util.StringUtils;
import com.skyvn.hw.R;
import com.skyvn.hw.base.MyApplication;
import com.skyvn.hw.view.main.order.OrderFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by dell on 2018/12/29.
 */

public class NoneFragment2 extends SupportFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_load, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(OrderFragment.class) == null) {
            loadRootFragment(R.id.fl_first_container, new OrderFragment());
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


    }
}
