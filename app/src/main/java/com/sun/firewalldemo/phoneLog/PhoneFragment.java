package com.sun.firewalldemo.phonelog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.sun.firewalldemo.R;

import java.util.List;

/**
 * Created by S on 2016/5/3.
 */
public class PhoneFragment extends Fragment {
    private ListView mListView;
    private PhoneLogAdapter mPhoneLogAdapter;
    private List<PhoneLogBean> mBeanList;
    private PhoneLogDao mPhoneLogDao;
    private TextView tv_no_log;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone,container,false);
        mListView = (ListView) view.findViewById(R.id.lv_phone_log);
        tv_no_log= (TextView) view.findViewById(R.id.tv_no_phone);


        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        mPhoneLogDao = new PhoneLogDao(getContext());
        mBeanList = mPhoneLogDao.getPhoneLog();

        if (mPhoneLogAdapter==null){
            mPhoneLogAdapter = new PhoneLogAdapter(mBeanList,getContext());
            mListView.setAdapter(mPhoneLogAdapter);
        }else {
            mPhoneLogAdapter.setPhoneLog(mBeanList);
            mPhoneLogAdapter.notifyDataSetChanged();
        }

        if (mBeanList.isEmpty()){
            tv_no_log.setVisibility(View.VISIBLE);
        }else {
            tv_no_log.setVisibility(View.GONE);
        }
    }
}
