package com.sun.firewalldemo.blacklist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.firewalldemo.MainActivity;
import com.sun.firewalldemo.R;

import java.util.List;

/**
 * Created by S on 2016/5/16.
 */
public class BlackListItemActivity extends AppCompatActivity {
    private EditText  edt_name;
    private TextView tv_phone;
    private Button btn_addManual;
    private CheckBox cb_phone, cb_msg;
    private BlackListDao dao;
    private List<BlackListBean> beanList;

    private String now_phone;
    private int now_mode;

    private static final String EXTRA = BlackListItemActivity.class.getSimpleName();

    public static Intent newIntent(Context context, int position) {
        Intent intent = new Intent(context, BlackListItemActivity.class);
        intent.putExtra(EXTRA, position);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_blacklistitem);

        dao = new BlackListDao(this);


        initView();
        initEvent();
    }

    private void initView() {
        beanList = dao.getAllDatas();
        //从List列表进入传递的位置
        int position = getIntent().getIntExtra(EXTRA, 0);
        now_phone = beanList.get(position).getPhone();

        now_mode = beanList.get(position).getMode();

        /*edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_phone.setText(now_phone);*/
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_phone.setText(now_phone);

        edt_name = (EditText) findViewById(R.id.edt_name);

        btn_addManual = (Button) findViewById(R.id.btn_addManual);


        cb_phone = (CheckBox) findViewById(R.id.cb_phone);
        cb_msg = (CheckBox) findViewById(R.id.cb_msg);
        if (now_mode == BlackListDBTable.ALL) {
            cb_msg.setChecked(true);
            cb_phone.setChecked(true);
            return;
        } else if (now_mode == BlackListDBTable.MSG) {
            cb_msg.setChecked(true);
            cb_phone.setChecked(false);
            return;
        } else {
            cb_msg.setChecked(false);
            cb_phone.setChecked(true);
            return;
        }


    }

    private void initEvent() {
        btn_addManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String phone = edt_phone.getText().toString();
                System.out.println("String phone = edt_phone.getText().toString();" +phone);*/
                Boolean bMsg = cb_msg.isChecked();
                Boolean bPhone = cb_phone.isChecked();

                if (!(bMsg || bPhone)) {
                    Toast.makeText(BlackListItemActivity.this, "拦截方式至少有一个", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    int mode = BlackListDBTable.ALL;
                    //如果bMsg为假，则拦截方式为电话拦截
                    if (!bMsg) {
                        mode = BlackListDBTable.TEL;
                        Toast.makeText(BlackListItemActivity.this, "拦截方式为电话拦截", Toast.LENGTH_SHORT).show();
                    } else if (!bPhone) {
                        mode = BlackListDBTable.MSG;
                        Toast.makeText(BlackListItemActivity.this, "拦截方式为短信拦截", Toast.LENGTH_SHORT).show();
                    } else {
                        //更新拦截

                        Toast.makeText(BlackListItemActivity.this, "拦截方式为全部拦截", Toast.LENGTH_SHORT).show();
                    }

                    dao.update(now_phone, mode);
                    Intent intent = new Intent(BlackListItemActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


}
