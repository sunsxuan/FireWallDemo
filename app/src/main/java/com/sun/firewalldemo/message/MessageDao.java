package com.sun.firewalldemo.message;

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
public class MessageDao {
    private BlackListDB  mBlackListDB;

    public MessageDao(Context context) {
        mBlackListDB = new BlackListDB(context);
    }

    /**
     * 返回所有短信接收信息
     * @return
     */
    public List<MessageBean> getMsgContent() {
        List<MessageBean> datas = new ArrayList<>();
        SQLiteDatabase db = mBlackListDB.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + BlackListDBTable.MESSAGETABLE + " ", null);
        while (cursor.moveToNext()) {
            MessageBean bean = new MessageBean();
            bean.setName(cursor.getString(cursor.getColumnIndex(BlackListDBTable.NAME)));
            bean.setPhone(cursor.getString(cursor.getColumnIndex(BlackListDBTable.PHONE)));
            bean.setContent(cursor.getString(cursor.getColumnIndex(BlackListDBTable.MSG_CONTENT)));
            bean.setTime(cursor.getString(cursor.getColumnIndex(BlackListDBTable.TIME)));
            datas.add(bean);
        }
        cursor.close();
        db.close();
        return datas;
    }

    //添加拦截短信信息
    public void add(String name, String phone, String content, String time) {
        SQLiteDatabase db = mBlackListDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BlackListDBTable.NAME, name);
        values.put(BlackListDBTable.PHONE, phone);
        values.put(BlackListDBTable.MSG_CONTENT, content);
        values.put(BlackListDBTable.TIME, time);
        db.insert(BlackListDBTable.MESSAGETABLE, null, values);
        db.close();
    }
}
