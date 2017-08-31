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

public class TopRatedFragment extends Fragment {
    private static final String TAG = "TopRatedFragment";

    List<Movie> mMovies;
    MovieGridAdapter mMovieGridAdapter;
    GridLayoutManager mGridLayoutManager;
    RecyclerView mRecyclerView;
    private int mGridLayoutPositionIndexTr;
    private int mGridLayoutTopViewTr;
    private JsonObjectRequest mJsonObjectRequest;

    /**
     * Default constructor TopRatedFragment class.
     */

    public TopRatedFragment() {
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
                new JsonObjectRequest(Request.Method.GET, NetworkHelper.getTopRatedMovieJsonObjectUrl(), null, new Response.Listener<JSONObject>() {
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
        mGridLayoutPositionIndexTr = mainPreferences.getInt("mGridLayoutPositionIndexTr", 0);
        mGridLayoutTopViewTr = mainPreferences.getInt("mGridLayoutTopViewTr", 0);

        Log.d(TAG, "onCreateView: Called");
        return mRecyclerView;
    }

    @Override
    public void onResume() {
        if (mGridLayoutPositionIndexTr != -1) {
            mGridLayoutManager.scrollToPositionWithOffset(mGridLayoutPositionIndexTr, mGridLayoutTopViewTr);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        mGridLayoutPositionIndexTr = mGridLayoutManager.findFirstVisibleItemPosition();
        View startView = mRecyclerView.getChildAt(0);
        mGridLayoutTopViewTr = (startView == null) ? 0 : (startView.getTop() - mRecyclerView.getPaddingTop());

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE).edit();
        editor.putInt("mGridLayoutPositionIndexTr", mGridLayoutPositionIndexTr);
        editor.putInt("mGridLayoutTopViewTr", mGridLayoutTopViewTr);
        editor.apply();

        super.onPause();
        Log.d(TAG, "onPause: called");
    }
}
