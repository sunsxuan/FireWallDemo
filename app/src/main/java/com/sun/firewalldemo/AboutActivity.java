package com.sun.firewalldemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sun.firewalldemo.blacklist.BlackListBean;
import com.sun.firewalldemo.utils.LogUtils;

/**
 * Created by S on 2016/5/3.
 */
public class AboutActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private ToggleButton tb_1, tb_2, tb_3;
    private TextView tv_32;
    private BlackListBean bean;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        bean = new BlackListBean();
        mPref = getSharedPreferences("data", MODE_PRIVATE);
        mEditor = mPref.edit();

        initView();

        initEvent();
    }
    //按钮点击事件
    private void initEvent() {

        boolean tb_1_checked = mPref.getBoolean("tb1", false);
        boolean tb_2_checked = mPref.getBoolean("tb2", false);
        boolean tb_3_checked = mPref.getBoolean("tb3", false);

        if (tb_1_checked){
            tb_2.setVisibility(View.VISIBLE);
            tb_3.setVisibility(View.VISIBLE);
            tb_1.setChecked(true);
            openService();
            if (tb_2_checked){
                tb_2.setChecked(true);
            }else {
                tb_2.setChecked(false);
            }

            if (tb_3_checked){
                tb_3.setChecked(true);
            }else {
                tb_3.setChecked(false);
            }

        }
        else {
            closeService();

            tb_1.setChecked(false);
            tb_2.setChecked(false);
            tb_3.setChecked(false);

            tb_2.setVisibility(View.GONE);
            tb_3.setVisibility(View.GONE);
        }

    }

    private void openService() {
        //启动服务
        Intent intent = new Intent(this, BlackListService.class);
        startService(intent);
        LogUtils.d(getClass().getSimpleName()," open Service.");
    }
    private void closeService(){
        Intent intent = new Intent(this, BlackListService.class);
        stopService(intent);
        LogUtils.d(getClass().getSimpleName()," close Service.");
    }
    //初始化View
    private void initView() {
        tb_1 = (ToggleButton) findViewById(R.id.tb1);
        tb_2 = (ToggleButton) findViewById(R.id.tb2);
        tb_3 = (ToggleButton) findViewById(R.id.tb3);
        tv_32 = (TextView) findViewById(R.id.tv32);

        tb_1.setOnCheckedChangeListener(this);
        tb_2.setOnCheckedChangeListener(this);
        tb_3.setOnCheckedChangeListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.tb1:
                if (tb_1.isChecked()) {

                    //记录开启信息到pref中
                    mEditor.putBoolean("tb1", true);

                   openService();

                    tb_2.setVisibility(View.VISIBLE);
                    tb_3.setVisibility(View.VISIBLE);
                } else {
                    closeService();

                    tb_2.setVisibility(View.GONE);
                    tb_3.setVisibility(View.GONE);

                    tb_2.setChecked(false);
                    tb_3.setChecked(false);
                }
                mEditor.commit();
                break;
            case R.id.tb2:
                if (tb_2.isChecked()) {
                    mEditor.putBoolean("tb2", true);
                    Toast.makeText(this, "拦截提醒已开启", Toast.LENGTH_SHORT).show();
                } else {
                    mEditor.putBoolean("tb2", false);
                    Toast.makeText(this, "拦截提醒已关闭", Toast.LENGTH_SHORT).show();
                }
                mEditor.commit();
                break;
            case R.id.tb3:
                if (tb_3.isChecked()) {
                    mEditor.putBoolean("tb3", true);
                    tv_32.setText("已开启");
                } else {
                    mEditor.putBoolean("tb3", false);
                    tv_32.setText("未开启");
                }
                mEditor.commit();
                break;
            default:
                break;
        }
    }
}
