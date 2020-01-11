package com.skyvn.hw.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.util.PhoneUtils;
import com.skyvn.hw.widget.AlertDialog;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 客服界面
 */
public class KefuActivity extends BaseActivity {

    @BindView(R.id.kefu1)
    LinearLayout kefu1;
    @BindView(R.id.kefu2)
    LinearLayout kefu2;
    @BindView(R.id.zalo)
    LinearLayout zalo;
    @BindView(R.id.facebook)
    LinearLayout facebook;
    @BindView(R.id.phone1)
    TextView phone1;
    @BindView(R.id.phone2)
    TextView phone2;
    @BindView(R.id.zalo_num)
    TextView zaloNum;
    @BindView(R.id.facebook_num)
    TextView facebookNum;


    @Override
    protected int getLayout() {
        return R.layout.act_kefu;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.my_kefu));
    }


    @OnClick({R.id.kefu1, R.id.kefu2})
    public void kefuClick(View view) {
        switch (view.getId()) {
            case R.id.kefu1:
                String strPhone1 = phone1.getText().toString().trim();
                new AlertDialog(this).builder().setGone().setTitle(getResources().getString(R.string.kefudianhua))
                        .setMsg(strPhone1)
                        .setNegativeButton(getResources().getString(R.string.cancle), null)
                        .setPositiveButton(getResources().getString(R.string.call), v -> PhoneUtils.callPhone(strPhone1)).show();
                break;
            case R.id.kefu2:
                String strPhone2 = phone2.getText().toString().trim();
                new AlertDialog(this).builder().setGone().setTitle(getResources().getString(R.string.kefudianhua))
                        .setMsg(strPhone2)
                        .setNegativeButton(getResources().getString(R.string.cancle), null)
                        .setPositiveButton(getResources().getString(R.string.call), v -> PhoneUtils.callPhone(strPhone2)).show();

                break;
        }
    }


    @OnClick({R.id.zalo, R.id.facebook})
    public void zaloClick(View view) {
        switch (view.getId()) {
            case R.id.zalo:
                String strPhone1 = zaloNum.getText().toString().trim();
                new AlertDialog(this).builder().setGone().setTitle(getResources().getString(R.string.zalo))
                        .setMsg(strPhone1)
                        .setNegativeButton(getResources().getString(R.string.cancle), null)
                        .setPositiveButton(getResources().getString(R.string.copy), v -> copyText(strPhone1)).show();
                break;
            case R.id.facebook:
                String strPhone2 = facebookNum.getText().toString().trim();
                new AlertDialog(this).builder().setGone().setTitle(getResources().getString(R.string.facebook))
                        .setMsg(strPhone2)
                        .setNegativeButton(getResources().getString(R.string.cancle), null)
                        .setPositiveButton(getResources().getString(R.string.copy), v -> copyText(strPhone2)).show();

                break;
        }
    }


    private void copyText(String text) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(text);
        showToast(getResources().getString(R.string.copy_sourss));
    }

}
