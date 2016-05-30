package com.sun.firewalldemo.messagelog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sun.firewalldemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 2016/5/3.
 */
public class MessageFragment extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
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
        mListView.setOnItemLongClickListener(this);
        mListView.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" +mBeanList.get(position).getPhone()));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("确定删除这条记录吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMessageDao.delete(mBeanList.get(position).getPhone());

                updateUI();
            }
        });
        builder.show();
        return false;
    }
}
