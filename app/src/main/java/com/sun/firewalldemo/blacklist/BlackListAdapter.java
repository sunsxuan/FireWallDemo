package com.sun.firewalldemo.blacklist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.firewalldemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 2016/5/15.
 */
public class BlackListAdapter extends BaseAdapter {
    private BlackListAdapter mAdapter;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<BlackListBean> mBlackListBeen = new ArrayList<>();
    private BlackListDao dao;

    public BlackListAdapter(Context context,List<BlackListBean> blackListBeen) {
        mAdapter = this;
        mContext = context;
        mBlackListBeen = blackListBeen;
        mInflater=LayoutInflater.from(context);
    }

    public void setDao(BlackListDao dao){
        this.dao = dao;
    }
    public void setBean(List<BlackListBean> bean){
        mBlackListBeen = bean;
    }

    @Override
    public int getCount() {
        return mBlackListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mBlackListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null){
            holder = new ViewHolder();
            convertView=mInflater.inflate(R.layout.blacklist_item,parent,false);

            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_item);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_item_phone);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_item_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BlackListBean bean = mBlackListBeen.get(position);


        if (bean.getName()==null){
            holder.tv_name.setText("未命名");
        }else {
            holder.tv_name.setText(bean.getName());
        }
        holder.tv_phone.setText(bean.getPhone());
        //为删除图片添加点击事件
        holder.tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BlackListItemActivity.newIntent(mContext,position);
                mContext.startActivity(intent);
            }
        });

        //为删除图片添加点击事件
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(mContext);
                builder.setTitle("警告");
                builder.setMessage("确定要删除这个黑名单吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.delete(bean.getPhone());
                        mBlackListBeen.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });
        return convertView;
    }

    public class ViewHolder{
        TextView  tv_name;
        TextView  tv_phone;
        ImageView imageView;

    }
}
