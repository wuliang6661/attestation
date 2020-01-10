package com.skyvn.hw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.skyvn.hw.MainActivity;
import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/99:34
 * desc   :
 * version: 1.0
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_photo)
    EditText etPhoto;
    @BindView(R.id.input_layout_phone)
    TextInputLayout inputLayoutPhone;
    @BindView(R.id.et_image_verfication)
    EditText etImageVerfication;
    @BindView(R.id.input_layout_image_verification)
    TextInputLayout inputLayoutImageVerification;
    @BindView(R.id.image_verfication)
    ImageView imageVerfication;
    @BindView(R.id.et_verfication)
    EditText etVerfication;
    @BindView(R.id.input_layout_verfication)
    TextInputLayout inputLayoutVerfication;
    @BindView(R.id.get_verfication)
    TextView getVerfication;
    @BindView(R.id.bt_login)
    Button btLogin;

    @Override
    protected int getLayout() {
        return R.layout.act_login;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleText(getResources().getString(R.string.login_txt));
        inputLayoutPhone.setErrorEnabled(false);
        inputLayoutImageVerification.setErrorEnabled(false);
        inputLayoutVerfication.setErrorEnabled(false);
        setListener();
    }


    /**
     * 错误校验
     */
    private void setListener() {
        etImageVerfication.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence) || charSequence.length() != 4) {
                    inputLayoutImageVerification.setError(getResources().getString(R.string.input_image_verfication_error));
                    inputLayoutImageVerification.setErrorEnabled(true);
                } else {
                    inputLayoutImageVerification.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPhoto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence) || (charSequence.length() != 10 && charSequence.length() != 11)) {
                    inputLayoutPhone.setError(getResources().getString(R.string.input_phone_error));
                    inputLayoutPhone.setErrorEnabled(true);
                } else {
                    inputLayoutPhone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etVerfication.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence) || charSequence.length() != 6) {
                    inputLayoutVerfication.setError(getResources().getString(R.string.input_verfication_error));
                    inputLayoutVerfication.setErrorEnabled(true);
                } else {
                    inputLayoutVerfication.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @OnClick(R.id.bt_login)
    public void login() {
        gotoActivity(MainActivity.class, false);
    }

}
