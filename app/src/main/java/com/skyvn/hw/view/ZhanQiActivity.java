package com.skyvn.hw.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.bean.OrderDetailsBO;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 展期说明
 */
public class ZhanQiActivity extends BaseActivity {


    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;

    OrderDetailsBO detailsBO;

    @Override
    protected int getLayout() {
        return R.layout.act_zhanqi;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.zhanqi_title));
        rightButton();

        detailsBO = (OrderDetailsBO) getIntent().getExtras().getSerializable("order");
        Glide.with(this).load(detailsBO.getLogoOssUrl())
                .error(R.drawable.user_img_defalt).placeholder(R.drawable.user_img_defalt).into(userImg);
        userName.setText(detailsBO.getSmsName());
    }


    @OnClick(R.id.kefu_layout)
    public void clickKefu() {
        Intent intent = new Intent(this, KefuActivity.class);
        intent.putExtra("id", detailsBO.getTenantId());
        startActivity(intent);
//        gotoActivity(KefuActivity.class, false);
    }
}
