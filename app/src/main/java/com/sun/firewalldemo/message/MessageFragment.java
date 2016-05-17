package com.sun.firewalldemo.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.sun.firewalldemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 2016/5/3.
 */
public class MessageFragment extends Fragment {
    private ListView mListView;
    private MessageAdapter mAdapter;
    private List<MessageBean> mBeanList;
    private MessageDao mMessageDao;
    private TextView tv_no_mess;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        mListView = (ListView) view.findViewById(R.id.lv_message);
        tv_no_mess= (TextView) view.findViewById(R.id.tv_no_message);

        mBeanList = new ArrayList<>();
        mMessageDao = new MessageDao(getContext());

        updateUI();


        return view;
    }

    public void updateUI() {
        mBeanList = mMessageDao.getMsgContent();
        if (mAdapter == null){
//            mBeanList = new ArrayList<>();
            mAdapter = new MessageAdapter(getContext(),mBeanList);
            System.out.println(" System.out.println(mBlackListBeen); " +mBeanList);
            mListView.setAdapter(mAdapter);
        }else {
//            mBeanList = new ArrayList<>();
//            mBeanList = mMessageDao.getMsgContent();
            mAdapter.setBean(mBeanList);

            System.out.println("mAdapter.notifyDataSetChanged();" +mBeanList);
            mAdapter.notifyDataSetChanged();
        }

        if (mBeanList.isEmpty()){
            tv_no_mess.setVisibility(View.VISIBLE);
        }else {
            tv_no_mess.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
