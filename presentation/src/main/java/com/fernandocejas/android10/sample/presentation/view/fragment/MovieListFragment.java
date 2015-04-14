/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.MovieComponent;
import com.fernandocejas.android10.sample.presentation.model.MovieModel;
import com.fernandocejas.android10.sample.presentation.presenter.MovieListPresenter;
import com.fernandocejas.android10.sample.presentation.view.MovieListView;
import com.fernandocejas.android10.sample.presentation.view.adapter.MoviesAdapter;
import com.fernandocejas.android10.sample.presentation.view.adapter.MoviesLayoutManager;
import java.util.Collection;
import javax.inject.Inject;

/**
 * Fragment that shows a list of Movies.
 */
public class MovieListFragment extends BaseFragment implements MovieListView {

  /**
   * Interface for listening movie list events.
   */
  public interface MovieListListener {
    void onMovieClicked(final MovieModel movieModel);
  }

  @Inject MovieListPresenter movieListPresenter;

  @InjectView(R.id.rv_movies) RecyclerView rv_movies;
  @InjectView(R.id.rl_progress) RelativeLayout rl_progress;
  @InjectView(R.id.rl_retry) RelativeLayout rl_retry;
  @InjectView(R.id.bt_retry) Button bt_retry;

  private MoviesAdapter moviesAdapter;
  private MoviesLayoutManager moviesLayoutManager;

  private MovieListListener movieListListener;

  public MovieListFragment() { super(); }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof MovieListListener) {
      this.movieListListener = (MovieListListener) activity;
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_movie_list, container, true);
    ButterKnife.inject(this, fragmentView);
    setupUI();

    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.initialize();
    this.loadMovieList();
  }

  @Override public void onResume() {
    super.onResume();
    this.movieListPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.movieListPresenter.pause();
  }

  private void initialize() {
    this.getComponent(MovieComponent.class).inject(this);
    this.movieListPresenter.setView(this);
  }

  private void setupUI() {
    this.moviesLayoutManager = new MoviesLayoutManager(getActivity());
    this.rv_movies.setLayoutManager(moviesLayoutManager);
  }

  @Override public void showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override public void showRetry() {
    this.rl_retry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    this.rl_retry.setVisibility(View.GONE);
  }

  @Override public void renderMovieList(Collection<MovieModel> movieModelCollection) {
    if (movieModelCollection != null) {
      if (this.moviesAdapter == null) {
        this.moviesAdapter = new MoviesAdapter(getActivity(), movieModelCollection);
      } else {
        this.moviesAdapter.setMoviesCollection(movieModelCollection);
      }
      this.moviesAdapter.setOnItemClickListener(onItemClickListener);
      this.rv_movies.setAdapter(moviesAdapter);
    }
  }

  @Override public void viewMovie(MovieModel movieModel) {
    if (this.movieListListener != null) {
      this.movieListListener.onMovieClicked(movieModel);
    }
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context getContext() {
    return this.getActivity().getApplicationContext();
  }

  /**
   * Loads all movies.
   */
  private void loadMovieList() {
    this.movieListPresenter.initialize();
  }

  @OnClick(R.id.bt_retry) void onButtonRetryClick() {
    MovieListFragment.this.loadMovieList();
  }

  private MoviesAdapter.OnItemClickListener onItemClickListener =
      new MoviesAdapter.OnItemClickListener() {
        @Override public void onMovieItemClicked(MovieModel movieModel) {
            if (MovieListFragment.this.movieListPresenter != null && movieModel != null) {
              MovieListFragment.this.movieListPresenter.onMovieClicked(movieModel);
            }
        }
      };
}
