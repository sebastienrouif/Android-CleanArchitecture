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
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.MovieModel;
import java.util.Collection;
import java.util.List;

/**
 * Adaptar that manages a collection of {@link MovieModel}.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

  public interface OnItemClickListener {
    void onMovieItemClicked(MovieModel movieModel);
  }

  private List<MovieModel> moviesCollection;
  private final LayoutInflater layoutInflater;

  private OnItemClickListener onItemClickListener;

  public MoviesAdapter(Context context, Collection<MovieModel> moviesCollection) {
    this.validateMoviesCollection(moviesCollection);
    this.layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.moviesCollection = (List<MovieModel>) moviesCollection;
  }

  @Override public int getItemCount() {
    return (this.moviesCollection != null) ? this.moviesCollection.size() : 0;
  }

  @Override public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = this.layoutInflater.inflate(R.layout.row_movie, parent, false);
    MovieViewHolder movieViewHolder = new MovieViewHolder(view);

    return movieViewHolder;
  }

  @Override public void onBindViewHolder(MovieViewHolder holder, final int position) {
    final MovieModel movieModel = this.moviesCollection.get(position);
    holder.textViewTitle.setText(movieModel.getFullName());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (MoviesAdapter.this.onItemClickListener != null) {
          MoviesAdapter.this.onItemClickListener.onMovieItemClicked(movieModel);
        }
      }
    });
  }

  @Override public long getItemId(int position) {
    return position;
  }

  public void setMoviesCollection(Collection<MovieModel> moviesCollection) {
    this.validateMoviesCollection(moviesCollection);
    this.moviesCollection = (List<MovieModel>) moviesCollection;
    this.notifyDataSetChanged();
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  private void validateMoviesCollection(Collection<MovieModel> moviesCollection) {
    if (moviesCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  static class MovieViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.title) TextView textViewTitle;

    public MovieViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }
}
