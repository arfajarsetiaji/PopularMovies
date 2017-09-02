package com.arfajarsetiaji.android.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arfajarsetiaji.android.popularmovies.DetailActivity;
import com.arfajarsetiaji.android.popularmovies.R;
import com.arfajarsetiaji.android.popularmovies.model.Movie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.viewHolder> {
    private static final String TAG = "FavoriteAdapter";

    Context mContext;
    List<Movie> mMovies;

    public FavoriteAdapter(Context context, List<Movie> movies) {
        mContext = context;
        this.mMovies = movies;
        Log.d(TAG, "FavoriteAdapter: Called.");
    }

    @Override
    public FavoriteAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        Log.d(TAG, "onCreateViewHolder: Called.");
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FavoriteAdapter.viewHolder holder, final int position) {
        if (position < mMovies.size()) {
            String posterPath = mMovies.get(position).getPosterPath();
            holder.tvPosterMovie.setText(mMovies.get(position).getOriginalTitle() + "\n" + "(Loading...)");
            Glide.with(holder.ivPosterMovie.getContext()).load(posterPath).dontTransform().diskCacheStrategy(DiskCacheStrategy.SOURCE)
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
                            holder.ivPosterMovie.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(holder.ivPosterMovie);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("MOVIE_DETAILS", mMovies.get(position));
                    intent.putExtras(bundle);
                    Log.d(TAG, "onClick: Called.");
                    mContext.startActivity(intent);
                }
            });
        }
        Log.d(TAG, "onBindViewHolder: Called.");
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + mMovies.size());
        return mMovies.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "viewHolder";

        TextView tvPosterMovie;
        ImageView ivPosterMovie;

        public viewHolder(View itemView) {
            super(itemView);
            tvPosterMovie = (TextView) itemView.findViewById(R.id.tv_item_poster);
            ivPosterMovie = (ImageView) itemView.findViewById(R.id.iv_item_poster);
            Log.d(TAG, "viewHolder: Called.");
        }
    }
}