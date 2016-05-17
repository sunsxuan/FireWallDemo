package com.sun.firewalldemo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 2016/5/3.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String>  mTabTitle = new ArrayList<>();

    private Context mContext;


    public MyPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext = context;
    }

    public void addFragment(Fragment fragment,String title) {
        mFragments.add(fragment);
        mTabTitle.add(title);
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitle.get(position);
    }
}
