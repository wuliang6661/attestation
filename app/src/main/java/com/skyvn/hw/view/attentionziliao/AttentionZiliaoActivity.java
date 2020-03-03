package com.skyvn.hw.view.attentionziliao;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.skyvn.hw.R;
import com.skyvn.hw.api.HttpResultSubscriber;
import com.skyvn.hw.api.HttpServerImpl;
import com.skyvn.hw.bean.AuthTypeBO;
import com.skyvn.hw.config.IConstant;
import com.skyvn.hw.mvp.MVPBaseActivity;
import com.skyvn.hw.view.CommonMsgActivity;
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
import com.skyvn.hw.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.hw.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AttentionZiliaoActivity extends MVPBaseActivity<AttentionZiliaoContract.View,
        AttentionZiliaoPresenter> implements AttentionZiliaoContract.View {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private List<AuthTypeBO> typeBOS;

    @Override
    protected int getLayout() {
        return R.layout.act_attention_ziliao;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
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
                    } else {
                        item_type.setTextColor(Color.parseColor("#666666"));
                        item_type.setText(getResources().getString(R.string.yiwancheng));
                        holder.getView(R.id.item_image).setVisibility(View.INVISIBLE);
                    }
                } else {
                    if (status == 0) {   //未完成
                        item_type.setTextColor(Color.parseColor("#FF6860"));
                        item_type.setText(getResources().getString(R.string.weiwancheng));
                        holder.getView(R.id.item_image).setVisibility(View.VISIBLE);
                    } else {
                        item_type.setTextColor(Color.parseColor("#666666"));
                        item_type.setText(getResources().getString(R.string.yiwancheng));
                        holder.getView(R.id.item_image).setVisibility(View.INVISIBLE);
                    }
                }
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (typeBOS.get(position).getStatus() != 0) {  //已完成
                    return;
                }
                goAttention(typeBOS.get(position).getCode());
            }
        });
        recycleView.setAdapter(adapter);
    }


    private void goAttention(String code) {
        switch (code) {
            case "0":   //个人资料
                if (IConstant.STYLE == 1) {
                    gotoActivity(PersonMsgActivity.class, false);
                } else {
                    gotoActivity(PersonMsgActivity2.class, false);
                }
                break;
            case "1":  //身份证验证
                if (IConstant.STYLE == 1) {
                    gotoActivity(ShiMingActivity.class, false);
                } else {
                    gotoActivity(ShiMingActivity2.class, false);
                }
                break;
            case "2":  // 活体验证
                gotoActivity(LiveAttentionActivity.class, false);
                break;
            case "3":  // 紧急联系人验证
                gotoActivity(EmergencyContactActivity.class, false);
                break;
            case "4":  // 驾照验证
                gotoActivity(JiaZhaoActivity.class, false);
                break;
            case "5": // 运营商验证
                showToast(getString(R.string.wurenzheng));
                break;
            case "6":  // 通讯录验证
                showToast(getString(R.string.wurenzheng));
                break;
            case "7":  // 绑定银行卡验证
                gotoActivity(BindBankCardActivity.class, false);
                break;
            case "8":  //短信记录验证
                showToast(getString(R.string.wurenzheng));
                break;
            case "9":   //1414短信验证
                gotoActivity(Msg14Activity.class, false);
                break;
            case "10":  //手持身份证小视频
                gotoActivity(VideoActivity.class, false);
                break;
            case "11":  // 公司资料验证
                gotoActivity(CommonMsgActivity.class, false);
                break;
        }
    }

}
