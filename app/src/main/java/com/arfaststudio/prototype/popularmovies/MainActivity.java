package com.arfaststudio.prototype.popularmovies;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arfaststudio.prototype.popularmovies.adapter.MovieAdapter;
import com.arfaststudio.prototype.popularmovies.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    RecyclerView mRecyclerView;
    ArrayList<MovieModel> listMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dataset
        // 1. Buat model data (MovieModel.java)
        // 2. Arraylist MovieModel
        listMovie = new ArrayList<>();

        // Data dummy
       /* for (int i=0; i<20; i++){
            MovieModel movie1 = new MovieModel();
            movie1.setJudul("Minion");
            movie1.setPoster("http://4.bp.blogspot.com/-C2mOS0RSo4A/VpMM15jHevI/AAAAAAAAJ1I/w-fsNlOZvYc/s1600/poster%2Bfilm%2Bterbaik%2Bsicario%2B-%2Bnamafilm.jpg");
            listMovie.add(movie1);

            MovieModel movie2 = new MovieModel();
            movie2.setJudul("Minion");
            movie2.setPoster("http://4.bp.blogspot.com/-C2mOS0RSo4A/VpMM15jHevI/AAAAAAAAJ1I/w-fsNlOZvYc/s1600/poster%2Bfilm%2Bterbaik%2Bsicario%2B-%2Bnamafilm.jpg");
            listMovie.add(movie2);

        }*/
        /*// 3. set data ke MovieModel
        MovieModel movie1 = new MovieModel();
        movie1.setJudul("Minion");
        movie1.setPoster("http://4.bp.blogspot.com/-C2mOS0RSo4A/VpMM15jHevI/AAAAAAAAJ1I/w-fsNlOZvYc/s1600/poster%2Bfilm%2Bterbaik%2Bsicario%2B-%2Bnamafilm.jpg");
        // 4. Tambahkan model ke Arraylist
        listMovie.add(movie1);*/

        // Data online
        getDataOnline();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        MovieAdapter adapter = new MovieAdapter(MainActivity.this, listMovie);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));


    }

    //  Using Volley
    private void getDataOnline() {
        // final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Loading", "Mohon Bersabar");
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=bccd5b3bc9c3658bab941eeb0e1be99c&language=en-US&page=1";

        JsonObjectRequest ambilData = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // loading.hide();
                try {
                    JSONArray arrayResults = response.getJSONArray("results");
                    for (int i = 0; i < arrayResults.length() ; i++) {
                        JSONObject jsonObject = arrayResults.getJSONObject(i);
                        Log.d(TAG, "onResponse: Hasil json" + jsonObject);

                        MovieModel movie = new MovieModel();
                        movie.setJudul(jsonObject.getString("title"));
                        movie.setPoster(jsonObject.getString("poster_path"));
                        movie.setRating(jsonObject.getString("vote_average"));
                        movie.setReleaseDate(jsonObject.getString("release_date"));
                        movie.setOverview(jsonObject.getString("overview"));
                        listMovie.add(movie);

                        // Adapter
                        // 1. Buat adapter (MovieAdapter.java)
                        MovieAdapter adapter = new MovieAdapter(MainActivity.this, listMovie);
                        mRecyclerView.setAdapter(adapter);

                        // LayoutManager
                        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue antrian = Volley.newRequestQueue(this);
        antrian.add(ambilData);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
