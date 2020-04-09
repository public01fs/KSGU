package com.application.ksgu;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Cons {
    public static final String SERVER_URL 		  		= "http://35.231.34.16/api/";
    public static final String URL                      = "https://sslog.apps.samudera.id:9500/apibooking/Api/";
    public static final String URL1                     = "http://core.samudera.com:8010/coins2018/modules/survey/";
    public static final String URL2                     = "http://core.samudera.com:8010/coins2018/modules/gate/";

    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_NAMA = "name";
    public static final String KEY_USERNAME = "nickname";
    public static final String KEY_TIPE = "vendor";
    public static final String KEY_NIP_NIM = "branch_id";
    public static final String KEY_TOKEN = "depo_code";

    public static List<String> getDetailProfile(Context ctx) {
        List<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.detail_tabs);
        for (int i = 0; i < name_arr.length; i++) {
            items.add(name_arr[i]);
        }
        return items;
    }
}
