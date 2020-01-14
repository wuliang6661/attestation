package com.skyvn.hw.view.contact;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.skyvn.hw.R;
import com.skyvn.hw.mvp.MVPBaseActivity;
import com.skyvn.hw.util.phone.PhoneDto;
import com.skyvn.hw.util.phone.PhoneUtil;
import com.skyvn.hw.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.hw.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;


/**
 * MVPPlugin
 * 通讯录
 */

public class ContactActivity extends MVPBaseActivity<ContactContract.View, ContactPresenter>
        implements ContactContract.View {

    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.search_delete)
    ImageView searchDelete;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @Override
    protected int getLayout() {
        return R.layout.act_contact;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.contact));
        rightButton();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        requestPermission();
        initView();
    }


    private void initView() {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getPersonList(editable.toString());
            }
        });
    }


    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 200);
        } else {
            getPersonList("");
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
                    Toast.makeText(this, "未开启通讯录权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }


    /**
     * 获取通讯录用户
     */
    private void getPersonList(String msg) {
        List<PhoneDto> phones = new PhoneUtil(this).searchContacts(msg);
        LGRecycleViewAdapter<PhoneDto> adapter = new LGRecycleViewAdapter<PhoneDto>(phones) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_persons_layout;
            }

            @Override
            public void convert(LGViewHolder holder, PhoneDto phoneDto, int position) {
                holder.setText(R.id.person_name, phoneDto.getName());
                holder.setText(R.id.phone, phoneDto.getTelPhone());
            }
        };
        recycleView.setAdapter(adapter);
    }

}