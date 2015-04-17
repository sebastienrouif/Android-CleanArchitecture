/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.repository;

import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.PaginatedMovies;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import java.util.Collection;

/**
 * Interface that represents a Repository for getting {@link Movie} related data.
 */
public interface MovieRepository {
  /**
   * Callback used to be notified when either a movie list has been loaded or an error happened.
   */
  interface MovieListCallback {
    void onMovieListLoaded(PaginatedMovies paginatedMovies);

    void onError(ErrorBundle errorBundle);
  }

  /**
   * Callback used to be notified when either a movie has been loaded or an error happened.
   */
  interface MovieDetailsCallback {
    void onMovieLoaded(Movie movie);

    void onError(ErrorBundle errorBundle);
  }

  /**
   * Get a collection of {@link Movie}.
   *
   * @param movieListCallback A {@link MovieListCallback} used for notifying clients.
   */
  void getMovieList(int page, MovieListCallback movieListCallback);

  /**
   * Get an {@link Movie} by id.
   *
   * @param movieId The movie id used to retrieve movie data.
   * @param movieCallback A {@link MovieDetailsCallback} used for notifying clients.
   */
  void getMovieById(final int movieId, MovieDetailsCallback movieCallback);
}
