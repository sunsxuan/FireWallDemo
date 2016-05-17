package com.sun.firewalldemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;
import com.sun.firewalldemo.blacklist.BlackListDao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by S on 2016/5/16.
 */
public class PhoneReceiver extends BroadcastReceiver {
    private PhoneStateListener listener;

    @Override
    public void onReceive(final Context context, Intent intent) {
        final BlackListDao dao = new BlackListDao(context);

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            //去电电话
            System.out.println("去电电话");
        }else {
            //来电电话
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            //设置监听器
            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

            /*String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                //state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))
                System.out.println("来电电话");

            }*/
        }

        listener = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                switch (state){
                    //手机空闲
                    case TelephonyManager.CALL_STATE_IDLE:
                        System.out.println("TelephonyManager.CALL_STATE_IDLE:");
                        break;
                    //电话被挂起
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        System.out.println("TelephonyManager.CALL_STATE_OFFHOOK");
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        System.out.println("TelephonyManager.CALL_STATE_RINGING"+incomingNumber);

                        /*int mode = dao.getMode(incomingNumber);
                        if ((mode== BlackListDBTable.ALL)||(mode==BlackListDBTable.TEL)){
                            stopCall();
                        }*/

                        if (incomingNumber.equals("17098159625")){
                            stopCall();
                        }
                }
            }

            private void stopCall() {

                try {
                    Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
                    // 获取远程TELEPHONY_SERVICE的IBinder对象的代理


                    IBinder binder = (IBinder) method.invoke(null,Context.TELECOM_SERVICE);
                    // 将IBinder对象的代理转换为ITelephony对象
                    ITelephony telephony = ITelephony.Stub.asInterface(binder);
                    // 挂断电话
                    telephony.endCall();
                    telephony.cancelMissedCallsNotification();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
