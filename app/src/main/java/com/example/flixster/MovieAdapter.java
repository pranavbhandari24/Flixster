package com.example.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemMovie = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(itemMovie);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            relativeLayout = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            String imageUrl;
            String description = movie.getDescription();
            if (description.length() > 300) {
                description = description.substring(0, 270) + "...";
            }
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(description);
            //Using Glide to get the image from the url and load it into the imageView
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                imageUrl = movie.getBackdropPathPath();
            else
                imageUrl = movie.getPosterPath();

            Glide.with(context).load(imageUrl).placeholder(R.drawable.placeholder).into(ivPoster);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startDetailActivity = new Intent(context, DetailActivity.class);
                    startDetailActivity.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(startDetailActivity);
                }
            });
        }
    }
}
