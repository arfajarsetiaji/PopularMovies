package com.arfaststudio.prototype.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sackcentury.shinebuttonlib.ShineButton;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private ShineButton mBtnfavorite;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sentIntent = new Intent(DetailActivity.this, SettingsActivity.class);
                startActivity(sentIntent);

            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView ivposter = (ImageView)findViewById(R.id.iv_detail_image);
        TextView tvRating = (TextView)findViewById(R.id.tv_rating);
        TextView tvReleaseDate = (TextView)findViewById(R.id.tv_release_date);
        TextView tvOverview = (TextView)findViewById(R.id.tv_overview);

        // Terima data.
        final String judul = getIntent().getStringExtra("JUDUL");
        final String poster = getIntent().getStringExtra("POSTER");
        final String rating = getIntent().getStringExtra("RATING");
        final String releaseDate = getIntent().getStringExtra("RELEASE_DATE");
        final String overview = getIntent().getStringExtra("OVERVIEW");

        Log.d(TAG, "onCreate: Judul = " + judul);
        Log.d(TAG, "onCreate: Poster = " + poster);
        Log.d(TAG, "onCreate: Rating = " + rating);
        Log.d(TAG, "onCreate: Release Date = " + releaseDate);
        Log.d(TAG, "onCreate: Overview = " + overview);

        getSupportActionBar().setTitle(judul);
        Glide.with(DetailActivity.this).load(poster).into(ivposter);
        tvRating.setText(rating);
        tvReleaseDate.setText(releaseDate);
        tvOverview.setText(overview);

        mBtnfavorite = (ShineButton) findViewById(R.id.btn_favorit);
        mBtnfavorite.init(DetailActivity.this);
        mBtnfavorite.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if(checked) {
                    // Simpan favorit.
                    mEditor = mSharedPreferences.edit();
                    mEditor.putBoolean("FAVORITE" + " " + judul, true);
                    mEditor.commit();
                } else {
                    // Hapus dari favorit.
                    mEditor = mSharedPreferences.edit();
                    mEditor.putBoolean("FAVORITE" + " " + judul, false);
                    mEditor.commit();
                }
            }
        });

        mSharedPreferences = getApplicationContext().getSharedPreferences("SETTING", 0);
        Boolean fav = mSharedPreferences.getBoolean("FAVORITE" + " " + judul, false);
        if (fav) {
            mBtnfavorite.setChecked(true);
        }

    }
}