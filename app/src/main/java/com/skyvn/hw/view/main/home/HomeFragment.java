package com.skyvn.hw.view.main.home;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.base.MyApplication;
import com.skyvn.hw.bean.AttentionSourrssBO;
import com.skyvn.hw.bean.AuthStatusBO;
import com.skyvn.hw.bean.BannerBO;
import com.skyvn.hw.bean.GongGaoBO;
import com.skyvn.hw.bean.StatusBO;
import com.skyvn.hw.mvp.MVPBaseFragment;
import com.skyvn.hw.util.AuthenticationUtils;
import com.skyvn.hw.util.GPSUtils;
import com.skyvn.hw.view.KefuActivity;
import com.skyvn.hw.view.LoginActivity;
import com.skyvn.hw.view.MessageActivity;
import com.skyvn.hw.view.SettingActivity;
import com.skyvn.hw.view.WebActivity;
import com.skyvn.hw.view.attentionziliao.AttentionZiliaoActivity;
import com.skyvn.hw.widget.AlertDialog;
import com.skyvn.hw.widget.PopXingZhi;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

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

public class HomeFragment extends MVPBaseFragment<HomeContract.View, HomePresenter>
        implements HomeContract.View {

    @BindView(R.id.home_banner_img)
    ImageView homeBannerImg;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.gonggao_text)
    TextView gonggaoText;
    @BindView(R.id.bt_login)
    Button btLogin;
    Unbinder unbinder;
    @BindView(R.id.gonggao_layout)
    LinearLayout gonggaoLayout;
    @BindView(R.id.pay_num)
    TextView payNum;
    @BindView(R.id.pay_num_layout)
    LinearLayout payNumLayout;
    @BindView(R.id.days_text)
    TextView daysText;
    @BindView(R.id.days_layout)
    LinearLayout daysLayout;
    @BindView(R.id.status_all_layout)
    LinearLayout statusAllLayout;
    @BindView(R.id.status_hint_img)
    ImageView statusHintImg;
    @BindView(R.id.status_hint1)
    TextView statusHint1;
    @BindView(R.id.status_hint2)
    TextView statusHint2;
    @BindView(R.id.status_hint3)
    TextView statusHint3;
    @BindView(R.id.status_layout)
    LinearLayout statusLayout;


    BannerBO bannerBO;
    List<BannerBO> bannerBOS;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.getHomeBanner();
        mPresenter.getHomeCarses();
    }


    @OnClick(R.id.btn_album)
    public void right() {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
//        gotoActivity(ConfirmationActivity.class,false);
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getNoticeList();
        if (MyApplication.token == null) {
            statusAllLayout.setVisibility(View.VISIBLE);
            statusLayout.setVisibility(View.GONE);
            mPresenter.getPayNumAndDays("saas.app.la", 3);
            mPresenter.getPayNumAndDays("saas.app.ld", 4);
        } else {
            mPresenter.getMyApply();
        }
    }

    private void setBanner() {
        List<String> images = new ArrayList<>();
        if (bannerBOS == null) {
            images.add("");
        } else {
            for (BannerBO bannerBO : bannerBOS) {
                images.add(bannerBO.getImageUrl());
            }
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(position -> {
            if (bannerBOS == null || StringUtils.isEmpty(bannerBOS.get(position).getForwardUrl())) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("url", bannerBOS.get(position).getForwardUrl());
            bundle.putString("title", getString(R.string.guanggao));
            gotoActivity(WebActivity.class, bundle, false);
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    @OnClick(R.id.gonggao_layout)
    public void clickGonggao() {
        gotoActivity(MessageActivity.class, false);
    }


    @OnClick({R.id.pay_num_layout, R.id.days_layout})
    public void clickLayout(View view) {
        switch (view.getId()) {
            case R.id.pay_num_layout:
                mPresenter.getPayNumAndDays("saas.app.la", 0);
                break;
            case R.id.days_layout:
                mPresenter.getPayNumAndDays("saas.app.ld", 1);
                break;
        }
    }

    @OnClick(R.id.kefu_layout)
    public void clickKefu() {
//        if (MyApplication.token == null) {
//            gotoActivity(LoginActivity.class, false);
//            return;
//        }
        gotoActivity(KefuActivity.class, false);
    }


    @OnClick(R.id.bt_login)
    public void clickAddOrder() {
        if (MyApplication.token == null) {
            gotoActivity(LoginActivity.class, false);
        } else {
            getMyAuthStatus();
        }
    }


    public void clickGoAttention() {
        showProgress();
        HttpServerImpl.getFirstAuth().subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                stopProgress();
                if (!"-1".equals(s.getCode())) {
                    gotoActivity(AttentionZiliaoActivity.class, false);
                }
                AuthenticationUtils.goAuthNextPageByHome(s.getCode(), s.getNeedStatus(), false, getActivity());
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }

    private void getMyAuthStatus() {
        HttpServerImpl.getMyAuthStatus().subscribe(new HttpResultSubscriber<AuthStatusBO>() {
            @Override
            public void onSuccess(AuthStatusBO s) {
                if ("2".equals(s.getStatus())) {  //认证通过
                    requestPermission();
                } else {
                    new AlertDialog(getActivity()).builder().setGone().setTitle(getResources().getString(R.string.tishi))
                            .setMsg(getString(R.string.renzheng_hint))
                            .setNegativeButton(getResources().getString(R.string.cancle), null)
                            .setPositiveButton(getResources().getString(R.string.qurenzheng), v ->
                                    clickGoAttention()
                            ).show();
                }
//                requestPermission();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 1);

        } else {
            checkPermissions();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                    // 执形我们想要的操作
                    checkPermissions();
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            || !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        //提示用户前往设置界面自己打开权限
//                        Toast.makeText(this, "请前往设置界面打开权限", Toast.LENGTH_SHORT).show();
                        showToast(getString(R.string.qingdakaigps));
                        return;
                    }

                }
            }
        }
    }


    private double loginLatitude;
    private double loginLongitude;

    /**
     * Detect camera authorization
     */
    public void checkPermissions() {
        showProgress();
        timer.start();
        GPSUtils.getInstance(getActivity().getApplicationContext()).getLngAndLat(new GPSUtils.OnLocationResultListener() {
            @Override
            public void onLocationResult(Location location) {
                loginLatitude = location.getLatitude();
                loginLongitude = location.getLongitude();
                timer.cancel();
                GPSUtils.getInstance(getActivity().getApplicationContext()).removeListener();
                mPresenter.updateLocation(loginLatitude + "", loginLongitude + "");
                GPSUtils.getInstance(getActivity().getApplicationContext()).removeListener();
                LogUtils.e("loginLatitude == " + loginLatitude + "   loginLongitude ==  " + loginLongitude);
            }

            @Override
            public void OnLocationChange(Location location) {
                loginLatitude = location.getLatitude();
                loginLongitude = location.getLongitude();
                timer.cancel();
                GPSUtils.getInstance(getActivity().getApplicationContext()).removeListener();
                mPresenter.updateLocation(loginLatitude + "", loginLongitude + "");
                GPSUtils.getInstance(getActivity().getApplicationContext()).removeListener();
                LogUtils.e("loginLatitude == " + loginLatitude + "   loginLongitude ==  " + loginLongitude);
            }

            @Override
            public void OnLocationError() {
                timer.cancel();
                GPSUtils.getInstance(getActivity().getApplicationContext()).removeListener();
                stopProgress();
            }
        });
    }


    CountDownTimer timer = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            stopProgress();
            GPSUtils.getInstance(getActivity().getApplicationContext()).removeListener();
            showToast(getString(R.string.gpshuoqushibai));
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (timer != null) {
            timer.cancel();
        }
        GPSUtils.getInstance(getActivity().getApplicationContext()).removeListener();
    }


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getBanner(BannerBO bannerBO) {
        this.bannerBO = bannerBO;
        if (bannerBO == null) {
            Glide.with(getActivity()).load("").error(R.drawable.defalt_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.defalt_img).into(homeBannerImg);
        } else {
            Glide.with(getActivity()).load(bannerBO.getImageUrl()).error(R.drawable.defalt_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.defalt_img).into(homeBannerImg);
        }
    }


    @OnClick(R.id.home_banner_img)
    public void clickBanner() {
        if (bannerBO == null || StringUtils.isEmpty(bannerBO.getForwardUrl())) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("url", bannerBO.getForwardUrl());
        bundle.putString("title", getString(R.string.guanggao));
        gotoActivity(WebActivity.class, bundle, false);
    }


    @Override
    public void getGongGao(List<GongGaoBO> gongGaoBOS) {
        if (gongGaoBOS == null || gongGaoBOS.isEmpty()) {
            gonggaoLayout.setVisibility(View.GONE);
            return;
        }
        gonggaoLayout.setVisibility(View.VISIBLE);
        StringBuilder builder = new StringBuilder();
        for (GongGaoBO gongGaoBO : gongGaoBOS) {
            builder.append(gongGaoBO.getContent());
            builder.append("             ");
        }
        gonggaoText.setText(builder.toString());
    }

    @Override
    public void getLunBoImgs(List<BannerBO> bannerBOS) {
        this.bannerBOS = bannerBOS;
        setBanner();
    }

    @Override
    public void getStatus(StatusBO statusBO) {
        stopProgress();
        switch (statusBO.getStatus()) {  // 0-可申请，1-审核中，2-审核完成
            case 0:
                statusAllLayout.setVisibility(View.VISIBLE);
                statusLayout.setVisibility(View.GONE);
                mPresenter.getPayNumAndDays("saas.app.la", 3);
                mPresenter.getPayNumAndDays("saas.app.ld", 4);
                break;
            case 1:
                statusAllLayout.setVisibility(View.GONE);
                statusLayout.setVisibility(View.VISIBLE);
                statusHintImg.setImageResource(R.drawable.status0_img);
                statusHint1.setText(getResources().getString(R.string.shenqingyitijiao));
                statusHint2.setText(getResources().getString(R.string.womenzhengzaishenhe));
                statusHint3.setText(getResources().getString(R.string.youjieguonaixindengdai));
                break;
            case 2:
                statusAllLayout.setVisibility(View.GONE);
                statusLayout.setVisibility(View.VISIBLE);
                statusHintImg.setImageResource(R.drawable.status1_img);
                statusHint1.setText(getResources().getString(R.string.yitijiaoshenqing));
                statusHint2.setText(getResources().getString(R.string.mingtianzailai));
                statusHint3.setText(getResources().getString(R.string.mingtianjingxi));
                break;
        }
    }

    @Override
    public void getPayNumOrDays(List<String> list, int type) {
        if (list.isEmpty()) {
            return;
        }
        if (type == 3) {
            payNum.setText(list.get(0));
            return;
        } else if (type == 4) {
            daysText.setText(list.get(0));
            return;
        }
        PopXingZhi popXingZhi = new PopXingZhi(getActivity(), "", list);
        popXingZhi.setListener((position, item) -> {
            switch (type) {
                case 0:
                    payNum.setText(item);
                    break;
                case 1:
                    daysText.setText(item);
                    break;
            }
        });
        popXingZhi.showAtLocation(getActivity().getWindow().getDecorView());
    }

    @Override
    public void updateGpsSource() {
        String amounts = payNum.getText().toString().trim();
        if (StringUtils.isEmpty(amounts)) {
            return;
        }
        String days = daysText.getText().toString().trim();
        if (StringUtils.isEmpty(days)) {
            return;
        }
        String[] paynums = amounts.split("~");
        mPresenter.addMyApply(days, paynums[1], paynums[0]);
    }

    @Override
    public void updateGpsError() {
        stopProgress();
        showToast(getString(R.string.gpsshangchuanshibai));
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Glide 加载图片简单用法
            Glide.with(context).load(path).error(R.drawable.defalt_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.defalt_img).into(imageView);
        }

//        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
//        @Override
//        public ImageView createImageView(Context context) {
//            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//            return simpleDraweeView;
//        }
    }

}
