package com.sun.firewalldemo.blacklist;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sun.firewalldemo.BaseActivity;
import com.sun.firewalldemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 2016/5/15.
 */
public class AddFromMsgActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView lv_phone_list;
    private PhoneBean mPhoneBean;
    private List<PhoneBean> phoneList;
    private PhoneAdapter mAdapter;
    private BlackListDao dao;

    private final String SMS_URI_INBOX = "content://sms/inbox" ; //收件箱


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fromphone);
        phoneList = new ArrayList<>();

        //读取收件箱联系人
        readInboxLog();

        lv_phone_list = (ListView) findViewById(R.id.lv_phone_list);
        lv_phone_list.setOnItemClickListener(this);
        mAdapter = new PhoneAdapter(phoneList, this);
        lv_phone_list.setAdapter(mAdapter);

    }


    private void readInboxLog() {

        Cursor cursor = getContentResolver().query(Uri.parse(SMS_URI_INBOX),
                null,null,null,null);
        while (cursor.moveToNext()){

            String name = cursor.getString(cursor.getColumnIndex("person"));

            String number = cursor.getString(cursor.getColumnIndex("address"));
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