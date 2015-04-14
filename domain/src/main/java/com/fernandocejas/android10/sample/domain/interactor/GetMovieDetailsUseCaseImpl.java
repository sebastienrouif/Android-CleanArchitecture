/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.MovieRepository;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link GetMovieDetailsUseCase} that represents a use case for
 * retrieving data related to an specific {@link Movie}.
 */
public class GetMovieDetailsUseCaseImpl implements GetMovieDetailsUseCase {

  private final MovieRepository movieRepository;
  private final ThreadExecutor threadExecutor;
  private final PostExecutionThread postExecutionThread;

  private int movieId = -1;
  private GetMovieDetailsUseCase.Callback callback;

  /**
   * Constructor of the class.
   *
   * @param movieRepository A {@link MovieRepository} as a source for retrieving data.
   * @param threadExecutor {@link ThreadExecutor} used to execute this use case in a background
   * thread.
   * @param postExecutionThread {@link PostExecutionThread} used to post updates when the use case
   * has been executed.
   */
  @Inject
  public GetMovieDetailsUseCaseImpl(MovieRepository movieRepository, ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
    this.movieRepository = movieRepository;
    this.threadExecutor = threadExecutor;
    this.postExecutionThread = postExecutionThread;
  }

  @Override public void execute(int movieId, Callback callback) {
    if (movieId < 0 || callback == null) {
      throw new IllegalArgumentException("Invalid parameter!!!");
    }
    this.movieId = movieId;
    this.callback = callback;
    this.threadExecutor.execute(this);
  }

  @Override public void run() {
    this.movieRepository.getMovieById(this.movieId, this.repositoryCallback);
  }

  private final MovieRepository.MovieDetailsCallback repositoryCallback =
      new MovieRepository.MovieDetailsCallback() {
        @Override public void onMovieLoaded(Movie movie) {
          notifyGetMovieDetailsSuccessfully(movie);
        }

        @Override public void onError(ErrorBundle errorBundle) {
          notifyError(errorBundle);
        }
      };

  private void notifyGetMovieDetailsSuccessfully(final Movie movie) {
    this.postExecutionThread.post(new Runnable() {
      @Override public void run() {
        callback.onMovieDataLoaded(movie);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    this.postExecutionThread.post(new Runnable() {
      @Override public void run() {
        callback.onError(errorBundle);
      }
    });
  }
}
