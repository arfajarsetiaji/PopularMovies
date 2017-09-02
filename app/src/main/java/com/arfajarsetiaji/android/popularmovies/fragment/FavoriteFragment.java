package com.arfajarsetiaji.android.popularmovies.fragment;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arfajarsetiaji.android.popularmovies.MainApplication;
import com.arfajarsetiaji.android.popularmovies.R;
import com.arfajarsetiaji.android.popularmovies.adapter.FavoriteAdapter;
import com.arfajarsetiaji.android.popularmovies.data.MoviesContract;
import com.arfajarsetiaji.android.popularmovies.model.Movie;
import com.arfajarsetiaji.android.popularmovies.network.NetworkHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String TAG = "FavoriteFragment";
    private static final String KEY_FAVORITE_LAYOUT_TOP_VIEW = "FAVORITE_LAYOUT_TOP_VIEW";
    private static final String KEY_FAVORITE_LAYOUT_POSITION_INDEX = "FAVORITE_LAYOUT_POSITION_INDEX";
    private static final int ID_FILM_LOADER = 100;

    HashSet<Movie> mMovieHashSet;
    List<Movie> mMovieList;
    FavoriteAdapter mFavoriteAdapter;
    GridLayoutManager mGridLayoutManager;
    RecyclerView mRecyclerView;
    ConstraintLayout mConstraintLayout;
    private int mFavoriteLayoutTopView, mFavoriteLayoutPositionIndex;

    public FavoriteFragment() {
        Log.d(TAG, "FavoriteFragment: Called.");
    }

    private void setupRecyclerViewLayout() {
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        Log.d(TAG, "setupRecyclerViewLayout: Called.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.favorite_fragment, container, false);
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        Log.d(TAG, "onCreateView: Called.");
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mConstraintLayout = (ConstraintLayout) view.findViewById(R.id.constraint_layout);
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: Called.");
    }

    @Override
    public void onResume() {
        getActivity().getSupportLoaderManager().initLoader(ID_FILM_LOADER, null, this);
        try {
            if (mFavoriteLayoutPositionIndex != -1) {
                mGridLayoutManager.scrollToPositionWithOffset(mFavoriteLayoutPositionIndex, mFavoriteLayoutTopView);
            }
        } catch (Exception ignored){
        }
        super.onResume();
        Log.d(TAG, "onResume: Called.");
    }

    @Override
    public void onPause() {
        try {
            mFavoriteLayoutPositionIndex = mGridLayoutManager.findFirstVisibleItemPosition();
            View startView = mRecyclerView.getChildAt(0);
            mFavoriteLayoutTopView = (startView == null) ? 0 : (startView.getTop() - mRecyclerView.getPaddingTop());

            SharedPreferences.Editor editor = getActivity().getSharedPreferences(MainApplication.getNameMainPreference(), MainApplication.getModeMainPreferencePrivate()).edit();
            editor.putInt(KEY_FAVORITE_LAYOUT_POSITION_INDEX, mFavoriteLayoutPositionIndex);
            editor.putInt(KEY_FAVORITE_LAYOUT_TOP_VIEW, mFavoriteLayoutTopView);
            editor.apply();
        } catch (Exception ignored) {
        }
        super.onPause();
        Log.d(TAG, "onPause: Called.");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case ID_FILM_LOADER:
                Uri filmUri = MoviesContract.MoviesEntry.CONTENT_URI;
                Log.d(TAG, "onCreateLoader: Called.");
                return new CursorLoader(getActivity(), filmUri, null, null, null, null);
            default:
                Log.d(TAG, "onCreateLoader: Called.");
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() != 0) {
            for (int i = 0; i < data.getCount(); i++) {
                data.moveToPosition(i);
                Movie movie = new Movie();
                movie.setMovieId(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID)));
                movie.setOriginalLanguage(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_LANGUAGE)));
                movie.setOriginalTitle(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE)));
                movie.setTitle(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_TITLE)));
                movie.setGenreIds(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_GENRE_IDS)));
                movie.setReleaseDate(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE)));
                movie.setVoteCount(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_VOTE_COUNT)));
                movie.setVoteAverage(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE)));
                movie.setPopularity(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_POPULARITY)));
                movie.setOverview(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_OVERVIEW)));
                movie.setAdult(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ADULT)));
                movie.setVideo(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_VIDEO)));
                movie.setPosterPath(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH)));
                movie.setBackdropPath(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH)));
                movie.setMovieImageUrlPrefix(NetworkHelper.getImageUrlPrefix());
                if (i == 0) {
                    mMovieHashSet = new HashSet<>();
                }
                mMovieHashSet.add(movie);
                if (i == data.getCount() - 1) {
                    mMovieList = new ArrayList<>(mMovieHashSet);
                    Collections.sort(mMovieList);
                    mFavoriteAdapter = new FavoriteAdapter(getActivity(), mMovieList);
                    mRecyclerView.setAdapter(mFavoriteAdapter);
                    setupRecyclerViewLayout();
                    mFavoriteAdapter.notifyDataSetChanged();
                }
            }
        } else {
            mConstraintLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        Log.d(TAG, "onLoadFinished: Called.");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: Called.");
    }
}
