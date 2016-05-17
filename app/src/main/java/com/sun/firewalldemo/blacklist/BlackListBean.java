package com.sun.firewalldemo.blacklist;

/**
 * Created by S on 2016/5/15.
 */
public class BlackListBean {
    private String name ;
    private String phone;
    private int mode;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BlackListBean{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", mode=" + mode +
                '}';
    }
}
