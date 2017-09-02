package com.arfajarsetiaji.android.popularmovies;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arfajarsetiaji.android.popularmovies.network.NetworkHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainApplication extends Application{
    private static final String TAG = "MainApplication";
    private static final String NAME_MAIN_PREFERENCE = "MAIN_PREFERENCE";
    private static final int MODE_MAIN_PREFERENCE_PRIVATE = 0x0000;
    private static final String KEY_GENRE_INITIALIZED = "GENRE_INITIALIZED";

    private static RequestQueue sRequestQueue;

    public static RequestQueue getRequestQueue() {
        return sRequestQueue;
    }

    public static String getNameMainPreference() {
        return NAME_MAIN_PREFERENCE;
    }

    public static int getModeMainPreferencePrivate() {
        return MODE_MAIN_PREFERENCE_PRIVATE;
    }

    private void initializeApplication() {
        sRequestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d(TAG, "initializeApplication: Called.");
    }

    private void initializeGenre() {
        SharedPreferences mainPreferences = getSharedPreferences(getNameMainPreference(), getModeMainPreferencePrivate());
        if (mainPreferences.getBoolean(KEY_GENRE_INITIALIZED, false)) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NetworkHelper.getGenreIdJsonObjectUrl(), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray arrayResults = response.getJSONArray("genres");
                        for (int i = 0; i < arrayResults.length(); i++) {
                            // Ambil JsonObject dari setiap anggota JsonArray.
                            JSONObject jsonObject = arrayResults.getJSONObject(i);
                            SharedPreferences.Editor editor = getSharedPreferences(getNameMainPreference(), getModeMainPreferencePrivate()).edit();
                            editor.putString("GENRE" + jsonObject.getString("id"), jsonObject.getString("name"));
                            editor.apply();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            getRequestQueue().add(jsonObjectRequest);
        } else{
            SharedPreferences.Editor editor = getSharedPreferences(getNameMainPreference(), getModeMainPreferencePrivate()).edit();
            editor.putString("GENRE28", "Action");
            editor.putString("GENRE12", "Adventure");
            editor.putString("GENRE16", "Animation");
            editor.putString("GENRE35", "Comedy");
            editor.putString("GENRE80", "Crime");
            editor.putString("GENRE99", "Documentary");
            editor.putString("GENRE18", "Drama");
            editor.putString("GENRE10751", "Family");
            editor.putString("GENRE14", "Fantasy");
            editor.putString("GENRE36", "History");
            editor.putString("GENRE27", "Horror");
            editor.putString("GENRE10402", "Music");
            editor.putString("GENRE9648", "Mystery");
            editor.putString("GENRE10749", "Romance");
            editor.putString("GENRE878", "Science Fiction");
            editor.putString("GENRE10770", "TV Movie");
            editor.putString("GENRE53", "Thriller");
            editor.putString("GENRE10752", "War");
            editor.putString("GENRE37", "Western");
            editor.putBoolean(KEY_GENRE_INITIALIZED, true);
            editor.apply();
        }
    }

    private void resetRecyclerViewPosition() {
        SharedPreferences.Editor editor = getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE).edit();
        editor.putInt("mGridLayoutPositionIndexMp", 0);
        editor.putInt("mGridLayoutTopViewMp", 0);
        editor.putInt("mGridLayoutPositionIndexTr", 0);
        editor.putInt("mGridLayoutTopViewTr", 0);
        editor.putInt("mGridLayoutPositionIndexF", 0);
        editor.putInt("mGridLayoutTopViewF", 0);
        editor.apply();
        Log.d(TAG, "resetRecyclerViewPosition: Called.");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplication();
        initializeGenre();
        resetRecyclerViewPosition();
        Log.d(TAG, "onCreate: Called.");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory: Called.");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate: Called.");
    }
}