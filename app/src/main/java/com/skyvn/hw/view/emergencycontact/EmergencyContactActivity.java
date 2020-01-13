package com.skyvn.hw.view.emergencycontact;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.skyvn.hw.R;
import com.skyvn.hw.mvp.MVPBaseActivity;
import com.skyvn.hw.view.contact.ContactActivity;

import butterknife.OnClick;


/**
 * MVPPlugin
 * 紧急联系人
 */

public class EmergencyContactActivity extends MVPBaseActivity<EmergencyContactContract.View,
        EmergencyContactPresenter> implements EmergencyContactContract.View {

    @Override
    protected int getLayout() {
        return R.layout.act_emergency_contact;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.jingjilianxiren));
        rightButton();
    }


    @OnClick({R.id.one_phone_layout, R.id.two_phone_layout})
    public void phoneClick(View view) {
        switch (view.getId()) {
            case R.id.one_phone_layout:
                gotoActivity(ContactActivity.class, false);
                break;
            case R.id.two_phone_layout:
                gotoActivity(ContactActivity.class, false);
                break;
        }
    }
}
