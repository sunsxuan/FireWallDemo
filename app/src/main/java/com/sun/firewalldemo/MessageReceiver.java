package com.sun.firewalldemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.sun.firewalldemo.blacklist.BlackListDBTable;
import com.sun.firewalldemo.blacklist.BlackListDao;
import com.sun.firewalldemo.message.MessageDao;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by S on 2016/5/16.
 */
public class MessageReceiver extends BroadcastReceiver {
    final String SMS_URI_INBOX = "content://sms/inbox";
    private BlackListDao mBlackListDao;
    private MessageDao mMessageDao;

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for (Object pdu : pdus) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
            String number = smsMessage.getDisplayOriginatingAddress();
            String content = smsMessage.getMessageBody();

            long date = smsMessage.getTimestampMillis();
            Date timeDate = new Date(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            String time = simpleDateFormat.format(timeDate);
            String name = "未知";

            mBlackListDao = new BlackListDao(context);
            int mode = mBlackListDao.getMode(number);

            mMessageDao = new MessageDao(context);

            //拦截此号码的短信
            if ((mode== BlackListDBTable.MSG)||(mode== BlackListDBTable.ALL)){
                System.out.println(" abort "+number);

                //将此短信加入到短信拦截列表
                mMessageDao.add(name,number,content,time);

                abortBroadcast();
            }


        }
    }

}
