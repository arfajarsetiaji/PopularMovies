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

/**
 * Activity utama yang berisi MostPopularFragment, TopRatedFragment, Favorite Fragment + BottomNavigationView.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MainActivity";

    FrameLayout mFrameLayout;
    BottomNavigationView mBottomNavigationView;
    Fragment mSelectedFragment;

    int mSelectedPage;

    /**
     * Inisialisasi awal Activity.
     */

    private void initialization() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        mSelectedFragment = null;

        mBottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);
    }

    /**
     * Fungsi untuk menentukan Fragment yang pertama tampil saat activity dijalankan,
     * jika MainActivity direstart oleh system.
     * */

    private void setDefaultPage(Bundle savedInstanceState) {
        // Ambil posisi terakhir fragment yang ditampilkan dari savedInstanceState,
        // lalu tampilkan fragment tersebut.
        if (savedInstanceState != null) {
            mSelectedPage = savedInstanceState.getInt("SELECTED_PAGE");
        }
        if (mSelectedPage == 1) {
            View view = mBottomNavigationView.findViewById(R.id.action_top_rated_movies);
            view.performClick();
        } else if (mSelectedPage == 2) {
            View view = mBottomNavigationView.findViewById(R.id.action_favorite_movies);
            view.performClick();
        } else {
            View view = mBottomNavigationView.findViewById(R.id.action_most_popular_movies);
            view.performClick();
        }
    }

    /**
     * Override fungsi- fungsi Activity lifecycle.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
        setDefaultPage(savedInstanceState);
        Log.d(TAG, "onCreate: Called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: Called");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: Called");
        // Simpan posisi fragment yang terakhir ditampilkan di outstate,
        // jika MainActivity dimatikan oleh system.
        outState.putInt("SELECTED_PAGE", mSelectedPage);
        super.onSaveInstanceState(outState);
    }

    /**
     * Override fungsi - fungsi callback.
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Ganti fragment yang ditampilkan pada MainActivity berdasarkan item BottomNavigationView yang dipilih.
        switch (item.getItemId()) {
            case R.id.action_most_popular_movies:
                Log.d(TAG, "onNavigationItemSelected: item 1 clicked");
                mSelectedPage = 0;
                mSelectedFragment = new MostPopularFragment();
                break;
            case R.id.action_top_rated_movies:
                Log.d(TAG, "onNavigationItemSelected: item 2 clicked");
                mSelectedPage = 1;
                mSelectedFragment = new TopRatedFragment();
                break;
            case R.id.action_favorite_movies:
                Log.d(TAG, "onNavigationItemSelected: item 3 clicked");
                mSelectedPage = 2;
                mSelectedFragment = new FavoriteFragment();
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(mFrameLayout.getId(), mSelectedFragment);
        transaction.commit();
        return true;
    }
}
