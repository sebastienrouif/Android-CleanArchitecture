/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.cache;

import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.PaginatedMoviesEntity;

/**
 * An interface representing a movie Cache.
 */
public interface MovieCache {

  /**
   * Callback used to be notified when a {@link MovieEntity} has been loaded.
   */
  interface MovieCacheCallback {
    void onMovieEntityLoaded(MovieEntity movieEntity);

    void onError(Exception exception);
  }

  /**
   * Callback used to be notified when a {@link PaginatedMoviesEntity} has been loaded.
   */
  interface PaginatedMovieCacheCallback {
    void onMovieEntityLoaded(PaginatedMoviesEntity paginatedMoviesEntity);

    void onError(Exception exception);
  }

  /**
   * Gets an element from the cache using a {@link MovieCacheCallback}.
   *
   * @param movieId The movie id to retrieve data.
   * @param callback The {@link MovieCacheCallback} to notify the client.
   */
  void getMovie(final int movieId, final MovieCacheCallback callback);

  /**
   * Puts and element into the cache.
   *
   * @param movieEntity Element to insert in the cache.
   */
  void putMovie(MovieEntity movieEntity);

  /**
   * Checks if an element (Movie) exists in the cache.
   *
   * @param movieId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isMovieCached(final int movieId);

  /**
   * Checks if the cache is expired.
   *
   * @return true, the cache is expired, otherwise false.
   */
  boolean isMovieExpired();

  /**
   * Gets an element from the cache using a {@link MovieCacheCallback}.
   *
   * @param page The page to retrieve data.
   * @param callback The {@link MovieCacheCallback} to notify the client.
   */
  void getPaginatedMovies(final int page, final PaginatedMovieCacheCallback callback);

  /**
   * Puts and element into the cache.
   *
   * @param paginatedMoviesEntity Element to insert in the cache.
   */
  void putPaginatedMovie(PaginatedMoviesEntity paginatedMoviesEntity);

  /**
   * Checks if an element (Movie) exists in the cache.
   *
   * @param page The page used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isMoviePageCached(final int page);

  /**
   * Checks if the cache is expired.
   *
   * @return true, the cache is expired, otherwise false.
   */
  boolean isMoviePageExpired();

  /**
   * Evict all elements of the cache.
   */
  void evictAll();
}
