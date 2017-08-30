package com.arfajarsetiaji.android.popularmovies;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arfajarsetiaji.android.popularmovies.network.NetworkHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ar Fajar Setiaji on 29-Aug-17.
 **/

/**
 * Register MainApplication di AndroidManifest menggunakan @param android:name.
 */

public class MainApplication extends Application{
    private static final String TAG = "MainApplication";

    private static RequestQueue sRequestQueue;
    private static RequestManager sRequestManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sRequestQueue = Volley.newRequestQueue(getApplicationContext());
        sRequestManager = Glide.with(getApplicationContext());

        resetRecyclerViewPosition();

        initializeGenre();
    }

    private void resetRecyclerViewPosition() {
        SharedPreferences.Editor editor = getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE).edit();
        editor.putInt("mGridLayoutPositionIndexMp", 0);
        editor.putInt("mGridLayoutTopViewMp", 0);
        editor.putInt("mGridLayoutPositionIndexTr", 0);
        editor.putInt("mGridLayoutTopViewTr", 0);
        editor.apply();
    }

    /**
     * Set genre berdasarkan genre Id.
     */

    private void initializeGenre() {
        SharedPreferences mainPreferences = getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE);
        if (mainPreferences.getBoolean("DefaultGenreHasInitialized", false)) {
            // Genre sudah pernah diinisialisasi sebelumnya.
            // Mengambil JsonObject GenreId dari server, jika ada perubahan.
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NetworkHelper.getGenreIdJsonObjectUrl(), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        // Ambil JsonArray dengan key "genres" dari response.
                        JSONArray arrayResults = response.getJSONArray("genres");
                        for (int i = 0; i < arrayResults.length(); i++) {
                            // Ambil JsonObject dari setiap anggota JsonArray.
                            JSONObject jsonObject = arrayResults.getJSONObject(i);
                            // Simpan data genre di SharedPreferences.
                            SharedPreferences.Editor editor = getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE).edit();
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
            // Inisialisasi value awal genre.
            SharedPreferences.Editor editor = getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE).edit();
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
            editor.apply();
            // Tandai bahwa genre sudah pernah diinisialisasi.
            editor.putBoolean("DefaultGenreHasInitialized", true);
            editor.apply();
        }
    }

    /**
     * Getter dari static Volley.RequestQueue & static Glide.RequestManager.
     * */

    public static RequestQueue getRequestQueue() {
        return sRequestQueue;
    }

    public static RequestManager getRequestManager() {
        return sRequestManager;
    }
}