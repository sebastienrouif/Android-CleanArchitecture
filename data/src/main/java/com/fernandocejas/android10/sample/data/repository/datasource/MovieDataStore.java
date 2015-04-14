/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import java.util.Collection;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface MovieDataStore {
  /**
   * Callback used for clients to be notified when either a movie list has been loaded or any error
   * occurred.
   */
  interface MovieListCallback {
    void onMovieListLoaded(Collection<MovieEntity> moviesCollection);

    void onError(Exception exception);
  }

  /**
   * Callback used for clients to be notified when either movie data has been retrieved successfully
   * or any error occurred.
   */
  interface MovieDetailsCallback {
    void onMovieEntityLoaded(MovieEntity movieEntity);

    void onError(Exception exception);
  }

  /**
   * Get a collection of {@link com.fernandocejas.android10.sample.domain.Movie}.
   *
   * @param movieListCallback A {@link MovieListCallback} used for notifying clients.
   */
  void getMoviesEntityList(MovieListCallback movieListCallback);

  /**
   * Get a {@link MovieEntity} by its id.
   *
   * @param id The id to retrieve movie data.
   * @param movieDetailsCallback A {@link MovieDetailsCallback} for notifications.
   */
  void getMovieEntityDetails(int id, MovieDetailsCallback movieDetailsCallback);
}
