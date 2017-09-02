package com.arfajarsetiaji.android.popularmovies.system;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SystemHelper {
    private static final String TAG = "SystemHelper";

    public static boolean youtubeInstalled(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
        if (intent != null) {
            Log.d(TAG, "youtubeInstalled: True.");
            return true;
        } else {
            Log.d(TAG, "youtubeInstalled: False");
            return false;
        }
    }
}
