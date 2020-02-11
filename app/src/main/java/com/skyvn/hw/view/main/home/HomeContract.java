package com.skyvn.hw.view.main.home;

import com.skyvn.hw.mvp.BasePresenter;
import com.skyvn.hw.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HomeContract {
    interface View extends BaseRequestView {

        void getBanner();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
