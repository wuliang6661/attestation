package com.skyvn.hw.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.skyvn.hw.bean.SmsBO;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class SMSUtils {

    private static Uri SMS_INBOX = Uri.parse("content://sms/");

    public static List<SmsBO> obtainPhoneMessage(Context context) {
        List<SmsBO> list = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
        if (null == cur) {
            Log.i("ooc", "************cur == null");
            return null;
        }
        while (cur.moveToNext()) {
            String number = cur.getString(cur.getColumnIndex("address"));//手机号
            String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
            String body = cur.getString(cur.getColumnIndex("body"));//短信内容
            long date = cur.getLong(cur.getColumnIndex("date"));
            //至此就获得了短信的相关的内容, 以下是把短信加入map中，构建listview,非必要。
            Log.i(TAG, "name=" + name + "phoneNumber=" + number + ",body=" + body);
            SmsBO smsBO = new SmsBO();
            smsBO.setMessage(body);
            smsBO.setName(StringUtils.isEmpty(name) ? "-" : name);
            smsBO.setPhone(number);
            smsBO.setSendTime(TimeUtils.millis2String(date));
            list.add(smsBO);
        }
        cur.close();
        return list;
    }


}
