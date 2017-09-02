package com.arfajarsetiaji.android.popularmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.arfajarsetiaji.android.popularmovies.fragment.FavoriteFragment;
import com.arfajarsetiaji.android.popularmovies.fragment.MostPopularFragment;
import com.arfajarsetiaji.android.popularmovies.fragment.TopRatedFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MainActivity";
    private static final String KEY_DISPLAYED_FRAGMENT_ID = "SELECTED_FRAGMENT";

    FrameLayout mFrameLayout;
    BottomNavigationView mBottomNavigationView;
    Fragment mDisplayedFragment;

    int mDisplayedFragmentId;

    private void initializeActivity(Bundle savedInstanceState ) {
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);

        setSupportActionBar(toolbar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);
        if (savedInstanceState != null) {
            switch (savedInstanceState.getInt(KEY_DISPLAYED_FRAGMENT_ID)) {
                case 0:
                    View mostPopularFragment = mBottomNavigationView.findViewById(R.id.action_top_rated_movies);
                    mostPopularFragment.performClick();
                    break;
                case 1:
                    View topRatedFragment = mBottomNavigationView.findViewById(R.id.action_favorite_movies);
                    topRatedFragment.performClick();
                    break;
                case 2:
                    View favoriteFaragment = mBottomNavigationView.findViewById(R.id.action_most_popular_movies);
                    favoriteFaragment.performClick();
                    break;
            }
        }
        Log.d(TAG, "initializeActivity: Called.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(savedInstanceState);
        Log.d(TAG, "onCreate: Called.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Called.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Called.");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: Called.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Called.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Called.");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_DISPLAYED_FRAGMENT_ID, mDisplayedFragmentId);
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: Called.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Called.");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: Called.");
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_most_popular_movies:
                mDisplayedFragmentId = 0;
                mDisplayedFragment = new MostPopularFragment();
                Log.d(TAG, "onNavigationItemSelected: Most popular movies selected.");
                break;
            case R.id.action_top_rated_movies:
                mDisplayedFragmentId = 1;
                mDisplayedFragment = new TopRatedFragment();
                Log.d(TAG, "onNavigationItemSelected: Top rated movies selected.");
                break;
            case R.id.action_favorite_movies:
                mDisplayedFragmentId = 2;
                mDisplayedFragment = new FavoriteFragment();
                Log.d(TAG, "onNavigationItemSelected: Favorite movies selected.");
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(mFrameLayout.getId(), mDisplayedFragment);
        transaction.commit();
        Log.d(TAG, "onNavigationItemSelected: Called.");
        return true;
    }
}
