package com.skyvn.hw.view.main;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.base.MyApplication;
import com.skyvn.hw.bean.GoHomeEvent;
import com.skyvn.hw.bean.LiveKeyBO;
import com.skyvn.hw.util.AppManager;
import com.skyvn.hw.util.UpdateUtils;
import com.skyvn.hw.view.LoginActivity;
import com.skyvn.hw.view.main.none.NoneFragment1;
import com.skyvn.hw.view.main.none.NoneFragment2;
import com.skyvn.hw.view.main.none.NoneFragment3;
import com.xyz.tabitem.BottmTabItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ai.advance.liveness.lib.GuardianLivenessDetectionSDK;
import ai.advance.liveness.lib.Market;
import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {


    @BindView(R.id.main1)
    BottmTabItem main1;
    @BindView(R.id.main2)
    BottmTabItem main2;
    @BindView(R.id.main3)
    BottmTabItem main3;


    private int selectPosition = 0;
    private BottmTabItem[] buttms;
    private SupportFragment[] mFragments = new SupportFragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        buttms = new BottmTabItem[]{main1, main2, main3};
        initFragment();
        getSaasKey();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 获取活体检测的key
     */
    private void getSaasKey() {
        HttpServerImpl.getSaaSActiveKey().subscribe(new HttpResultSubscriber<LiveKeyBO>() {
            @Override
            public void onSuccess(LiveKeyBO s) {
                if (s == null || StringUtils.isEmpty(s.getSdkKey()) || StringUtils.isEmpty(s.getSecretKey())) {
                    showToast(getString(R.string.huotiqueshi));
                    return;
                }
                MyApplication.LIVE_KEY = s.getSdkKey();
                MyApplication.Secret_Key = s.getSecretKey();
                GuardianLivenessDetectionSDK.init(getApplication(), MyApplication.LIVE_KEY, MyApplication.Secret_Key,
                        Market.Vietnam);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new UpdateUtils().checkUpdate(this, null);
    }

    @OnClick({R.id.main1, R.id.main2, R.id.main3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main1:
                showHideFragment(mFragments[0], mFragments[selectPosition]);
                selectPosition = 0;
                setButtom(0);
                break;
            case R.id.main2:
                if (MyApplication.token == null) {
                    gotoActivity(LoginActivity.class, false);
                    return;
                }
                showHideFragment(mFragments[1], mFragments[selectPosition]);
                selectPosition = 1;
                setButtom(1);
                break;
            case R.id.main3:
                if (MyApplication.token == null) {
                    gotoActivity(LoginActivity.class, false);
                    return;
                }
                showHideFragment(mFragments[2], mFragments[selectPosition]);
                selectPosition = 2;
                setButtom(2);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GoHomeEvent event) {
        showHideFragment(mFragments[0], mFragments[selectPosition]);
        selectPosition = 0;
        setButtom(0);
    }


    /**
     * 初始化fragment
     */
    private void initFragment() {
        SupportFragment firstFragment = findFragment(NoneFragment1.class);
        if (firstFragment == null) {
            mFragments[0] = NoneFragment1.newInstance();
            mFragments[1] = new NoneFragment2();
            mFragments[2] = new NoneFragment3();

            loadMultipleRootFragment(R.id.fragment_container, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(NoneFragment2.class);
            mFragments[2] = findFragment(NoneFragment3.class);
        }
    }


    /**
     * 设置底部按钮显示
     */
    private void setButtom(int position) {
        for (int i = 0; i < buttms.length; i++) {
            if (position == i) {
                buttms[i].setSelectState(true);
            } else {
                buttms[i].setSelectState(false);
            }
        }
    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showToast("再按一次退出程序");
                    firstTime = secondTime;
                    return true;
                } else {
                    AppManager.getAppManager().finishAllActivity();
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


}
