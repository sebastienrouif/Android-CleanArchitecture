/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.GetMovieDetailsUseCase;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.mapper.MovieModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.MovieModel;
import com.fernandocejas.android10.sample.presentation.view.MovieDetailsView;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class MovieDetailsPresenter implements Presenter {

  /** id used to retrieve movie details */
  private int movieId;

  private MovieDetailsView viewDetailsView;

  private final GetMovieDetailsUseCase getMovieDetailsUseCase;
  private final MovieModelDataMapper movieModelDataMapper;

  @Inject
  public MovieDetailsPresenter(GetMovieDetailsUseCase getMovieDetailsUseCase,
                               MovieModelDataMapper movieModelDataMapper) {
    this.getMovieDetailsUseCase = getMovieDetailsUseCase;
    this.movieModelDataMapper = movieModelDataMapper;
  }

  public void setView(@NonNull MovieDetailsView view) {
    this.viewDetailsView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  /**
   * Initializes the presenter by start retrieving movie details.
   */
  public void initialize(int movieId) {
    this.movieId = movieId;
    this.loadMovieDetails();
  }

  /**
   * Loads movie details.
   */
  private void loadMovieDetails() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getMovieDetails();
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.getContext(),
        errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void showMovieDetailsInView(Movie movie) {
    final MovieModel movieModel = this.movieModelDataMapper.transform(movie);
    this.viewDetailsView.renderMovie(movieModel);
  }

  private void getMovieDetails() {
    this.getMovieDetailsUseCase.execute(this.movieId, this.movieDetailsCallback);
  }

  private final GetMovieDetailsUseCase.Callback movieDetailsCallback = new GetMovieDetailsUseCase.Callback() {
    @Override public void onMovieDataLoaded(Movie movie) {
      MovieDetailsPresenter.this.showMovieDetailsInView(movie);
      MovieDetailsPresenter.this.hideViewLoading();
    }

    @Override public void onError(ErrorBundle errorBundle) {
      MovieDetailsPresenter.this.hideViewLoading();
      MovieDetailsPresenter.this.showErrorMessage(errorBundle);
      MovieDetailsPresenter.this.showViewRetry();
    }
  };
}
