package com.arfajarsetiaji.android.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arfajarsetiaji.android.popularmovies.DetailActivity;
import com.arfajarsetiaji.android.popularmovies.MainApplication;
import com.arfajarsetiaji.android.popularmovies.R;
import com.arfajarsetiaji.android.popularmovies.model.Movie;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

/**
 * Created by Ar Fajar Setiaji on 27-Aug-17.
 */

/**
 * Extends RecyclerView.Adapter dikombinasikan dengan inner class viewHolder extends RecyclerView.viewHolder
 * untuk selanjutnya dipakai sebagai adapter di object RecyclerView
 * pada MostPopularFragment & TopRatedFragment .
 */

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.viewHolder> {
    private static final String TAG = "MovieGridAdapter";

    Context mContext;
    List<Movie> mMovies;

    /**
     * Default constructor MovieGridAdapter class.
     */

    public MovieGridAdapter(Context context, List<Movie> movies) {
        mContext = context;
        this.mMovies = movies;
    }

    /**
     * Override fungsi - fungsi RecyclerView.Adapter.
     */

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        // Pastikan mMovies tidak null atau value position !> mMovies.size untuk menghindari exception.
        // Karena di fungsi getItemCount, itemCount default sudah diubah menjadi 20.
        if (position < mMovies.size()) {
            // Ambil url poster dari setiap Movie object di dalam mMovies.
            String posterPath = mMovies.get(position).getPosterPath();

            // Tampilkan pesan status loading sebelum resource poster diambil dari server & ditampilkan di ImageView poster.
            holder.tvPosterMovie.setText(mMovies.get(position).getOriginalTitle() + "\n" + "(Loading...)");

            // Ambil resource gambar dari server dan tampilkan pada ImageView poster menggunakan Glide.RequestManager.
            MainApplication.getRequestManager().load(posterPath).dontTransform().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            // Tampilkan pesan pada TextView status saat resource gagal ditampilkan.
                            holder.tvPosterMovie.setText(mMovies.get(position).getOriginalTitle() + "\n" + "(Failed to load movie poster)");
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            // Buat ImageView Poster menjadi visible saat resource siap ditampilkan.
                            holder.ivposterMovie.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(holder.ivposterMovie);

            // Buka DetailActivity dengan membawa object Movie berdasarkan index itemView yang dipilih.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("MOVIE_DETAILS", mMovies.get(position));
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // Jika list mMovies masih kosong, buat 20 dummy viewHolder.
        if (mMovies.size() != 0) {
            return mMovies.size();
        } else return 20;

    }

    /**
     * Viewholder class setiap item dalam MostPopularFragment, TopRatedFragment & FavoriteFragment.
     */

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvPosterMovie;
        ImageView ivposterMovie;

        // Inisialisasi awal viewHolder.
        public viewHolder(View itemView) {
            super(itemView);
            tvPosterMovie = (TextView) itemView.findViewById(R.id.tv_item_poster);
            ivposterMovie = (ImageView) itemView.findViewById(R.id.iv_item_poster);
        }
    }



}