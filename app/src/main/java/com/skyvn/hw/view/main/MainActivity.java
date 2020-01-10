package com.skyvn.hw.view.main;

import android.os.Bundle;
import android.view.KeyEvent;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.util.AppManager;
import com.xyz.tabitem.BottmTabItem;

import butterknife.BindView;
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
    private SupportFragment[] mFragments = new SupportFragment[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
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
