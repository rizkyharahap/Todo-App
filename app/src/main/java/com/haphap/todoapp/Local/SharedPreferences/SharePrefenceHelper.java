package com.haphap.todoapp.Local.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharePrefenceHelper {

    private static final String ID_KEY = "com.haphap.todoapp.Database_ID_KEY";
    private static SharePrefenceHelper INSTANCE;
    private SharedPreferences sharedPreferences;

    private SharePrefenceHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static synchronized SharePrefenceHelper getINSTANCE(Context context){
        if (INSTANCE == null){
            INSTANCE = new SharePrefenceHelper(PreferenceManager.getDefaultSharedPreferences(context));
        }
        return INSTANCE;
    }

    public void  setLoggedUserId(Long id) {
        sharedPreferences.edit().putLong(ID_KEY,id).apply();
    }

    public Long getUserId() {
        return sharedPreferences.getLong(ID_KEY, -1);
    }
}
