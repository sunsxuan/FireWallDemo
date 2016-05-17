package com.sun.firewalldemo.blacklist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sun.firewalldemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 2016/5/15.
 */
public class AddFromPhoneLogActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lv_phone_list;
    private PhoneBean mPhoneBean;
    private List<PhoneBean> phoneList;
    private PhoneAdapter mAdapter;
    private BlackListDao dao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fromphone);
        phoneList = new ArrayList<>();

        //读取最近通话联系人
        readCallLog();

        lv_phone_list = (ListView) findViewById(R.id.lv_phone_list);
        lv_phone_list.setOnItemClickListener(this);
        mAdapter = new PhoneAdapter(phoneList, this);
        lv_phone_list.setAdapter(mAdapter);

    }


    private void readCallLog() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = getContentResolver().query(
                CallLog.Calls.CONTENT_URI, null, null, null, null
        );
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(
                    CallLog.Calls.CACHED_NAME
            ));
            String number = cursor.getString(cursor.getColumnIndex(
                    CallLog.Calls.NUMBER
            ));


            mPhoneBean = new PhoneBean();
            mPhoneBean.setName(name);
            mPhoneBean.setNumber(number);
            phoneList.add(mPhoneBean);
        }

        for (int i = 0; i < phoneList.size() - 1; i++) {
            for (int j = phoneList.size() - 1; j > i; j--) {
                if(phoneList.get(j).toString().equals(phoneList.get(i).toString())){
                    phoneList.remove(j);
                }
            }
        }
        cursor.close();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = phoneList.get(position).getName();
        String number = phoneList.get(position).getNumber();
        dao = new BlackListDao(this);
        dao.add(name,number,BlackListDBTable.ALL);
        //关闭Activity
        finish();
    }
}

