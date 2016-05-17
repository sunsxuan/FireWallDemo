package com.sun.firewalldemo;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.sun.firewalldemo.blacklist.BlackListBean;
import com.sun.firewalldemo.blacklist.BlackListDao;
import com.sun.firewalldemo.blacklist.BlackListDBTable;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void testAdd(){
        BlackListDao dao = new BlackListDao(getContext());
        for (int i=0;i<20;i++){
            dao.add("1234567", BlackListDBTable.TEL);
            Log.d("TAG", "testSum: "+i);
            System.out.println("testSum: "+i);
        }
        List<BlackListBean> datas = dao.getAllDatas();
        System.out.println(datas);
    }

    public void testQueryAll(){
        BlackListDao dao = new BlackListDao(getContext());
        List<BlackListBean> datas = dao.getAllDatas();
        System.out.println(datas);
    }

    public void testUpdate(){
        BlackListDao dao = new BlackListDao(getContext());
        dao.update("1234567", BlackListDBTable.ALL);
        List<BlackListBean> datas = dao.getAllDatas();
        System.out.println(datas);
    }

    public void testDelete(){
        BlackListDao dao = new BlackListDao(getContext());
        dao.delete("1234567");
    }

}