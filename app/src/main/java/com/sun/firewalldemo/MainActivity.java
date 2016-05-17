package com.sun.firewalldemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //加载Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_content,new MainFragment())
                .commit();
/*        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout)findViewById(R.id.tab_layout);


        initViewPager(mViewPager);

        mTabLayout.addTab(mTabLayout.newTab().setText("黑名单"));
        mTabLayout.addTab(mTabLayout.newTab().setText("电话拦截"));
        mTabLayout.addTab(mTabLayout.newTab().setText("短信拦截"));
        mTabLayout.setupWithViewPager(mViewPager);*/

    }


/*
    private void initViewPager(ViewPager viewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),this);
        adapter.addFragment(new PhoneFragment());
        adapter.addFragment(new MessageFragment());
        adapter.addFragment(new BlacklistFragment());
        viewPager.setAdapter(adapter);
    }
*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
