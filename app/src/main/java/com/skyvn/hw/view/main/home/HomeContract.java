package com.skyvn.hw.view.main.home;

import com.skyvn.hw.bean.BannerBO;
import com.skyvn.hw.bean.GongGaoBO;
import com.skyvn.hw.bean.StatusBO;
import com.skyvn.hw.mvp.BasePresenter;
import com.skyvn.hw.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HomeContract {
    interface View extends BaseRequestView {

        void getBanner(BannerBO bannerBO);

        void getGongGao(List<GongGaoBO> gongGaoBOS);

        void getLunBoImgs(List<BannerBO> bannerBOS);

        void getStatus(StatusBO statusBO);

        void getPayNumOrDays(List<String> list, int type);

        void updateGpsSource();

        void updateGpsError();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
