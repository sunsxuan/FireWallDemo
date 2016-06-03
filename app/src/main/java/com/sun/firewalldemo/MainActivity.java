package com.sun.firewalldemo;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sun.firewalldemo.utils.ActivityContainer;
import com.sun.firewalldemo.utils.LogUtils;

public class MainActivity extends BaseActivity {
    private long currentBackTime = 0; //当前按下返回键的系统时间
    private long lastBackTime = 0; //上次按下返回键的系统时间
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(1);

        /**
         * 启动开启服务
         */
        mPref = getSharedPreferences("data",MODE_PRIVATE);
        boolean tb_1_checked = mPref.getBoolean("tb1",false);
        if (tb_1_checked){
            Intent intent = new Intent(this,BlackListService.class);
            startService(intent);
        }




        //加载Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_content,new MainFragment())
                .commit();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                Intent intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_quit:
                AlertDialog.Builder  builder  = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("确定退出应用吗？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //销毁所有Activity
                        ActivityContainer.getInstance().finishAll();
                    }
                });
                builder.show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            currentBackTime = System.currentTimeMillis();
            LogUtils.d("TAG","currentBackTime "+currentBackTime );
            //比较两次时间间隔
            if (currentBackTime-lastBackTime > 2*1000){
                Toast.makeText(MainActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
                lastBackTime = currentBackTime;
            }else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 注册开机广播   开机启动服务
     */
    public class BootCompleteReceiver extends BroadcastReceiver{
        private final String ACTION = "android.intent.action.BOOT_COMPLETED";
        @Override
        public void onReceive(Context context, Intent intent) {
            mPref = getSharedPreferences("data",MODE_PRIVATE);
            boolean tb_2_checked = mPref.getBoolean("tb2",false);
            if (tb_2_checked){
                if (intent.getAction().equals(ACTION)){
                    //启动服务
                    Intent intent1 = new Intent(context, BlackListService.class);
                    startService(intent1);
                    Toast.makeText(context, "开机启动监听服务", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}

