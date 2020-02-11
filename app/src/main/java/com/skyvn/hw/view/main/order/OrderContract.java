package com.skyvn.hw.view.main.order;

import com.skyvn.hw.bean.OrderBO;
import com.skyvn.hw.mvp.BasePresenter;
import com.skyvn.hw.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OrderContract {
    interface View extends BaseRequestView {

        void getOrder(OrderBO orderBO);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
