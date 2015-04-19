/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.MovieModel;
import com.squareup.picasso.Picasso;

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
    private final Picasso mPicasso;

    private OnItemClickListener mOnItemClickListener;

    public MoviesAdapter(Collection<MovieModel> moviesCollection, Picasso picasso) {
        validateMoviesCollection(moviesCollection);
        mPicasso = picasso;
        mMoviesCollection = (List<MovieModel>) moviesCollection;
    }

    @Override
    public int getItemCount() {
        return (mMoviesCollection != null) ? mMoviesCollection.size() : 0;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView movieRowView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_movie, parent, false);
        return new MovieViewHolder(movieRowView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        final MovieModel movieModel = mMoviesCollection.get(position);
        holder.itemView.setTag(position);
        holder.bindTo(movieModel, mPicasso);
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
        mOnItemClickListener = onItemClickListener;
    }

    private void validateMoviesCollection(Collection<MovieModel> moviesCollection) {
        if (moviesCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.movie_row_poster) ImageView mPosterImageView;

        @InjectView(R.id.movie_row_title) TextView mTitleView;

        @InjectView(R.id.movie_row_release_date) TextView mReleaseDateView;

        @InjectView(R.id.movie_row_rating) TextView mRatingView;

        public MovieViewHolder(CardView itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void bindTo(MovieModel movieModel, Picasso picasso) {
            picasso.load(movieModel.getPosterUrl(mPosterImageView.getWidth()))
                    .placeholder(R.drawable.logo)
                    .into(mPosterImageView);
            mTitleView.setText(movieModel.getTitle());
            mReleaseDateView.setText(itemView.getResources().getString(R.string.movie_release_date_formated, movieModel.getReleaseDate()));
            mRatingView.setText(itemView.getResources().getString(R.string.movie_rating_formated, movieModel.getVoteAverage()));
        }
    }
}
