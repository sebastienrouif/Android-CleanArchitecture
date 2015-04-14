/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;

/**
 * This interface represents a execution unit for a use case to get data for an specific movie.
 * By convention this use case ({@link Interactor}) implementation will return the result using a
 * callback that should be executed in the UI thread.
 */
public interface GetMovieDetailsUseCase extends Interactor {
  /**
   * Callback used to be notified when either a movie has been loaded or an error happened.
   */
  interface Callback {
    void onMovieDataLoaded(Movie movie);
    void onError(ErrorBundle errorBundle);
  }

  /**
   * Executes this movie case.
   *
   * @param movieId The movie id to retrieve.
   * @param callback A {@link GetMovieDetailsUseCase.Callback} used for notify the client.
   */
  public void execute(int movieId, Callback callback);
}
