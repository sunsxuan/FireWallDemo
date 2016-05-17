package com.sun.firewalldemo.blacklist;

/**
 * Created by S on 2016/5/4.
 */
public class BlackListDBTable {
    public static final  String BLACKLISTTABLE = "blacklist";
    public static final  String MESSAGETABLE = "message";
    public static final  String PHONELOGTABLE = "phonelog";
    public static final  String NAME = "name";
    public static final  String PHONE = "phone";
    public static final  String MODE = "mode";
    public static final  String MSG_CONTENT = "content";
    public static final  String TIME = "time";
    //拦截电话
    public static final  int TEL = 1;
    //拦截短信
    public static final  int MSG = 2;
    //拦截全部
    public static final  int ALL = 3;
}
