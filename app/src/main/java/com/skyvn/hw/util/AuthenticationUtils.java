package com.skyvn.hw.util;

import android.app.Activity;
import android.content.Intent;

import com.skyvn.hw.view.CommonMsgActivity;
import com.skyvn.hw.view.JiaZhaoActivity;
import com.skyvn.hw.view.LiveAttentionActivity;
import com.skyvn.hw.view.Msg14Activity;
import com.skyvn.hw.view.PersonMsgActivity;
import com.skyvn.hw.view.ShiMingActivity;
import com.skyvn.hw.view.VideoActivity;
import com.skyvn.hw.view.attentionziliao.AttentionZiliaoActivity;
import com.skyvn.hw.view.bindbankcard.BindBankCardActivity;
import com.skyvn.hw.view.contact.ContactActivity;
import com.skyvn.hw.view.emergencycontact.EmergencyContactActivity;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1910:27
 * desc   :  根据认证顺序判断去哪个页面
 * version: 1.0
 */
public class AuthenticationUtils {

    public static final int PERSON_MSG = 0;// 个人资料认证
    public static final int ID_CARD = 1;   //身份证认证
    public static final int LIVE_PAGE = 2;  //活体验证
    public static final int CONTACT_PAGE = 3;  //紧急联系人
    public static final int DEVICE_PAGE = 4;    //驾照验证
    public static final int PHONE_COMMON = 5;   //运营商验证
    public static final int PHONE_LIST = 6;      //通讯录验证
    public static final int BIND_BANK_CARD = 7;   //绑定银行卡验证
    public static final int SMS__JILU_PAGE = 8;         //短信记录认证
    public static final int SMS_PAGE = 9;       //短信1414验证
    public static final int VIDEO_PAGE = 10;    //视频验证
    public static final int COMMON_MSG_PAGE = 11;   //公司信息认证


    public static void goAuthNextPage(String pageNo, int needSourss, Activity context) {
        int code = Integer.parseInt(pageNo);
        switch (code) {
            case PERSON_MSG:
                gotoActivity(PersonMsgActivity.class, needSourss, true, context);
                break;
            case ID_CARD:
                gotoActivity(ShiMingActivity.class, needSourss, true, context);
                break;
            case LIVE_PAGE:
                gotoActivity(LiveAttentionActivity.class, needSourss, true, context);
                break;
            case CONTACT_PAGE:
                gotoActivity(EmergencyContactActivity.class, needSourss, true, context);
                break;
            case DEVICE_PAGE:
                gotoActivity(JiaZhaoActivity.class, needSourss, true, context);
                break;
            case PHONE_COMMON:
                break;
            case PHONE_LIST:
                Intent intent = new Intent(context, ContactActivity.class);
                intent.putExtra("needStatus", needSourss);
                intent.putExtra("auth_type", 0);
                context.startActivity(intent);
                context.finish();
                break;
            case BIND_BANK_CARD:
                gotoActivity(BindBankCardActivity.class, needSourss, true, context);
                break;
            case SMS__JILU_PAGE:
                break;
            case SMS_PAGE:
                gotoActivity(Msg14Activity.class, needSourss, true, context);
                break;
            case VIDEO_PAGE:
                gotoActivity(VideoActivity.class, needSourss, true, context);
                break;
            case COMMON_MSG_PAGE:
                gotoActivity(CommonMsgActivity.class, needSourss, true, context);
                break;
            case -1:   //认证完成
                gotoActivity(AttentionZiliaoActivity.class, needSourss, true, context);
                break;
        }
    }


    /**
     * 常用的跳转方法
     */
    public static void gotoActivity(Class<?> cls, int needSourss, boolean isFinish, Activity context) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("needStatus", needSourss);
        context.startActivity(intent);
        if (isFinish) {
            context.finish();
        }
    }

}
