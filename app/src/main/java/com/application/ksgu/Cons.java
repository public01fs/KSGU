package com.application.ksgu;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Cons {
    public static final String SERVER_URL 		  		= "http://35.231.34.16/api/";
    public static final String URL                      = "https://sslog.apps.samudera.id:9500/apibooking/Api/";
    public static final String URL1                     = "http://core.samudera.com:8010/coins2018/modules/survey/";
    public static final String URL2                     = "http://core.samudera.com:8010/coins2018/modules/gate/";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CREATED = "created_at";
    public static final String KEY_UPDATED = "updated_at";
    public static final String KEY_KETERANGAN = "keterangan";
    public static final String KEY_IMG = "img";
    public static final String KEY_NPWP = "npwp";
    public static final String KEY_ALAMAT = "alamat";
    public static final String KEY_TELEPON = "telepon";
    public static final String KEY_DAERAH = "daerah";
    public static final String KEY_DAERAH_ID = "daerah_id";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_AGEN_ID = "agen_id";
    public static final String KEY_AGEN_NAMA = "agen_nama";
    public static final String KEY_AGEN_ALAMAT = "agen_alamat";
    public static final String KEY_AGEN_KOTA = "agen_kota";

    public static List<String> getDetailProfile(Context ctx) {
        List<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.detail_tabs);
        for (int i = 0; i < name_arr.length; i++) {
            items.add(name_arr[i]);
        }
        return items;
    }
}
