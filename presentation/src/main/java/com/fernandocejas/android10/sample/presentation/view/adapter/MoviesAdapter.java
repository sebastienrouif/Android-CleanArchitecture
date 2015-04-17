/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.MovieModel;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Adaptar that manages a collection of {@link MovieModel}.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    public interface OnItemClickListener {
        void onMovieItemClicked(MovieModel movieModel);
    }

    private List<MovieModel> mMoviesCollection;
    private final LayoutInflater mLayoutInflater;

    private OnItemClickListener mOnItemClickListener;

    public MoviesAdapter(Context context, Collection<MovieModel> moviesCollection) {
        validateMoviesCollection(moviesCollection);
        mLayoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMoviesCollection = (List<MovieModel>) moviesCollection;
    }

    @Override
    public int getItemCount() {
        return (mMoviesCollection != null) ? mMoviesCollection.size() : 0;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.mLayoutInflater.inflate(R.layout.row_movie, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        final MovieModel movieModel = this.mMoviesCollection.get(position);
        holder.textViewTitle.setText(movieModel.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoviesAdapter.this.mOnItemClickListener != null) {
                    MoviesAdapter.this.mOnItemClickListener.onMovieItemClicked(movieModel);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addMoviesCollection(Collection<MovieModel> moviesCollection) {
        validateMoviesCollection(moviesCollection);
        mMoviesCollection.addAll(moviesCollection);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private void validateMoviesCollection(Collection<MovieModel> moviesCollection) {
        if (moviesCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title)
        TextView textViewTitle;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
