package com.sun.firewalldemo.blacklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sun.firewalldemo.R;

import java.util.List;

/**
 * Created by S on 2016/5/17.
 */
public class PhoneAdapter extends BaseAdapter {
    private List<PhoneBean> phoneList;
    private PhoneBean mPhoneBean;
    private Context mContext;
    private LayoutInflater mInflater;


    public PhoneAdapter(List<PhoneBean> phoneList, Context context) {
        this.phoneList = phoneList;
        mContext = context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return phoneList.size();
    }

    @Override
    public Object getItem(int position) {
        return phoneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.act_fromphone_item,parent,false);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_phone_number);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_phone_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        mPhoneBean = phoneList.get(position);
        final String name = mPhoneBean.getName();
        final String phone = mPhoneBean.getNumber();
        holder.tv_name.setText(name);
        holder.tv_number.setText(phone);


        return convertView;
    }

    class ViewHolder{
        TextView tv_name ,tv_number;
    }
}
