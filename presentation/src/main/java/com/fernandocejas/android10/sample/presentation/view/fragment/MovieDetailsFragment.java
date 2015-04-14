/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.MovieComponent;
import com.fernandocejas.android10.sample.presentation.model.MovieModel;
import com.fernandocejas.android10.sample.presentation.presenter.MovieDetailsPresenter;
import com.fernandocejas.android10.sample.presentation.view.MovieDetailsView;
import com.fernandocejas.android10.sample.presentation.view.component.AutoLoadImageView;
import javax.inject.Inject;

/**
 * Fragment that shows details of a certain movie.
 */
public class MovieDetailsFragment extends BaseFragment implements MovieDetailsView {

  private static final String ARGUMENT_KEY_USER_ID = "org.android10.ARGUMENT_USER_ID";

  private int movieId;

  @Inject MovieDetailsPresenter movieDetailsPresenter;

  @InjectView(R.id.iv_cover) AutoLoadImageView iv_cover;
  @InjectView(R.id.tv_fullname) TextView tv_fullname;
  @InjectView(R.id.tv_email) TextView tv_email;
  @InjectView(R.id.tv_followers) TextView tv_followers;
  @InjectView(R.id.tv_description) TextView tv_description;
  @InjectView(R.id.rl_progress) RelativeLayout rl_progress;
  @InjectView(R.id.rl_retry) RelativeLayout rl_retry;
  @InjectView(R.id.bt_retry) Button bt_retry;

  public MovieDetailsFragment() { super(); }

  public static MovieDetailsFragment newInstance(int movieId) {
    MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();

    Bundle argumentsBundle = new Bundle();
    argumentsBundle.putInt(ARGUMENT_KEY_USER_ID, movieId);
    movieDetailsFragment.setArguments(argumentsBundle);

    return movieDetailsFragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_movie_details, container, false);
    ButterKnife.inject(this, fragmentView);

    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.initialize();
  }

  @Override public void onResume() {
    super.onResume();
    this.movieDetailsPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.movieDetailsPresenter.pause();
  }

  private void initialize() {
    this.getComponent(MovieComponent.class).inject(this);
    this.movieDetailsPresenter.setView(this);
    this.movieId = getArguments().getInt(ARGUMENT_KEY_USER_ID);
    this.movieDetailsPresenter.initialize(this.movieId);
  }

  @Override public void renderMovie(MovieModel movie) {
    if (movie != null) {
      this.iv_cover.setImageUrl(movie.getCoverUrl());
      this.tv_fullname.setText(movie.getFullName());
      this.tv_email.setText(movie.getEmail());
      this.tv_followers.setText(String.valueOf(movie.getFollowers()));
      this.tv_description.setText(movie.getDescription());
    }
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

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context getContext() {
    return getActivity().getApplicationContext();
  }

  /**
   * Loads all movies.
   */
  private void loadMovieDetails() {
    if (this.movieDetailsPresenter != null) {
      this.movieDetailsPresenter.initialize(this.movieId);
    }
  }

  @OnClick(R.id.bt_retry)
  void onButtonRetryClick() {
    MovieDetailsFragment.this.loadMovieDetails();
  }
}
