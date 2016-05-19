package com.sun.firewalldemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sun.firewalldemo.utils.ActivityContainer;

/**
 * Created by S on 2016/5/19.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityContainer.getInstance().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityContainer.getInstance().removeActivity(this);
        Log.d("Finish Activity", getClass().getSimpleName());
    }
}
