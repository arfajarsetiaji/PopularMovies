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
    private static final String KEY_TOP_RATED_LAYOUT_TOP_VIEW = "KEY_TOP_RATED_LAYOUT_TOP_VIEW";
    private static final String KEY_TOP_RATED_LAYOUT_POSITION_INDEX = "KEY_TOP_RATED_LAYOUT_POSITION_INDEX";

    List<Movie> mMovies;
    MovieGridAdapter mMovieGridAdapter;
    GridLayoutManager mGridLayoutManager;
    RecyclerView mRecyclerView;
    private int mTopRatedLayoutTopView, mTopRatedLayoutPositionIndexTr;
    private JsonObjectRequest mJsonObjectRequest;

    public TopRatedFragment() {
        Log.d(TAG, "TopRatedFragment: Called.");
    }

    private void initializeFragment(LayoutInflater inflater, ViewGroup container) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.main_recycler_view, container, false);
        mMovies = new ArrayList<>();
        mMovieGridAdapter = new MovieGridAdapter(getActivity(), mMovies);
        mJsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, NetworkHelper.getTopRatedMovieJsonObjectUrl(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray arrayResults = response.getJSONArray("results");
                            for (int i = 0; i < arrayResults.length() ; i++) {
                                // Buat JsonObject dari setiap anggota JsonArray.
                                JSONObject jsonObject = arrayResults.getJSONObject(i);
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
                                mMovies.add(movie);
                                if (i == arrayResults.length() - 1) {
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
        Log.d(TAG, "initializeFragment: Called.");
    }

    private void setupRecyclerViewLayout() {
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        Log.d(TAG, "setupRecyclerViewLayout: Called.");
    }

    private void refreshMovies() {
        RequestQueue requestQueue = MainApplication.getRequestQueue();
        requestQueue.add(mJsonObjectRequest);
        Log.d(TAG, "refreshMovies: Called.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeFragment(inflater, container);
        refreshMovies();
        setupRecyclerViewLayout();
        SharedPreferences mainPreferences = getActivity().getSharedPreferences(MainApplication.getNameMainPreference(), MainApplication.getModeMainPreferencePrivate());
        mTopRatedLayoutTopView = mainPreferences.getInt(KEY_TOP_RATED_LAYOUT_TOP_VIEW, 0);
        mTopRatedLayoutPositionIndexTr = mainPreferences.getInt(KEY_TOP_RATED_LAYOUT_POSITION_INDEX, 0);
        Log.d(TAG, "onCreateView: Called.");
        return mRecyclerView;
    }

    @Override
    public void onResume() {
        if (mTopRatedLayoutPositionIndexTr != -1) {
            mGridLayoutManager.scrollToPositionWithOffset(mTopRatedLayoutPositionIndexTr, mTopRatedLayoutTopView);
        }
        super.onResume();
        Log.d(TAG, "onResume: Called.");
    }

    @Override
    public void onPause() {
        mTopRatedLayoutPositionIndexTr = mGridLayoutManager.findFirstVisibleItemPosition();
        View startView = mRecyclerView.getChildAt(0);
        mTopRatedLayoutTopView = (startView == null) ? 0 : (startView.getTop() - mRecyclerView.getPaddingTop());
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE).edit();
        editor.putInt(KEY_TOP_RATED_LAYOUT_TOP_VIEW, mTopRatedLayoutTopView);
        editor.putInt(KEY_TOP_RATED_LAYOUT_POSITION_INDEX, mTopRatedLayoutPositionIndexTr);
        editor.apply();
        super.onPause();
        Log.d(TAG, "onPause: Called.");
    }
}
