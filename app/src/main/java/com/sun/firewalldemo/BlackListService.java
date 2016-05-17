package com.sun.firewalldemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by S on 2016/5/16.
 */
public class BlackListService extends Service {
    private SmsReceiver mSmsReceiver;
    private PhoneReceiver mPhoneReceiver;
    private PhoneStateListener listener;
    private TelephonyManager tm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("Service onCreate()");

        //注册短信接听  短信广播
        mSmsReceiver = new SmsReceiver();
        mPhoneReceiver = new PhoneReceiver();

        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mSmsReceiver, intentFilter);

        //注册电话接听

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
                        break;
                    default:
                        break;
                }

            }

            private void stopCall() {
                ITelephony telephony = null;
                try {
                    Class clazz = Class.forName("android.os.ServiceManager");
                    Method method = clazz.getDeclaredMethod("getService", String.class);
                    // 获取远程TELEPHONY_SERVICE的IBinder对象的代理


                    IBinder binder = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
                    // 将IBinder对象的代理转换为ITelephony对象
                    telephony = ITelephony.Stub.asInterface(binder);
                    // 挂断电话
                    telephony.endCall();

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
        tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

       /* <intent-filter>
        <action android:name="android.intent.action.NEW_OUTGOING_CALL"/>
        <action android:name="android.intent.action.PHONE_STATE"/>
        </intent-filter>*/

        /*IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.NEW_OUTGOING_CALL");
        intentFilter2.addAction("android.intent.action.PHONE_STATE");
        registerReceiver(mPhoneReceiver,intentFilter2);*/

        super.onCreate();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service onDestroy() ");
        unregisterReceiver(mSmsReceiver);

        tm.listen(listener,PhoneStateListener.LISTEN_NONE);
    }
}
