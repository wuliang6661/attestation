package com.skyvn.hw.view.attentionziliao;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.google.gson.Gson;
import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.bean.AttentionSourrssBO;
import com.skyvn.hw.bean.AuthTypeBO;
import com.skyvn.hw.bean.ContactBO;
import com.skyvn.hw.bean.SmsBO;
import com.skyvn.hw.config.IConstant;
import com.skyvn.hw.mvp.MVPBaseActivity;
import com.skyvn.hw.util.AppManager;
import com.skyvn.hw.util.AuthenticationUtils;
import com.skyvn.hw.util.SMSUtils;
import com.skyvn.hw.util.UpdateFileUtils;
import com.skyvn.hw.util.phone.PhoneDto;
import com.skyvn.hw.util.phone.PhoneUtil;
import com.skyvn.hw.view.CommonMsgActivity;
import com.skyvn.hw.view.DialogAttentionActivity;
import com.skyvn.hw.view.JiaZhaoActivity;
import com.skyvn.hw.view.LiveAttentionActivity;
import com.skyvn.hw.view.Msg14Activity;
import com.skyvn.hw.view.VideoActivity;
import com.skyvn.hw.view.bindbankcard.BindBankCardActivity;
import com.skyvn.hw.view.emergencycontact.EmergencyContactActivity;
import com.skyvn.hw.view.person_msg_style.PersonMsgActivity;
import com.skyvn.hw.view.person_msg_style.PersonMsgActivity2;
import com.skyvn.hw.view.shiming_style.ShiMingActivity;
import com.skyvn.hw.view.shiming_style.ShiMingActivity2;
import com.skyvn.hw.widget.AlertDialog;
import com.skyvn.hw.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.hw.widget.lgrecycleadapter.LGViewHolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AttentionZiliaoActivity extends MVPBaseActivity<AttentionZiliaoContract.View,
        AttentionZiliaoPresenter> implements AttentionZiliaoContract.View {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.back)
    LinearLayout back;

    private List<AuthTypeBO> typeBOS;

    @Override
    protected int getLayout() {
        return R.layout.act_attention_ziliao;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        back.setVisibility(View.VISIBLE);
        setTitleText(getResources().getString(R.string.renzhengziliao));
        rightButton();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAuthList();
    }


    @OnClick(R.id.back)
    public void back() {
        AppManager.getAppManager().goHome();
    }


    /**
     * 获取所有认证项的认证状态
     */
    private void getAuthList() {
        HttpServerImpl.getAuthList().subscribe(new HttpResultSubscriber<List<AuthTypeBO>>() {
            @Override
            public void onSuccess(List<AuthTypeBO> typeBOS) {
                AttentionZiliaoActivity.this.typeBOS = typeBOS;
                showAttentionAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 显示所有认证项
     */
    private void showAttentionAdapter() {
        LGRecycleViewAdapter<AuthTypeBO> adapter = new LGRecycleViewAdapter<AuthTypeBO>(typeBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_attention_msg;
            }

            @Override
            public void convert(LGViewHolder holder, AuthTypeBO authTypeBO, int position) {
                holder.setText(R.id.item_name, authTypeBO.getName());
                TextView item_type = (TextView) holder.getView(R.id.item_type);
                int needStatus = authTypeBO.getNeedStatus();
                int status = authTypeBO.getStatus();
                if (needStatus == 0) {   //不必填
                    if (status == 0) {   //未完成
                        item_type.setTextColor(Color.parseColor("#0077EA"));
                        item_type.setText(getResources().getString(R.string.weiwancheng));
                        holder.getView(R.id.item_image).setVisibility(View.VISIBLE);
                    } else if (status == 1) {
                        item_type.setTextColor(Color.parseColor("#666666"));
                        item_type.setText(getResources().getString(R.string.yiwancheng));
                        holder.getView(R.id.item_image).setVisibility(View.INVISIBLE);
                    } else {
                        item_type.setTextColor(Color.parseColor("#0077EA"));
                        item_type.setText(getResources().getString(R.string.weiwancheng));
                        holder.getView(R.id.item_image).setVisibility(View.VISIBLE);
                    }
                } else {
                    if (status == 0) {   //未完成
                        item_type.setTextColor(Color.parseColor("#FF6860"));
                        item_type.setText(getResources().getString(R.string.weiwancheng));
                        holder.getView(R.id.item_image).setVisibility(View.VISIBLE);
                    } else if (status == 1) {
                        item_type.setTextColor(Color.parseColor("#666666"));
                        item_type.setText(getResources().getString(R.string.yiwancheng));
                        holder.getView(R.id.item_image).setVisibility(View.INVISIBLE);
                    } else {
                        item_type.setTextColor(Color.parseColor("#FF6860"));
                        item_type.setText(getResources().getString(R.string.weiwancheng));
                        holder.getView(R.id.item_image).setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (typeBOS.get(position).getStatus() == 1) {  //已完成
                    return;
                }
                if (isFirst(position)) {
                    goAttention(typeBOS.get(position).getCode(), typeBOS.get(position).getNeedStatus());
                }

            }
        });
        recycleView.setAdapter(adapter);
    }


    private boolean isFirst(int position) {
//        for (int i = 0; i < typeBOS.size(); i++) {
//            if (i < position) {
//                if (typeBOS.get(i).getNeedStatus() != 0 && typeBOS.get(i).getStatus() != 1) {   //必填未完成
//                    showToast(getString(R.string.qingxianrenzhengshangyige));
//                    return false;
//                }
//            }
//        }
        return true;
    }


    private void goAttention(String code, int needStatus) {
        switch (code) {
            case AuthenticationUtils.PERSON_MSG:   //个人资料
                if (IConstant.STYLE == 1) {
                    gotoActivity(PersonMsgActivity.class, needStatus);
                } else {
                    gotoActivity(PersonMsgActivity2.class, needStatus);
                }
                break;
            case AuthenticationUtils.ID_CARD:  //身份证验证
                if (IConstant.STYLE == 1) {
                    gotoActivity(ShiMingActivity.class, needStatus);
                } else {
                    gotoActivity(ShiMingActivity2.class, needStatus);
                }
                break;
            case AuthenticationUtils.LIVE_PAGE:  // 活体验证
                gotoActivity(LiveAttentionActivity.class, needStatus);
                break;
            case AuthenticationUtils.CONTACT_PAGE:  // 紧急联系人验证
                gotoActivity(EmergencyContactActivity.class, needStatus);
                break;
            case AuthenticationUtils.DEVICE_PAGE:  // 驾照验证
                gotoActivity(JiaZhaoActivity.class, needStatus);
                break;
            case AuthenticationUtils.PHONE_COMMON: // 运营商验证
                showToast(getString(R.string.wurenzheng));
                break;
            case AuthenticationUtils.PHONE_LIST:  // 通讯录验证
//                requestPermission();
                Intent intent = new Intent(this, DialogAttentionActivity.class);
                intent.putExtra("needStatus", needStatus);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            case AuthenticationUtils.BIND_BANK_CARD:  // 绑定银行卡验证
                gotoActivity(BindBankCardActivity.class, needStatus);
                break;
            case AuthenticationUtils.SMS__JILU_PAGE:  //短信记录验证
//                requestSmsPermission();
                Intent intent1 = new Intent(this, DialogAttentionActivity.class);
                intent1.putExtra("needStatus", needStatus);
                intent1.putExtra("type", 1);
                startActivity(intent1);
                break;
            case AuthenticationUtils.SMS_PAGE:   //1414短信验证
                gotoActivity(Msg14Activity.class, needStatus);
                break;
            case AuthenticationUtils.VIDEO_PAGE:  //手持身份证小视频
                gotoActivity(VideoActivity.class, needStatus);
                break;
            case AuthenticationUtils.COMMON_MSG_PAGE:  // 公司资料验证
                gotoActivity(CommonMsgActivity.class, needStatus);
                break;
        }
    }


    /**
     * 常用的跳转方法
     */
    public void gotoActivity(Class<?> cls, int needSourss) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("needStatus", needSourss);
        startActivity(intent);
    }


    /**
     * 通讯录权限
     */
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        } else {
            getPersonList("");
        }
    }


    /**
     * 短信记录权限
     */
    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 300);
        } else {
            getSmsList();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200://刚才的识别码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    getPersonList("");
                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(this, R.string.tongxunluquanxian, Toast.LENGTH_LONG).show();
                }
                break;
            case 300:  //短信记录
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    getSmsList();
                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(this, R.string.duanxinhuotongxunluquanxian, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }


    List<PhoneDto> phones;

    /**
     * 获取通讯录用户
     */
    private void getPersonList(String msg) {
        showProgress();
        new Thread(() -> {
            try {
                phones = new PhoneUtil(AttentionZiliaoActivity.this).searchContacts(msg);
                handler.sendEmptyMessage(0x11);
            } catch (Exception ex) {
                ex.printStackTrace();
                handler.sendEmptyMessage(0x22);
            }
        }).start();
    }

    List<SmsBO> smsBOS;

    /**
     * 获取短信记录
     */
    private void getSmsList() {
        showProgress();
        new Thread(() -> {
            try {
                smsBOS = SMSUtils.obtainPhoneMessage(AttentionZiliaoActivity.this);
                handler.sendEmptyMessage(0x33);
            } catch (Exception ex) {
                ex.printStackTrace();
                handler.sendEmptyMessage(0x22);
            }
        }).start();
    }


    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    stopProgress();
                    new AlertDialog(AttentionZiliaoActivity.this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
                            .setMsg(getResources().getString(R.string.contact_aleg_dialog))
                            .setNegativeButton(getResources().getString(R.string.buyunxu), null)
                            .setPositiveButton(getResources().getString(R.string.hao), v -> {
                                commitContactList(phones);
                            }).show();
                    break;
                case 0x22:
                    stopProgress();
                    break;
                case 0x33:
                    stopProgress();
                    new AlertDialog(AttentionZiliaoActivity.this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
                            .setMsg(getString(R.string.sms_aleg_dialog))
                            .setNegativeButton(getResources().getString(R.string.buyunxu), null)
                            .setPositiveButton(getResources().getString(R.string.hao), v -> {
                                commitSmsList();
                            }).show();
                    break;
            }
        }
    };


    /**
     * 上传短信记录验证
     */
    private void commitSmsList() {
        showProgress();
        String json = new Gson().toJson(smsBOS);
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/saas.json";
        FileUtils.createFileByDeleteOldFile(filePath);
        try {
            FileWriter fw = new FileWriter(new File(filePath));
            BufferedWriter out = new BufferedWriter(fw);
            out.write(json);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UpdateFileUtils utils = new UpdateFileUtils();
        utils.setListener(new UpdateFileUtils.OnCallBackListener() {
            @Override
            public void call(String s) {
                updateSms(s);
            }

            @Override
            public void callError(String message) {
                stopProgress();
                showToast(message);
            }
        });
        utils.updateFile(4, filePath);
    }

    private void updateSms(String url) {
        HttpServerImpl.addClientSmsRecordAuth(url).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                stopProgress();
                showToast(getResources().getString(R.string.commit_sourss_toast));
//                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), AttentionZiliaoActivity.this);
                getAuthList();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    /**
     * 上传通讯录验证
     */
    private void commitContactList(List<PhoneDto> phoneDtos) {
        showProgress();
        List<ContactBO> contactBOS = new ArrayList<>();
        for (PhoneDto phone : phoneDtos) {
            ContactBO contactBO = new ContactBO();
            contactBO.setPhone(phone.getTelPhone());
            contactBO.setName(phone.getName());
            contactBOS.add(contactBO);
        }
        String json = new Gson().toJson(contactBOS);
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/saas.json";
        FileUtils.createFileByDeleteOldFile(filePath);
        try {
            FileWriter fw = new FileWriter(new File(filePath));
            BufferedWriter out = new BufferedWriter(fw);
            out.write(json);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UpdateFileUtils utils = new UpdateFileUtils();
        utils.setListener(new UpdateFileUtils.OnCallBackListener() {
            @Override
            public void call(String s) {
                updateContact(s);
            }

            @Override
            public void callError(String message) {
                stopProgress();
                showToast(message);
            }
        });
        utils.updateFile(3, filePath);
    }


    private void updateContact(String url) {
        HttpServerImpl.commitContactList(url).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                stopProgress();
                showToast(getResources().getString(R.string.commit_sourss_toast));
//                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), AttentionZiliaoActivity.this);
                getAuthList();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


}
