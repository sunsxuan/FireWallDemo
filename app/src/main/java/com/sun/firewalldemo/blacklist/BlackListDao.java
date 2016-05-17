package com.sun.firewalldemo.blacklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 2016/5/4.
 */
public class BlackListDao {
    private BlackListDB mBlackListDB;

    public BlackListDao(Context context) {
        this.mBlackListDB = new BlackListDB(context);
    }

    //查询所有黑名单的数据
    public List<BlackListBean> getAllDatas() {
        List<BlackListBean> datas = new ArrayList<>();
        SQLiteDatabase db = mBlackListDB.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + BlackListDBTable.BLACKLISTTABLE + " ", null);
        while (cursor.moveToNext()) {
            BlackListBean bean = new BlackListBean();
            bean.setName(cursor.getString(cursor.getColumnIndex(BlackListDBTable.NAME)));
            bean.setPhone(cursor.getString(cursor.getColumnIndex(BlackListDBTable.PHONE)));
            bean.setMode(cursor.getInt(cursor.getColumnIndex(BlackListDBTable.MODE)));
            datas.add(bean);
        }
        cursor.close();
        db.close();
        return datas;
    }



    /**
     * 通过添加Bean方式添加黑名单和拦截方式
     */

    public void add(BlackListBean bean) {
        add(bean.getName(), bean.getPhone(), bean.getMode());
    }

    //添加黑名单和拦截方式
    public void add(String name, String phone, int mode) {
        SQLiteDatabase db = mBlackListDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BlackListDBTable.NAME, name);
        values.put(BlackListDBTable.MODE, mode);
        values.put(BlackListDBTable.PHONE, phone);
        db.insert(BlackListDBTable.BLACKLISTTABLE, null, values);
        db.close();
    }





    /**
     * 更新新的拦截方式
     *
     * @param phone
     * @param mode
     */
    public void update(String name, String phone, int mode) {
        SQLiteDatabase db = mBlackListDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BlackListDBTable.MODE, mode);
        values.put(BlackListDBTable.NAME, name);
        db.update(BlackListDBTable.BLACKLISTTABLE, values, BlackListDBTable.PHONE + "=?", new String[]{phone});
        db.close();
    }

    public void delete(String phone) {
        SQLiteDatabase db = mBlackListDB.getWritableDatabase();
        db.delete(BlackListDBTable.BLACKLISTTABLE, BlackListDBTable.PHONE + "=?", new String[]{phone});
        db.close();
    }

    /**
     * 查询电话拦截模式
     *
     * @param phone
     */
    public int getMode(String phone) {
        SQLiteDatabase db = mBlackListDB.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + BlackListDBTable.MODE + " from "
                        + BlackListDBTable.BLACKLISTTABLE + " where " + BlackListDBTable.PHONE + " = ?",
                new String[]{phone});
        int mode = 0;
        if (cursor.moveToNext()) {
            mode = cursor.getInt(cursor.getColumnIndex("mode"));
            System.out.println("mode = cursor.getInt(cursor.getColumnIndex(\"mode\"));" + mode);

        } else {
            mode = 0;
        }
        System.out.println("return mode " + mode);
        return mode;
    }
}
