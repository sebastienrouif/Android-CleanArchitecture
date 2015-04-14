/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.net;

import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.domain.Movie;
import java.util.Collection;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
  /**
   * Callback used to be notified when either a movie list has been loaded or an error happened.
   */
  interface MovieListCallback {
    void onMovieListLoaded(Collection<MovieEntity> moviesCollection);

    void onError(Exception exception);
  }

  /**
   * Callback to be notified when getting a movie from the network.
   */
  interface MovieDetailsCallback {
    void onMovieEntityLoaded(MovieEntity movieEntity);

    void onError(Exception exception);
  }

  static final String API_BASE_URL = "http://www.android10.org/myapi/";

  /** Api url for getting all movies */
  static final String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
  /** Api url for getting a movie profile: Remember to concatenate id + 'json' */
  static final String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

  /**
   * Get a collection of {@link Movie}.
   *
   * @param movieListCallback A {@link MovieListCallback} used for notifying clients.
   */
  void getMovieList(MovieListCallback movieListCallback);

  /**
   * Retrieves a movie by id from the network.
   *
   * @param movieId The movie id used to get movie data.
   * @param movieDetailsCallback {@link MovieDetailsCallback} to be notified when movie data has been
   * retrieved.
   */
  void getMovieById(final int movieId, final MovieDetailsCallback movieDetailsCallback);
}
