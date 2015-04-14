/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.cache;

import com.fernandocejas.android10.sample.data.entity.MovieEntity;

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
   * Gets an element from the cache using a {@link MovieCacheCallback}.
   *
   * @param movieId The movie id to retrieve data.
   * @param callback The {@link MovieCacheCallback} to notify the client.
   */
  void get(final int movieId, final MovieCacheCallback callback);

  /**
   * Puts and element into the cache.
   *
   * @param movieEntity Element to insert in the cache.
   */
  void put(MovieEntity movieEntity);

  /**
   * Checks if an element (Movie) exists in the cache.
   *
   * @param movieId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isCached(final int movieId);

  /**
   * Checks if the cache is expired.
   *
   * @return true, the cache is expired, otherwise false.
   */
  boolean isExpired();

  /**
   * Evict all elements of the cache.
   */
  void evictAll();
}
