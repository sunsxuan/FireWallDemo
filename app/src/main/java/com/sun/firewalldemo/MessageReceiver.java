package com.sun.firewalldemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.sun.firewalldemo.blacklist.BlackListDao;
import com.sun.firewalldemo.blacklist.BlackListDBTable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by S on 2016/5/16.
 */
public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for (Object pdu : pdus) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
            String sender = smsMessage.getDisplayOriginatingAddress();
            String content = smsMessage.getMessageBody();
            long date = smsMessage.getTimestampMillis();
            Date timeDate = new Date(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(timeDate);

            System.out.println("短信来自:" + sender);
            System.out.println("短信内容:" + content);
            System.out.println("短信时间:" + time);

            BlackListDao dao = new BlackListDao(context);
            int mode = dao.getMode(sender);

            //拦截此号码的短信
            if ((mode== BlackListDBTable.MSG)||(mode== BlackListDBTable.ALL)){
                System.out.println(" abort "+sender);
                abortBroadcast();
            }


        }
    }
}
