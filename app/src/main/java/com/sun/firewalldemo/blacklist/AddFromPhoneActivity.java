package com.sun.firewalldemo.blacklist;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
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
public class AddFromPhoneActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
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

        //读取联系人信息
        readContacts();

        lv_phone_list = (ListView) findViewById(R.id.lv_phone_list);
        lv_phone_list.setOnItemClickListener(this);
        mAdapter = new PhoneAdapter(phoneList,this);
        lv_phone_list.setAdapter(mAdapter);

    }

    //读取联系人列表
    private void readContacts() {


        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null
        );
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            ));
            String number = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            ));
            mPhoneBean = new PhoneBean();
            mPhoneBean.setName(name);
            mPhoneBean.setNumber(number);
            phoneList.add(mPhoneBean);

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
