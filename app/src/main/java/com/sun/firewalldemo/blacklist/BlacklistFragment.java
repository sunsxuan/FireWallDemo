package com.sun.firewalldemo.blacklist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sun.firewalldemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 2016/5/3.
 */
public class BlacklistFragment extends Fragment  implements View.OnClickListener{
    private Button btn_add_blackList;
    private TextView tv_noList;
    private ListView mListView;
    private BlackListAdapter mAdapter;
    private List<BlackListBean> mBlackListBeen;
    private BlackListDao mBlackListDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blacklist,container,false);
        btn_add_blackList  = (Button) view.findViewById(R.id.btn_blacklist);
        tv_noList = (TextView) view.findViewById(R.id.tv_noList);

        btn_add_blackList.setOnClickListener(this);

        mListView = (ListView) view.findViewById(R.id.lv_blackList);

        mBlackListDao = new BlackListDao(getContext());

        updateUI();

        return view;
    }

    //重新更新UI
    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }


    private void updateUI() {
        mBlackListBeen = mBlackListDao.getAllDatas();

        if (mAdapter == null){
//            mBlackListBeen = new ArrayList<>();

            mAdapter = new BlackListAdapter(getContext(),mBlackListBeen);

            System.out.println(" System.out.println(mBlackListBeen); " +mBlackListBeen);
            mListView.setAdapter(mAdapter);
        }else {
            mAdapter.setDao(mBlackListDao);

//            mBlackListBeen = new ArrayList<>();
//            mBlackListBeen = mBlackListDao.getAllDatas();
            mAdapter.setBean(mBlackListBeen);

            System.out.println("mAdapter.notifyDataSetChanged();" +mBlackListBeen);
            mAdapter.notifyDataSetChanged();
        }

        if (mBlackListBeen.isEmpty()){
            tv_noList.setVisibility(View.VISIBLE);
        }else {
            tv_noList.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_blacklist:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("添加黑名单号码");
                //指定下拉列表
                final String[] lists = {"从通话记录添加","从短信记录添加","从联系人添加","手动添加"};
                builder.setItems(lists, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:
                                Intent intent1 = new Intent(getContext(),AddFromPhoneLogActivity.class);
                                startActivity(intent1);
                                break;
                            case 1:
                                Intent intent2 = new Intent(getContext(),AddFromMsgActivity.class);
                                startActivity(intent2);
                                break;
                            case 2:
                                Intent intent3 = new Intent(getContext(),AddFromPhoneActivity.class);
                                startActivity(intent3);
                                break;
                            case 3:
                                Intent intent4 = new Intent(getContext(),AddFromManualActivity.class);
                                startActivity(intent4);
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
        updateUI();
    }
}
