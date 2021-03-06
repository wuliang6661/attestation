package com.skyvn.hw.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.base.BaseActivity;
import com.skyvn.hw.base.MyApplication;
import com.skyvn.hw.bean.ContaceBO;
import com.skyvn.hw.bean.KeFuBO;
import com.skyvn.hw.bean.OrderDetailsBO;
import com.skyvn.hw.config.IConstant;
import com.skyvn.hw.util.AppManager;
import com.skyvn.hw.util.PhoneUtils;
import com.skyvn.hw.util.language.LanguageType;
import com.skyvn.hw.widget.AlertDialog;
import com.skyvn.hw.widget.MyDialog;
import com.skyvn.hw.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.hw.widget.lgrecycleadapter.LGViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 立即提现界面
 */
public class ConfirmationActivity extends BaseActivity {


    @BindView(R.id.jine_num)
    TextView jineNum;
    @BindView(R.id.qixian_num)
    TextView qixianNum;
    @BindView(R.id.dangzhang_num)
    TextView dangzhangNum;
    @BindView(R.id.lixi_num)
    TextView lixiNum;
    @BindView(R.id.fuwufei_num)
    TextView fuwufeiNum;
    @BindView(R.id.yinghuan_num)
    TextView yinghuanNum;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.bt_commit)
    Button btCommit;
    @BindView(R.id.shuoming)
    TagFlowLayout shuoming;
    @BindView(R.id.person_img)
    RoundedImageView personImg;
    @BindView(R.id.person_name)
    TextView personName;
    //    @BindView(R.id.kefu_num1)
//    TextView kefuNum1;
//    @BindView(R.id.kefu_layout)
//    LinearLayout kefuLayout;
//    @BindView(R.id.kefu_num2)
//    TextView kefuNum2;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private String orderId;
    private OrderDetailsBO orderDetailsBO;

    @Override
    protected int getLayout() {
        return R.layout.act_comfirmation;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.querenjiekuan));
        rightButton();


        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        recycleView.setNestedScrollingEnabled(false);
        orderId = getIntent().getExtras().getString("id");
        getOrderDetails();
        setFlow();
    }

    private void setFlow() {
        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.jiekuan_hint1));
        list.add(getResources().getString(R.string.jiekuan_hint2));
        list.add(getResources().getString(R.string.jiekuan_hint3));
        list.add(getResources().getString(R.string.jiekuan_hint4));
        idFlowlayout.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.text_xieyi,
                        idFlowlayout, false);
                tv.setText(s);
                if (position == 1 || position == 3) {
                    tv.setTextColor(Color.parseColor("#2F87E0"));
                } else {
                    tv.setTextColor(Color.parseColor("#888888"));
                }
                return tv;
            }
        });
        idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (position == 1 || position == 3) {
                    getContact(position);
                }
                return false;
            }
        });
    }


    private void getContact(int position) {
        showProgress();
        HttpServerImpl.getContract().subscribe(new HttpResultSubscriber<ContaceBO>() {
            @Override
            public void onSuccess(ContaceBO s) {
                stopProgress();
                if (s == null) {
                    showToast(getString(R.string.huoquxieyishibai));
                    return;
                }
                String url;
                String title;
                if (position == 1) {
                    url = s.getLoanContractUrl();
                    title = getResources().getString(R.string.jiekuan_hint2);
                } else {
                    url = s.getServiceContractUrl();
                    title = getResources().getString(R.string.jiekuan_hint4);
                }
                if (url.contains("?")) {
                    url += "&productId=" + orderDetailsBO.getProductId() + "&token=" + MyApplication.token + "&tenantId=" + orderDetailsBO.getTenantId()
                            + "&language=" + MyApplication.spUtils.getString(IConstant.LANGUAGE_TYPE, LanguageType.CHINESE.getLanguage());
                } else {
                    url += "?productId=" + orderDetailsBO.getProductId() + "&token=" + MyApplication.token + "&tenantId=" +
                            orderDetailsBO.getTenantId() + "&language=" +
                            MyApplication.spUtils.getString(IConstant.LANGUAGE_TYPE, LanguageType.CHINESE.getLanguage());
                }
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                bundle.putString("title", title);
                gotoActivity(WebActivity.class, bundle, false);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private void getOrderDetails() {
        showProgress();
        HttpServerImpl.getMyLoan(orderId).subscribe(new HttpResultSubscriber<OrderDetailsBO>() {
            @Override
            public void onSuccess(OrderDetailsBO s) {
                stopProgress();
                orderDetailsBO = s;
                showData();
                getKeFu();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private void showData() {
        Glide.with(this).load(orderDetailsBO.getLogoOssUrl())
                .error(R.drawable.user_img_defalt).placeholder(R.drawable.user_img_defalt).into(personImg);
        personName.setText(orderDetailsBO.getSmsName());
        jineNum.setText(orderDetailsBO.getPrice());
        qixianNum.setText(orderDetailsBO.getDays() + "");
        dangzhangNum.setText(orderDetailsBO.getPayAmount() + getResources().getString(R.string.danwei_yuan));
        lixiNum.setText(orderDetailsBO.getInterestAmount() + getResources().getString(R.string.danwei_yuan));
        yinghuanNum.setText(orderDetailsBO.getDelayRepaymentAmount() + getResources().getString(R.string.danwei_yuan));
        fuwufeiNum.setText(orderDetailsBO.getServiceAmount() + getResources().getString(R.string.danwei_yuan));

        showYuqiFlow();
    }


    /**
     * 设置逾期费用显示
     */
    private void showYuqiFlow() {
        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.jiekuan_shuoming1));
        list.add(getResources().getString(R.string.jiekuan_shuoming2) + orderDetailsBO.getOverdueAmount() + "/" +
                getResources().getString(R.string.danwei_tian));
        list.add(getResources().getString(R.string.jiekuan_shuoming3));
        shuoming.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.text_xieyi,
                        shuoming, false);
                tv.setText(s);
                if (position == 1) {
                    tv.setTextColor(Color.parseColor("#000000"));
                } else {
                    tv.setTextColor(Color.parseColor("#888888"));
                }
                return tv;
            }
        });
    }


    @OnClick(R.id.bt_commit)
    public void commit() {
        if (!checkbox.isChecked()) {
            showToast(getResources().getString(R.string.xieyi_hint));
            return;
        }
        HttpServerImpl.withDraw(orderId, orderDetailsBO.getTenantId()).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showTiXianSourss();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 获取全部客服
     */
    private void getKeFu() {
        HttpServerImpl.getUserContact(orderDetailsBO.getTenantId()).subscribe(new HttpResultSubscriber<List<KeFuBO>>() {
            @Override
            public void onSuccess(List<KeFuBO> kefus) {
                if (kefus == null) {
                    return;
                }
                setAdapter(kefus);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void setAdapter(List<KeFuBO> kefus) {
        LGRecycleViewAdapter<KeFuBO> adapter = new LGRecycleViewAdapter<KeFuBO>(kefus) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_confirmation_kefu;
            }

            @Override
            public void convert(LGViewHolder holder, KeFuBO keFuBO, int position) {
                holder.setText(R.id.kefu_name, keFuBO.getName() + "：");
                holder.setText(R.id.kefu_num1, keFuBO.getContact());
            }
        };
        adapter.setOnItemClickListener(R.id.kefu_num1, (view, position) -> {
            switch (kefus.get(position).getType()) {
                case 0:   //电话
                    new AlertDialog(ConfirmationActivity.this).builder().setGone().setTitle(kefus.get(position).getName())
                            .setMsg(kefus.get(position).getContact())
                            .setNegativeButton(getResources().getString(R.string.cancle), null)
                            .setPositiveButton(getResources().getString(R.string.call), v -> PhoneUtils.callPhone(kefus.get(position).getContact())).show();
                    break;
                default:
                    new AlertDialog(ConfirmationActivity.this).builder().setGone().setTitle(kefus.get(position).getName())
                            .setMsg(kefus.get(position).getContact())
                            .setNegativeButton(getResources().getString(R.string.cancle), null)
                            .setPositiveButton(getResources().getString(R.string.copy), v -> copyText(kefus.get(position).getContact())).show();
                    break;
            }
        });
        recycleView.setAdapter(adapter);
    }


    private void copyText(String text) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(text);
        showToast(getResources().getString(R.string.copy_sourss));
    }

    private void showTiXianSourss() {
        View view = getLayoutInflater().inflate(R.layout.dialog_jiekuan_souress, null);
        TextView guanBi = view.findViewById(R.id.guanbi);
        MyDialog mMyDialog = new MyDialog(this, 0, 0, view, R.style.DialogTheme);
        mMyDialog.setCancelable(false);
        guanBi.setOnClickListener(view1 -> {
            mMyDialog.dismiss();
            AppManager.getAppManager().goHome();
        });
        mMyDialog.show();
    }


    @OnClick(R.id.kefu_img)
    public void clickFuwu() {
        showFuWuDialog();
    }

    /**
     * 显示服务费明细
     */
    private void showFuWuDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_fuwufei, null);
        TextView fuwuName1 = view.findViewById(R.id.fuwu_name1);
        TextView fuwuName2 = view.findViewById(R.id.fuwu_name2);
        TextView fuwuName3 = view.findViewById(R.id.fuwu_name3);
        TextView fuwuNum1 = view.findViewById(R.id.fuwu_num1);
        TextView fuwuNum2 = view.findViewById(R.id.fuwu_num2);
        TextView fuwuNum3 = view.findViewById(R.id.fuwu_num3);
        TextView guanBi = view.findViewById(R.id.guanbi);
        fuwuName1.setText(orderDetailsBO.getServiceOneName());
        fuwuName2.setText(orderDetailsBO.getServiceTwoName());
        fuwuName3.setText(orderDetailsBO.getServiceThreeName());
        fuwuNum1.setText(orderDetailsBO.getServiceOnePrice() + getResources().getString(R.string.danwei_yuan));
        fuwuNum2.setText(orderDetailsBO.getServiceTwoPrice() + getResources().getString(R.string.danwei_yuan));
        fuwuNum3.setText(orderDetailsBO.getServiceThreePrice() + getResources().getString(R.string.danwei_yuan));
        MyDialog mMyDialog = new MyDialog(this, 0, 0, view, R.style.DialogTheme);
        mMyDialog.setCancelable(true);
        guanBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyDialog.dismiss();
            }
        });
        mMyDialog.show();
    }

}
