/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.PaginatedMovies;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import java.util.Collection;

/**
 * This interface represents a execution unit for a use case to get a collection of {@link Movie}.
 * By convention this use case (Interactor) implementation will return the result using a Callback.
 * That callback should be executed in the UI thread.
 */
public interface GetMovieListUseCase extends Interactor {
  /**
   * Callback used to be notified when either a movies collection has been loaded or an error
   * happened.
   */
  interface Callback {
    void onMovieListLoaded(PaginatedMovies paginatedMovies);
    void onError(ErrorBundle errorBundle);
  }

  /**
   * Executes this movie case.
   *
   * @param callback A {@link GetMovieListUseCase.Callback} used to notify the client.
   */
  void execute(int page, Callback callback);
}
