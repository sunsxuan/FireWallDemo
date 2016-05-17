package com.sun.firewalldemo.phonelog;

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
public class PhoneLogAdapter extends BaseAdapter {
    private List<PhoneLogBean> mListBeen;
    private Context mContext;
    private LayoutInflater mInflater;

    public PhoneLogAdapter(List<PhoneLogBean> listBeen, Context context) {
        mListBeen = listBeen;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPhoneLog(List<PhoneLogBean> been){
        mListBeen = been;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = mInflater.inflate(R.layout.frag_log_item,parent,false);
            holder = new ViewHolder();
            holder.tv_log_name = (TextView) convertView.findViewById(R.id.tv_log_name);
            holder.tv_log_phone= (TextView) convertView.findViewById(R.id.tv_log_phone);
            holder.tv_log_time= (TextView) convertView.findViewById(R.id.tv_log_time);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        PhoneLogBean bean = mListBeen.get(position);
        String name =  bean.getName();
        String phone = bean.getPhone();
        String time  = bean.getTime();
        holder.tv_log_name.setText(name);
        holder.tv_log_phone.setText(phone);
        holder.tv_log_time.setText(time);

        return convertView;
    }

    class ViewHolder{
        TextView tv_log_name,tv_log_phone,tv_log_time;
    }
}
