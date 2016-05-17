package com.sun.firewalldemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sun.firewalldemo.blacklist.BlacklistFragment;
import com.sun.firewalldemo.message.MessageFragment;
import com.sun.firewalldemo.phonelog.PhoneFragment;

/**
 * Created by S on 2016/5/3.
 */
public class MainFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

//    private final BlacklistFragment blacklistFragment = new BlacklistFragment()  ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);


        initViewPager(mViewPager);

        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.blacklist));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.phone));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.message));
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    private void initViewPager(ViewPager viewPager) {
        //
        viewPager.setOffscreenPageLimit(3);
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager(),getActivity());

        adapter.addFragment(new BlacklistFragment(),getString(R.string.blacklist));
        adapter.addFragment(new PhoneFragment(),getString(R.string.phone));
        adapter.addFragment(new MessageFragment(),getString(R.string.message));
        viewPager.setAdapter(adapter);
    }


   /* public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
*/

}
