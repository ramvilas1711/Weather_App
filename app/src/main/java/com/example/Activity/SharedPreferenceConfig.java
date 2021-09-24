package com.example.Activity;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceConfig {

    String mobileNumber,fullName,Gender,DOB,Address1,Address2,pincode;
    Boolean saveValue = false;

    public static final String Shered_Pref = "com.example.Activity";

    public SharedPreferenceConfig(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Shered_Pref, Context.MODE_PRIVATE);
        mobileNumber=sharedPreferences.getString("mobileNumber","");
        fullName=sharedPreferences.getString("fullName","");
        Gender=sharedPreferences.getString("gender","");
        Address1 = sharedPreferences.getString("address1","");
        Address2 = sharedPreferences.getString("address2","");
        pincode = sharedPreferences.getString("pincode","");
        DOB = sharedPreferences.getString("dob","");
        saveValue = sharedPreferences.getBoolean("loginBooleanValue",false);

    }

    public void update(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Shered_Pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobileNumber", mobileNumber);
        editor.putString("fullName", fullName);
        editor.putString("gender", Gender);
        editor.putString("address1",Address1);
        editor.putString("Address2",Address2);
        editor.putString("pincode",pincode);
        editor.putBoolean("loginBooleanValue",saveValue);
        editor.apply();
    }
}
