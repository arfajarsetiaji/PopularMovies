package com.arfaststudio.prototype.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arfaststudio.prototype.popularmovies.DetailActivity;
import com.arfaststudio.prototype.popularmovies.R;
import com.arfaststudio.prototype.popularmovies.data.MovieContract;
import com.arfaststudio.prototype.popularmovies.model.MovieModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>{

    private Context mContext;
    private ArrayList<MovieModel> listMovie;
    private Cursor mCursor;

    private static final String TAG = "MovieAdapter";

    public MovieAdapter(Context context, ArrayList<MovieModel> listMovie) {

        mContext = context;
        this.listMovie = listMovie;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Menghubungkan dengan movie item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        int judulIndex = mCursor.getColumnIndex(MovieContract.MovielistEntry.COLUMN_JUDUL);
        int posterIndex = mCursor.getColumnIndex(MovieContract.MovielistEntry.COLUMN_POSTER);

        mCursor.moveToPosition(position);

        String judulFilm = mCursor.getString(judulIndex);
        String posterFilm = "https://image.tmdb.org/t/p/w500" + mCursor.getString(posterIndex);
        Log.d(TAG, "onBindViewHolder: " + judulFilm + " " + posterFilm);

        // Set data
        // holder.tvjudulMovie.setText(listMovie.get(position).getJudul());
        holder.tvjudulMovie.setText(judulFilm);

        // Glide untuk load gambar dari internet
        // Glide.with(mContext).load(listMovie.get(position).getPoster()).into(holder.ivposterMovie);
        Glide.with(mContext).load(posterFilm).into(holder.ivposterMovie);


        // Set onClick
        holder.ivposterMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked position" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, DetailActivity.class);
                // Kirim data
                intent.putExtra("JUDUL", listMovie.get(position).getJudul());
                intent.putExtra("POSTER", listMovie.get(position).getPoster());
                intent.putExtra("RATING", listMovie.get(position).getRating());
                intent.putExtra("RELEASE_DATE", listMovie.get(position).getReleaseDate());
                intent.putExtra("OVERVIEW", listMovie.get(position).getOverview());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // jumlah list
        // return listMovie.size();
        if(mCursor != null){
            return mCursor.getCount();
        }else {
            return -1;
        }

    }

    public class MyViewHolder extends ViewHolder {
        // Menyambungkan dengan komponen yang di dalam item

        ImageView ivposterMovie;
        TextView tvjudulMovie;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivposterMovie = (ImageView) itemView.findViewById(R.id.iv_item_poster);
            tvjudulMovie = (TextView) itemView.findViewById(R.id.tv_item_judul);
        }
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}