package it.tangodev.popularmoviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import it.tangodev.popularmoviesapp.R;
import it.tangodev.popularmoviesapp.models.Movie;

public abstract class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private List<Movie> dataset;
    private Context context;
    private MovieListAdapterListener listener;

    public MovieListAdapter(Context context, MovieListAdapterListener listener) {
        this.dataset = new ArrayList<>();
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_cell, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = dataset.get(position);
        holder.movieListCellImageview.setContentDescription(movie.getTitle());
        holder.movieListCellImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(movie);
            }
        });
        Picasso.with(context).load(movie.getPosterUrl()).fit().centerCrop().into(holder.movieListCellImageview);
        if(position == getItemCount() - 1) {
            onLastItemBind();
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieListCellImageview;

        public ViewHolder(View view) {
            super(view);
            movieListCellImageview = (ImageView) view.findViewById(R.id.movie_list_cell_imageview);
        }
    }

    public List<Movie> getDataset() {
        return dataset;
    }

    public interface MovieListAdapterListener {
        void onItemClick(Movie movie);
    }

    public abstract void onLastItemBind();
}
