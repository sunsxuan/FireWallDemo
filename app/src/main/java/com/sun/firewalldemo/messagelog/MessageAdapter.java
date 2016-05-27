package com.sun.firewalldemo.messagelog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sun.firewalldemo.R;
import com.sun.firewalldemo.blacklist.BlackListDao;

import java.util.List;

/**
 * Created by S on 2016/5/17.
 */
public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    private List<MessageBean> mListBeen;
    private BlackListDao dao;
    private LayoutInflater mInflater;

    public MessageAdapter(Context context, List<MessageBean> listBeen) {
        mContext = context;
        mListBeen = listBeen;
        dao = new BlackListDao(context);
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

    public void setBean(List<MessageBean> bean){
        mListBeen = bean;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.frag_mess_item,parent,false);
            holder.tv_content= (TextView) convertView.findViewById(R.id.tv_mess_content);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_mess_name);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_mess_time);
            holder.tv_phone= (TextView) convertView.findViewById(R.id.tv_mess_number);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        MessageBean bean = new MessageBean();
        bean = mListBeen.get(position);
        holder.tv_content.setText(bean.getContent());
        holder.tv_name.setText(bean.getName());
        holder.tv_phone.setText(bean.getPhone());
        holder.tv_time.setText(bean.getTime());

        return convertView;
    }

    class ViewHolder{
        TextView tv_content,tv_name,tv_time,tv_phone;
    }
}
