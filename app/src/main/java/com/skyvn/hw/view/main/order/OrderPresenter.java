package com.skyvn.hw.view.main.order;

import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.bean.OrderBO;
import com.skyvn.hw.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OrderPresenter extends BasePresenterImpl<OrderContract.View> implements OrderContract.Presenter {


    /**
     * 查询待确认订单
     */
    public void getMyConfirmLoan() {
        HttpServerImpl.getMyConfirmLoan().subscribe(new HttpResultSubscriber<OrderBO>() {
            @Override
            public void onSuccess(OrderBO s) {
                if (mView != null) {
                    mView.getOrder(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }


    /**
     * 查询已结束订单
     */
    public void getMyEndLoan() {
        HttpServerImpl.getMyEndLoan().subscribe(new HttpResultSubscriber<OrderBO>() {
            @Override
            public void onSuccess(OrderBO s) {
                if (mView != null) {
                    mView.getOrder(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }

    /**
     * 查询待还款订单
     */
    public void getMyRepayLoan() {
        HttpServerImpl.getMyRepayLoan().subscribe(new HttpResultSubscriber<OrderBO>() {
            @Override
            public void onSuccess(OrderBO s) {
                if (mView != null) {
                    mView.getOrder(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }

}
