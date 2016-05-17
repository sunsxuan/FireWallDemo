package com.sun.firewalldemo.blacklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.sun.firewalldemo.MainActivity;
import com.sun.firewalldemo.R;

import java.util.List;

/**
 * Created by S on 2016/5/15.
 */
public class AddFromManualActivity extends AppCompatActivity {
    private EditText edt_phone,edt_name;
    private Button btn_addManual;
    private CheckBox cb_phone,cb_msg;
    private BlackListDao dao;
    private List<BlackListBean> beanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_manual);
        dao = new BlackListDao(this);

        initView();
        initEvent();
    }

    private void initView() {
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_name = (EditText) findViewById(R.id.edt_name);
        btn_addManual = (Button) findViewById(R.id.btn_addManual);
        btn_addManual.setClickable(false);
        cb_phone = (CheckBox) findViewById(R.id.cb_phone);
        cb_msg = (CheckBox) findViewById(R.id.cb_msg);

        beanList = dao.getAllDatas();
    }

    private void initEvent() {
        btn_addManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edt_phone.getText().toString();
                String name = edt_name.getText().toString();
                Boolean bMsg = cb_msg.isChecked();
                Boolean bPhone = cb_phone.isChecked();

                if (TextUtils.isEmpty(phone) ){
                    Toast.makeText(AddFromManualActivity.this,"号码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if (!(bMsg||bPhone)){
                    Toast.makeText(AddFromManualActivity.this,"拦截方式至少有一个",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    int mode = BlackListDBTable.ALL;
                    //如果bMsg为假，则拦截方式为电话拦截
                    if (!bMsg){
                        mode = BlackListDBTable.TEL;
                        Toast.makeText(AddFromManualActivity.this,"拦截方式为电话拦截",Toast.LENGTH_SHORT).show();
                    }else if (!bPhone){
                        mode = BlackListDBTable.MSG;
                        Toast.makeText(AddFromManualActivity.this,"拦截方式为短信拦截",Toast.LENGTH_SHORT).show();
                    }else {
                        //添加电话号码到最顶端
                        //dao.add(phone,mode);

                        Toast.makeText(AddFromManualActivity.this,"拦截方式为全部拦截",Toast.LENGTH_SHORT).show();
                    }

                    BlackListBean bean = new BlackListBean();
                    String defaultName = "未知";
                    if (name.length()==0){
                        name = defaultName;
                    }
                    bean.setName(name);
                    bean.setPhone(phone);
                    bean.setMode(mode);
                    dao.add(bean);
                    //
                    beanList.add(0,bean);

                    System.out.println("beanList = dao.getAllDatas();" +beanList);

                    Intent intent = new Intent(AddFromManualActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


}
