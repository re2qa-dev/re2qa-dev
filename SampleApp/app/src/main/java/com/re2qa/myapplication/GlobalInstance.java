package com.re2qa.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import java.io.UnsupportedEncodingException;
import java.util.UUID;


public class GlobalInstance extends Application {
    private static GlobalInstance instance;
    private String ApplicationCode = "";
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        PackageInfo pInfo;

        try {
            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            ApplicationCode = pInfo.versionName + "." + pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public String GetImeNumberFromMobile() {
        String androidId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        UUID androidId_UUID = null;
        try {
            androidId_UUID = UUID
                    .nameUUIDFromBytes(androidId.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return androidId_UUID.toString();
    }
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static synchronized GlobalInstance getInstance() {
        return instance;
    }

    public void setPreferences(String PREFS_NAME, String text) {
        String PREFS_KEY = PREFS_NAME;
        SharedPreferences.Editor editor;
        SharedPreferences settings = this.getBaseContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(PREFS_KEY, text);
        editor.commit();
    }

    public String getPreferences(String PREFS_NAME) {
        String PREFS_KEY = PREFS_NAME;
        SharedPreferences settings = this.getBaseContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(PREFS_KEY, new String());
    }

    public void clearPreference(String PREFS_NAME) {
        SharedPreferences.Editor editor;
        SharedPreferences settings = this.getBaseContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context c) {
        this.context = c;
    }

    public String getApplicationCode() {
        return ApplicationCode;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
