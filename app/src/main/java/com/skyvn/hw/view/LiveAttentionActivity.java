package com.skyvn.hw.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.skyvn.hw.R;
import com.skyvn.hw.base.BaseActivity;

import ai.advance.liveness.lib.LivenessResult;
import ai.advance.liveness.sdk.activity.LivenessActivity;
import ai.advance.liveness.sdk.activity.ResultActivity;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1411:04
 * desc   : 开始人脸扫描界面
 * version: 1.0
 */
public class LiveAttentionActivity extends BaseActivity {

    static final int REQUEST_CODE_LIVENESS = 1000;
    static final int REQUEST_CODE_RESULT_PAGE = 1001;

    private static final int PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected int getLayout() {
        return R.layout.act_live_attention;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.shuanianrenzheng));
        rightButton();
    }


    @OnClick(R.id.bt_login)
    public void goLive() {
        checkPermissions();
    }


    private void toLivenessActivity() {
        Intent intent = new Intent(this, LivenessActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LIVENESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_LIVENESS:
                if (resultCode == RESULT_OK) {
//                    Call the following methods to get results
                    String livenessId = LivenessResult.getLivenessId();
                    String livenessBitmap = LivenessResult.getLivenessBase64Str();
                    String transactionId = LivenessResult.getTransactionId();
                    boolean success = LivenessResult.isSuccess();
                    String errorMsg = LivenessResult.getErrorMsg();
                    startActivityForResult(new Intent(this, ResultActivity.class), REQUEST_CODE_RESULT_PAGE);
                }
                break;
            case REQUEST_CODE_RESULT_PAGE: {
                if (resultCode == RESULT_OK) {
                    toLivenessActivity();
                }
            }
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Detect camera authorization
     */
    public void checkPermissions() {
        if (allPermissionsGranted()) {
            onPermissionGranted();
        } else {
            ActivityCompat.requestPermissions(this, getRequiredPermissions(), PERMISSIONS_REQUEST_CODE);
        }
    }

    public String[] getRequiredPermissions() {
        return new String[]{Manifest.permission.CAMERA};
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            //已授权
            if (allGranted(grantResults)) {
                onPermissionGranted();
            } else {
                onPermissionRefused();
            }
        }
    }

    /**
     * Denied camera permissions
     */
    public void onPermissionRefused() {
        new AlertDialog.Builder(this).setMessage(getString(R.string.liveness_no_camera_permission)).setPositiveButton(getString(R.string.liveness_perform), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).create().show();
    }

    private boolean allGranted(int[] grantResults) {
        boolean hasPermission = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
            }
        }
        return hasPermission;
    }

    /**
     * Got camera permissions
     */
    public void onPermissionGranted() {
        toLivenessActivity();
    }
}
