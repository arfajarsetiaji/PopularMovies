package com.arfajarsetiaji.android.popularmovies.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.arfajarsetiaji.android.popularmovies.MainApplication;
import com.arfajarsetiaji.android.popularmovies.R;
import com.arfajarsetiaji.android.popularmovies.adapter.MovieGridAdapter;
import com.arfajarsetiaji.android.popularmovies.model.Movie;
import com.arfajarsetiaji.android.popularmovies.network.NetworkHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MostPopularFragment extends Fragment {
    private static final String TAG = "MostPopularFragment";

    List<Movie> mMovies;
    MovieGridAdapter mMovieGridAdapter;
    GridLayoutManager mGridLayoutManager;
    RecyclerView mRecyclerView;
    private int mGridLayoutPositionIndexMp;
    private int mGridLayoutTopViewMp;
    private JsonObjectRequest mJsonObjectRequest;

    /**
     * Default constructor MostPopularFragment class.
     */

    public MostPopularFragment() {
        // Required empty public constructor
    }

    /**
     * Inisialisasi awal MostPopularFragment.
     */

    private void initialization(LayoutInflater inflater, ViewGroup container) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        mMovies = new ArrayList<>();
        mMovieGridAdapter = new MovieGridAdapter(getActivity(), mMovies);

        // JsonObjectRequest video dari MostPopularMovie.
        mJsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, NetworkHelper.getMostPopularMovieJsonObjectUrl(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Ambil JsonArray dengan key "results".
                            JSONArray arrayResults = response.getJSONArray("results");
                            for (int i = 0; i < arrayResults.length() ; i++) {
                                // Buat JsonObject dari setiap anggota JsonArray.
                                JSONObject jsonObject = arrayResults.getJSONObject(i);

                                // Buat object Movie dari setiap data video yang diambil dari JsonObject.
                                Movie movie = new Movie();
                                movie.setMovieId(jsonObject.getString("id"));
                                movie.setOriginalLanguage(jsonObject.getString("original_language"));
                                movie.setOriginalTitle(jsonObject.getString("original_title"));
                                movie.setTitle(jsonObject.getString("title"));
                                movie.setGenreIds(jsonObject.getString("genre_ids"));
                                movie.setReleaseDate(jsonObject.getString("release_date"));
                                movie.setVoteCount(jsonObject.getString("vote_count"));
                                movie.setVoteAverage(jsonObject.getString("vote_average"));
                                movie.setPopularity(jsonObject.getString("popularity"));
                                movie.setOverview(jsonObject.getString("overview"));
                                movie.setAdult(jsonObject.getString("adult"));
                                movie.setVideo(jsonObject.getString("video"));
                                movie.setPosterPath(jsonObject.getString("poster_path"));
                                movie.setBackdropPath(jsonObject.getString("backdrop_path"));
                                movie.setMovieImageUrlPrefix(NetworkHelper.getImageUrlPrefix());

                                // Isi List<Movie> dengan data dari JsonObject.
                                mMovies.add(movie);
                                if (i == arrayResults.length() - 1) {
                                    // Notify RecyclerView.Adapter saat loop terakhir berjalan.
                                    mMovieGridAdapter.notifyDataSetChanged();
                                }
                                Log.d(TAG, "onResponse: new movie (" + movie.getOriginalTitle() + ") added to movies");
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

        mRecyclerView.setAdapter(mMovieGridAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    /**
     * Fungsi untuk setup RecyclerView menggunakan GridLayoutManager.
     * */

    private void setupRecyclerViewLayout() {
        // Buat RecyclerView menjadi 2 kolom per baris jika orientasi portrait
        // 4 kolom per baris jika orientasi landscape.
        // TODO: 30-Aug-17 Ada bug, hasil berbeda tergantung day mode / night mode
        /*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGridLayoutManager = new GridLayoutManager(getActivity(), 4);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
        }*/
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    /**
     * Fungsi untuk request ulang data JsonObject dari server.
     * */

    private void refreshMovies() {
        RequestQueue requestQueue = MainApplication.getRequestQueue();
        requestQueue.add(mJsonObjectRequest);
    }

    /**
     * Override fungsi - fungsi Activity lifecycle & Fragment lifecycle
     * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initialization(inflater, container);
        refreshMovies();
        setupRecyclerViewLayout();

        SharedPreferences mainPreferences = getActivity().getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE);
        mGridLayoutPositionIndexMp = mainPreferences.getInt("mGridLayoutPositionIndexMp", 0);
        mGridLayoutTopViewMp = mainPreferences.getInt("mGridLayoutTopViewMp", 0);

        Log.d(TAG, "onCreateView: Called");
        return mRecyclerView;
    }

    @Override
    public void onResume() {
        if (mGridLayoutPositionIndexMp != -1) {
            mGridLayoutManager.scrollToPositionWithOffset(mGridLayoutPositionIndexMp, mGridLayoutTopViewMp);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        mGridLayoutPositionIndexMp = mGridLayoutManager.findFirstVisibleItemPosition();
        View startView = mRecyclerView.getChildAt(0);
        mGridLayoutTopViewMp = (startView == null) ? 0 : (startView.getTop() - mRecyclerView.getPaddingTop());

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE).edit();
        editor.putInt("mGridLayoutPositionIndexMp", mGridLayoutPositionIndexMp);
        editor.putInt("mGridLayoutTopViewMp", mGridLayoutTopViewMp);
        editor.apply();

        super.onPause();
        Log.d(TAG, "onPause: called");
    }
}
