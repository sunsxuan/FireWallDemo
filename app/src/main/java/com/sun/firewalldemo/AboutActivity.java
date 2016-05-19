package com.sun.firewalldemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sun.firewalldemo.blacklist.BlackListBean;

/**
 * Created by S on 2016/5/3.
 */
public class AboutActivity extends BaseActivity  implements CompoundButton.OnCheckedChangeListener{
    private ToggleButton tb_1,tb_2,tb_3;
    private TextView tv_32;
    private BlackListBean bean ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        bean = new BlackListBean();
        initView();
    }

    //初始化View
    private void initView() {
        tb_1= (ToggleButton) findViewById(R.id.tb1);
        tb_1.setChecked(true);


        tb_2= (ToggleButton) findViewById(R.id.tb2);
        tb_3= (ToggleButton) findViewById(R.id.tb3);
        tv_32= (TextView) findViewById(R.id.tv32);

        tb_1.setOnCheckedChangeListener(this);
        tb_2.setOnCheckedChangeListener(this);
        tb_3.setOnCheckedChangeListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if (tb_1.isChecked()){
            bean.setService_mode(1);
        }else {
            bean.setService_mode(2);
        }*/
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.tb1:
                if (tb_1.isChecked()){
                    //启动服务
//                    bean.setService_mode(1);
                    Intent intent = new Intent(this,BlackListService.class);
                    startService(intent);

                    tb_2.setVisibility(View.VISIBLE);
                    tb_3.setVisibility(View.VISIBLE);
                }else {
                    //关闭服务
//                    bean.setService_mode(2);
                    Intent intent = new Intent(this,BlackListService.class);
                    stopService(intent);

                    tb_2.setVisibility(View.GONE);
                    tb_3.setVisibility(View.GONE);

                    tb_2.setChecked(false);
                    tb_3.setChecked(false);
                }
                break;
            case R.id.tb2:
                if (tb_2.isChecked()){
                    Toast.makeText(this,"tb_2.isChecked()",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"tb_2.unChecked()",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tb3:
                if (tb_3.isChecked()){
                    tv_32.setText("已开启");
                }else {
                    tv_32.setText("未开启");                }
                break;
            default:
                break;
        }
    }
}
