package it.tangodev.popularmoviesapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.tangodev.popularmoviesapp.R;
import it.tangodev.popularmoviesapp.models.MovieReview;
import it.tangodev.popularmoviesapp.models.MovieVideo;

public class MovieReviewListAdapter extends RecyclerView.Adapter<MovieReviewListAdapter.ViewHolder> {
    private List<MovieReview> dataset;

    public MovieReviewListAdapter() {
        this.dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_review_list_cell, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieReview movieReview = dataset.get(position);
        holder.movieReviewListCellContent.setText(movieReview.getContent());
        holder.movieReviewListCellAuthor.setText(movieReview.getAuthor());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieReviewListCellContent, movieReviewListCellAuthor;

        public ViewHolder(View view) {
            super(view);
            movieReviewListCellContent = (TextView) view.findViewById(R.id.movie_review_list_cell_content);
            movieReviewListCellAuthor = (TextView) view.findViewById(R.id.movie_review_list_cell_author);
        }
    }

    public List<MovieReview> getDataset() {
        return dataset;
    }
}
