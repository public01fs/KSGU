package com.application.ksgu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.application.ksgu.Model.Login;

import java.util.HashMap;

import static com.application.ksgu.Cons.KEY_AGEN_ALAMAT;
import static com.application.ksgu.Cons.KEY_AGEN_ID;
import static com.application.ksgu.Cons.KEY_AGEN_KOTA;
import static com.application.ksgu.Cons.KEY_AGEN_NAMA;
import static com.application.ksgu.Cons.KEY_ALAMAT;
import static com.application.ksgu.Cons.KEY_CREATED;
import static com.application.ksgu.Cons.KEY_DAERAH;
import static com.application.ksgu.Cons.KEY_DAERAH_ID;
import static com.application.ksgu.Cons.KEY_EMAIL;
import static com.application.ksgu.Cons.KEY_ID;
import static com.application.ksgu.Cons.KEY_IMG;
import static com.application.ksgu.Cons.KEY_KETERANGAN;
import static com.application.ksgu.Cons.KEY_NAME;
import static com.application.ksgu.Cons.KEY_NPWP;
import static com.application.ksgu.Cons.KEY_TELEPON;
import static com.application.ksgu.Cons.KEY_TOKEN;
import static com.application.ksgu.Cons.KEY_UPDATED;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    private static final String PREF_NAME = "ksu";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_OTP = "IsOtp";
    private static final String IS_RESEND = "IsResend";
    private static final String TOKEN = "Token";

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(Login login){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID ,String.valueOf(login.getId()));
        editor.putString(KEY_NAME,login.getName());
        editor.putString(KEY_EMAIL,login.getEmail());
        editor.putString(KEY_CREATED,login.getCreatedAt());
        editor.putString(KEY_UPDATED,login.getUpdatedAt());
        editor.putString(KEY_KETERANGAN,login.getKeterangan());
        editor.putString(KEY_IMG,login.getImg());
        editor.putString(KEY_NPWP,login.getNpwp());
        editor.putString(KEY_ALAMAT,login.getAlamat());
        editor.putString(KEY_TELEPON,login.getTelepon());
        editor.putString(KEY_DAERAH,login.getAddress().getNama());
        editor.putString(KEY_DAERAH_ID,String.valueOf(login.getDaerahId()));
        editor.putString(KEY_TOKEN,login.getApiToken());
        if (login.getAgen() != null){
            editor.putString(KEY_AGEN_ID,String.valueOf(login.getAgen().getIDAGEN()));
            editor.putString(KEY_AGEN_NAMA,login.getAgen().getNAMAAGEN());
            editor.putString(KEY_AGEN_ALAMAT,login.getAgen().getALAMAT());
            editor.putString(KEY_AGEN_KOTA,login.getAgen().getKOTA());
        }
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    public void createOtpSession(Boolean otp){
        editor.putBoolean(IS_OTP, otp);
        editor.commit();
    }

    public void createResend(Boolean resend){
        editor.putBoolean(IS_RESEND,resend);
        editor.commit();
    }


    public HashMap<String, String> getLogin(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ID, pref.getString(KEY_ID,null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,null));
        user.put(KEY_CREATED, pref.getString(KEY_CREATED,null));
        user.put(KEY_UPDATED, pref.getString(KEY_UPDATED,null));
        user.put(KEY_KETERANGAN, pref.getString(KEY_KETERANGAN,null));
        user.put(KEY_IMG, pref.getString(KEY_IMG,null));
        user.put(KEY_NPWP, pref.getString(KEY_NPWP,null));
        user.put(KEY_ALAMAT, pref.getString(KEY_ALAMAT,null));
        user.put(KEY_TELEPON, pref.getString(KEY_TELEPON,null));
        user.put(KEY_DAERAH, pref.getString(KEY_DAERAH,null));
        user.put(KEY_DAERAH_ID,pref.getString(KEY_DAERAH_ID,null));
        user.put(KEY_TOKEN,pref.getString(KEY_TOKEN,null));
        user.put(KEY_AGEN_ID,pref.getString(KEY_AGEN_ID,null));
        user.put(KEY_AGEN_ALAMAT,pref.getString(KEY_AGEN_ALAMAT,null));
        user.put(KEY_AGEN_NAMA,pref.getString(KEY_AGEN_NAMA,null));
        user.put(KEY_AGEN_KOTA,pref.getString(KEY_AGEN_KOTA,null));
        return user;
    }

//    public HashMap<String, Integer> get_id(){
//        HashMap<String, Integer> id = new HashMap<>();
//        id.put(KEY_ID, pref.getInt(KEY_ID,0));
//
//        return id;
//    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
//        Intent i = new Intent(_context, LoginActivity.class);
//        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//        new AsyncTask<Void,Void,Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                try
//                {
//                    FirebaseInstanceId.getInstance().deleteInstanceId();
//                } catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void result) {
//                // Call your Activity where you want to land after log out
//            }
//        }.execute();

        Intent Jump_to_login = new Intent(_context, LoginActivity.class);
//        Jump_to_login.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Jump_to_login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        _context.startActivity(Jump_to_login);
        ((Activity) _context).finish();

        // Staring Login Activity
//        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isOTP(){
        return pref.getBoolean(IS_OTP,false);
    }

    public boolean isResend(){
        return pref.getBoolean(IS_RESEND,false);
    }
}
