package com.arfajarsetiaji.android.popularmovies.system;

/**
 * Created by Ar Fajar Setiaji on 29-Aug-17.
 */

import android.content.Context;
import android.content.Intent;

/**
 * Helper class untuk mempermudah saat berhubungan dengan system.
 */

public class SystemHelper {

    /**
     * Fungsi untuk memeriksa apakah aplikasi Youtube terinstal di device.
     */
    public static boolean youtubeInstalled(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
        if (intent != null) {
            return true;
        } else {
            return false;
        }
    }

}
