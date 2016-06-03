package com.sun.firewalldemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by S on 2016/6/3.
 */
public class NotifyService  extends Service{

    private String mMessage;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
                mMessage = "已拦截黑名单";
                sendNotification(intent);

        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotification(Intent intent) {
        String number = intent.getStringExtra("number");
        System.out.println("number "+number);
        //通知用户
        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pi =  PendingIntent.getActivity(this,0,intent1,
                PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentTitle(mMessage)//设置通知栏标题
                .setContentText(""+number)
                //设置通知栏显示内容
                .setContentIntent(pi) //设置通知栏点击意图
                //  .setNumber(number) //设置通知集合的数量
                .setTicker("拦截黑名单成功") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                //  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_message_white_18dp);//设置通知小ICON

        mNotificationManager.notify(1,mBuilder.build());
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
