package com.arfajarsetiaji.android.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arfajarsetiaji.android.popularmovies.data.MoviesContract;
import com.arfajarsetiaji.android.popularmovies.model.Movie;
import com.arfajarsetiaji.android.popularmovies.model.MovieReview;
import com.arfajarsetiaji.android.popularmovies.model.MovieVideo;
import com.arfajarsetiaji.android.popularmovies.network.NetworkHelper;
import com.arfajarsetiaji.android.popularmovies.system.SystemHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DetailActivity";

    LinearLayout mLlPlaceholderVideos, mLlPlaceholderReview;
    FloatingActionButton mFabFavorite;
    ImageView mIvPlaceholderBackdrop, mIvPlaceholderPoster;
    TextView mTvPlaceholderBackdrop, mTvPlaceholderOriginalTitle, mTvPlaceholderReleaseDate,
            mTvPlaceholderGenre, mTvPlaceholderRating, mTvPlaceholderPopularity, mTvPlaceholderOverview,
            mTvPlaceholderPoster, mTvPlaceholderVideos, mTvPlaceholderReviews, mTvPlaceholderReview;

    List<MovieVideo> mMovieVideos;
    List<MovieReview> mMovieReviews;
    Movie mMovie;
    JsonObjectRequest mMovieVideosJsonObjectRequest, mMovieReviewsJsonObjectRequest;

    /**
     * Inisialisasi awal activity.
     */
    
    private void initialization() {
        Bundle bundle = getIntent().getExtras();
        mMovie = bundle.getParcelable("MOVIE_DETAILS");

        // JsonObjectRequest video dari Movie yang dipilih.
        mMovieVideosJsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, NetworkHelper.getMovieVideosJsonObjectUrl(mMovie.getMovieId()), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Ambil JsonArray dengan key "results".
                            JSONArray arrayResults = response.getJSONArray("results");
                            for (int i = 0; i < arrayResults.length(); i++) {
                                // Buat JsonObject dari setiap anggota JsonArray.
                                JSONObject jsonObject = arrayResults.getJSONObject(i);

                                // Buat object MovieVideo dari setiap data video yang diambil dari JsonObject.
                                MovieVideo movieVideo = new MovieVideo();
                                movieVideo.setId(jsonObject.getString("id"));
                                movieVideo.setIso_639_1(jsonObject.getString("iso_639_1"));
                                movieVideo.setIso_3166_1(jsonObject.getString("iso_3166_1"));
                                movieVideo.setKey(jsonObject.getString("key"));
                                movieVideo.setName(jsonObject.getString("name"));
                                movieVideo.setSite(jsonObject.getString("site"));
                                movieVideo.setSize(jsonObject.getString("size"));
                                movieVideo.setType(jsonObject.getString("type"));

                                // Isi List<MovieVideo> dengan data dari JsonObject.
                                mMovieVideos.add(movieVideo);
                                setupVideoLinks();
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

        // JsonObjectRequest review dari Movie yang dipilih.
        mMovieReviewsJsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, NetworkHelper.getMovieReviewsJsonObjectUrl(mMovie.getMovieId()), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Ambil JsonArray dengan key "results".
                            JSONArray arrayResults = response.getJSONArray("results");
                            for (int i = 0; i < arrayResults.length(); i++) {
                                // Buat JsonObject dari setiap anggota JsonArray.
                                JSONObject jsonObject = arrayResults.getJSONObject(i);

                                // Buat object MovieReview dari setiap data video yang diambil dari JsonObject.
                                MovieReview movieReview = new MovieReview();
                                movieReview.setId(jsonObject.getString("id"));
                                movieReview.setAuthor(jsonObject.getString("author"));
                                movieReview.setContent(jsonObject.getString("content"));
                                movieReview.setUrl(jsonObject.getString("url"));

                                // Isi List<MovieReview> dengan semua data dari JsonObject.
                                mMovieReviews.add(movieReview);
                                setupReviews();
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


        RequestQueue requestQueue = Volley.newRequestQueue(DetailActivity.this);
        // Request untuk mengambil JsonObject daftar video yang tersedia untuk Movie yang dipilih.
        requestQueue.add(mMovieVideosJsonObjectRequest);
        // Request untuk mengambil JsonObject daftar review yang tersedia untuk Movie yang dipilih.
        requestQueue.add(mMovieReviewsJsonObjectRequest);
        
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mMovie.getTitle());

        mFabFavorite = (FloatingActionButton) findViewById(R.id.fab);
        mTvPlaceholderBackdrop = (TextView) findViewById(R.id.tv_item_backdrop);
        mIvPlaceholderBackdrop = (ImageView) findViewById(R.id.iv_item_backdrop);
        mTvPlaceholderOriginalTitle = (TextView) findViewById(R.id.tv_placeholder_original_title);
        mTvPlaceholderReleaseDate = (TextView) findViewById(R.id.tv_placeholder_release_date);
        mTvPlaceholderGenre = (TextView) findViewById(R.id.tv_placeholder_genre);
        mTvPlaceholderRating = (TextView) findViewById(R.id.tv_placeholder_rating);
        mTvPlaceholderPopularity = (TextView) findViewById(R.id.tv_placeholder_popularity);
        mTvPlaceholderOverview = (TextView) findViewById(R.id.tv_placeholder_overview);
        mTvPlaceholderPoster = (TextView) findViewById(R.id.tv_placeholder_poster);
        mIvPlaceholderPoster = (ImageView) findViewById(R.id.iv_placeholder_poster);
        mLlPlaceholderVideos = (LinearLayout) findViewById(R.id.ll_placeholder_trailer);
        mTvPlaceholderVideos = (TextView) findViewById(R.id.tv_placeholder_trailer);
        mTvPlaceholderReviews = (TextView) findViewById(R.id.tv_placeholder_reviews);
        mTvPlaceholderReview = (TextView) findViewById(R.id.tv_placeholder_review);
        mLlPlaceholderReview = (LinearLayout) findViewById(R.id.ll_placeholder_review);

        mMovieVideos = new ArrayList<>();
        mMovieReviews = new ArrayList<>();

        mFabFavorite.setOnClickListener(DetailActivity.this);
        String backdropPath = mMovie.getBackdropPath();
        Glide.with(DetailActivity.this).load(backdropPath).dontTransform().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mTvPlaceholderBackdrop.setText(mMovie.getOriginalTitle() + "\n" + "(Failed to load movie image)");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mIvPlaceholderBackdrop.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(mIvPlaceholderBackdrop);

        mTvPlaceholderOriginalTitle.setText(mMovie.getOriginalTitle());
        mTvPlaceholderReleaseDate.setText(mMovie.getReleaseDate());
        mTvPlaceholderGenre.setText(getGenre());
        mTvPlaceholderRating.setText(mMovie.getVoteAverage() + " (" + mMovie.getVoteCount() + ")");
        mTvPlaceholderPopularity.setText(mMovie.getPopularity());
        mTvPlaceholderOverview.setText(mMovie.getOverview());

        String posterPath = mMovie.getPosterPath();
        MainApplication.getRequestManager().load(posterPath).dontTransform().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mTvPlaceholderPoster.setText(mMovie.getOriginalTitle() + "\n" + "(Failed to load movie image)");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mIvPlaceholderPoster.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(mIvPlaceholderPoster);
    }

    /**
     * Fungsi untuk menampilkan daftar video yang tersedia untuk Movie yang dipilih.
     */
    void setupVideoLinks() {
        int totalMovieVideos = mMovieVideos.size();
        if (totalMovieVideos == 0) {
            // Tampilkan pesan saat tidak ada daftar video untuk Movie yang dipilih.
            mTvPlaceholderVideos.setText("No video for this movie.");
        } else {
            // Ubah TextView status menjadi label title, jika ada video untuk Movie yang dipilih.
            mTvPlaceholderVideos.setAllCaps(true);
            mTvPlaceholderVideos.setText("Videos");
            for (int i = 0; i < totalMovieVideos; i++) {
                // Ambil daftar video untuk Movie yang dipilih dan inflate menjadi Button.
                final MovieVideo movieVideo = mMovieVideos.get(i);
                if (movieVideo.getSite().equals("YouTube")) {
                    LayoutInflater layoutInflater = LayoutInflater.from(DetailActivity.this);
                    Button button = (Button) layoutInflater.inflate(R.layout.button_video, null, false);
                    button.setText("[" + movieVideo.getType() + "] " + movieVideo.getName());
                    // Buka video menggunakan aplikasi Youtube (jika terinstall) / browser / app yang kompatibel, jika di-click.
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemHelper.youtubeInstalled(DetailActivity.this)) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setPackage("com.google.android.youtube");
                                intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + movieVideo.getKey()));
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + movieVideo.getKey()));
                                startActivity(intent);
                            }
                        }
                    });
                    // Tambahkan setiap Button video ke dalam vertical LinearLayout sebagai child view.
                    mLlPlaceholderVideos.addView(button);
                }
            }
        }
    }

    /**
     * Fungsi untuk menampilkan daftar review yang tersedia untuk Movie yang dipilih.
     */
    private void setupReviews() {
        int totalMovieReviews = mMovieReviews.size();
        if (totalMovieReviews == 0) {
            // Tampilkan pesan pada TextView status jika tidak ada review untuk Movie yang dipilih.
            mTvPlaceholderReviews.setText("No reviews for this movie.");
        } else {
            // Hilangkan TextView status jika ada review untuk Movie yang dipilih.
            mTvPlaceholderReviews.setVisibility(View.GONE);
            for (int i = 0; i < totalMovieReviews; i++) {
                // Buat TextView untuk setiap review.
                final MovieReview movieReview = mMovieReviews.get(i);
                LayoutInflater layoutInflater = LayoutInflater.from(DetailActivity.this);
                TextView textView = (TextView) layoutInflater.inflate(R.layout.tv_row_column, null, false);
                textView.setText(movieReview.getContent());
                // Tambahkan setiap TextView review ke dalam vertical LinearLayout sebagai child view.
                mLlPlaceholderReview.addView(textView);
            }
        }
    }

    /**
     * Fungsi untuk menampilkan daftar genre untuk Movie yang dipilih.
     */

    private String getGenre() {
        String allGenre = "";
        String genreIds = mMovie.getGenreIds();
        if (genreIds.length() > 3) {
            genreIds = genreIds.substring(1, genreIds.length() - 1);
        }
        String[] genres = genreIds.split(",");
        for (int i = 0; i < genres.length; i++) {
            SharedPreferences mainPreferences = getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE);
            String genre = mainPreferences.getString("GENRE" + genres[i], "N/A");
            if (i != genres.length - 1) {
                genre = genre + ", ";
            }
            allGenre = allGenre + genre;
        }
        return allGenre;
    }

    /**
     * Fungsi untuk menambah & menghapus Movie ke / dari database.
     */

    private void deleteFromDatabase() {
        getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(mMovie.getMovieId())).build(), null, null);
        Snackbar.make(mFabFavorite, "Deleted drom favorite!", Snackbar.LENGTH_SHORT).show();
    }

    private void addToDatabase() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID, mMovie.getMovieId());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_LANGUAGE, mMovie.getOriginalLanguage());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE, mMovie.getOriginalTitle());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE, mMovie.getTitle());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_GENRE_IDS, mMovie.getGenreIds());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_COUNT, mMovie.getVoteCount());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE, mMovie.getVoteAverage());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_POPULARITY, mMovie.getPopularity());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_OVERVIEW, mMovie.getOverview());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_ADULT, mMovie.getAdult());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_VIDEO, mMovie.getVideo());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH, mMovie.getPosterPath());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH, mMovie.getBackdropPath());

        getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);
        Snackbar.make(mFabFavorite, "Added to favorite!", Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Override fungsi- fungsi activity lifecycle.
     */
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                String key = mMovie.getMovieId() + "SudahAdaDiDatabase";
                SharedPreferences mainPreferences = getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE);
                Boolean sudahAdaDiDatabase = mainPreferences.getBoolean(key, false);
                Log.d(TAG, "onClick: fab clicked");
                if (sudahAdaDiDatabase) {
                    deleteFromDatabase();
                    Log.d(TAG, "onClick: delete from db");
                    SharedPreferences.Editor editor = getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE).edit();
                    editor.putBoolean(key, false);
                    editor.apply();
                } else {
                    addToDatabase();
                    Log.d(TAG, "onClick: add to db");
                    SharedPreferences.Editor editor = getSharedPreferences("MAIN_PREFERENCES", MODE_PRIVATE).edit();
                    editor.putBoolean(key, true);
                    editor.apply();
                }

                break;
        }
    }

    /**
     * Override fungsi - fungsi callback.
     */

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}