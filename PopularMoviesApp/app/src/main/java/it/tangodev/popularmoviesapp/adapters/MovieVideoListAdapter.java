package it.tangodev.popularmoviesapp.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.tangodev.popularmoviesapp.R;
import it.tangodev.popularmoviesapp.models.MovieVideo;

public abstract class MovieVideoListAdapter extends RecyclerView.Adapter<MovieVideoListAdapter.ViewHolder> {
    private List<MovieVideo> dataset;

    public MovieVideoListAdapter() {
        this.dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_video_list_cell, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieVideo movieVideo = dataset.get(position);
        holder.movieVideoListCellTextview.setText(movieVideo.getName());
        holder.movieVideoListCellContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(movieVideo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieVideoListCellTextview;
        public LinearLayout movieVideoListCellContainer;

        public ViewHolder(View view) {
            super(view);
            movieVideoListCellTextview = (TextView) view.findViewById(R.id.movie_video_list_cell_textview);
            movieVideoListCellContainer = (LinearLayout) view.findViewById(R.id.movie_video_list_cell_container);
        }
    }

    public List<MovieVideo> getDataset() {
        return dataset;
    }

    public abstract void onItemClick(MovieVideo movieVideo);
}
