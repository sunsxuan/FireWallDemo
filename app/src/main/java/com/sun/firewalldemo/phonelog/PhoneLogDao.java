package com.sun.firewalldemo.phonelog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sun.firewalldemo.blacklist.BlackListDB;
import com.sun.firewalldemo.blacklist.BlackListDBTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 2016/5/17.
 */
public class PhoneLogDao {
    private BlackListDB  mBlackListDB;

    public PhoneLogDao(Context context) {
        mBlackListDB = new BlackListDB(context);
    }

    /**
     * 返回所有以拦截号码
     * @return
     */
    public List<PhoneLogBean> getPhoneLog() {
        List<PhoneLogBean> datas = new ArrayList<>();
        SQLiteDatabase db = mBlackListDB.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + BlackListDBTable.PHONELOGTABLE + " ", null);
        while (cursor.moveToNext()) {
            PhoneLogBean bean = new PhoneLogBean();
            bean.setName(cursor.getString(cursor.getColumnIndex(BlackListDBTable.NAME)));
            bean.setPhone(cursor.getString(cursor.getColumnIndex(BlackListDBTable.PHONE)));
            bean.setTime(cursor.getString(cursor.getColumnIndex(BlackListDBTable.TIME)));
            datas.add(bean);
        }
        cursor.close();
        db.close();
        return datas;
    }


    public void add(String name, String phone, String time) {
        SQLiteDatabase db = mBlackListDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BlackListDBTable.NAME, name);
        values.put(BlackListDBTable.PHONE, phone);
        values.put(BlackListDBTable.TIME, time);
        db.insert(BlackListDBTable.PHONELOGTABLE, null, values);
        db.close();
    }

    /**
     * 删除号码
     * @param phone
     */
    public void delete(String phone){
        SQLiteDatabase db = mBlackListDB.getWritableDatabase();
        db.delete(BlackListDBTable.PHONELOGTABLE,BlackListDBTable.PHONE +"=?",new String[]{phone});
        db.close();
    }
}
