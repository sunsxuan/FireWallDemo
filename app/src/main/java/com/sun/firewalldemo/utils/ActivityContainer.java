package com.sun.firewalldemo.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 2016/5/19.
 */
public class ActivityContainer {
    private static ActivityContainer sContainer;
    private  List<Activity> activityStack = new ArrayList<>();
    private ActivityContainer(){

    }

    public static ActivityContainer getInstance(){
        if (sContainer == null){
            sContainer = new ActivityContainer();
        }
        return sContainer;
    }

    public  void addActivity(Activity activity){

        activityStack.add(activity);
    }

    public  void removeActivity(Activity activity){
        activityStack.remove(activity);
    }

    public  void finishAll(){
        for (Activity activity : activityStack){
            if (null != activity){
                activity.finish();
            }
        }
        activityStack.clear();
    }
}
