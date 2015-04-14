/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.cache.MovieCache;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.net.RestApi;
import java.util.Collection;

/**
 * {@link MovieDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudMovieDataStore implements MovieDataStore {

  private final RestApi restApi;
  private final MovieCache movieCache;

  /**
   * Construct a {@link MovieDataStore} based on connections to the api (Cloud).
   *
   * @param restApi The {@link RestApi} implementation to use.
   * @param movieCache A {@link MovieCache} to cache data retrieved from the api.
   */
  public CloudMovieDataStore(RestApi restApi, MovieCache movieCache) {
    this.restApi = restApi;
    this.movieCache = movieCache;
  }

  /**
   * {@inheritDoc}
   *
   * @param movieListCallback A {@link MovieListCallback} used for notifying clients.
   */
  @Override public void getMoviesEntityList(final MovieListCallback movieListCallback) {
    this.restApi.getMovieList(new RestApi.MovieListCallback() {
      @Override public void onMovieListLoaded(Collection<MovieEntity> moviesCollection) {
        movieListCallback.onMovieListLoaded(moviesCollection);
      }

      @Override public void onError(Exception exception) {
        movieListCallback.onError(exception);
      }
    });
  }

  /**
   * {@inheritDoc}
   *
   * @param id The movie id used to retrieve movie data.
   * @param movieDetailsCallback A {@link MovieDetailsCallback} used for notifying clients.
   */
  @Override public void getMovieEntityDetails(int id,
      final MovieDetailsCallback movieDetailsCallback) {
    this.restApi.getMovieById(id, new RestApi.MovieDetailsCallback() {
      @Override public void onMovieEntityLoaded(MovieEntity movieEntity) {
        movieDetailsCallback.onMovieEntityLoaded(movieEntity);
        CloudMovieDataStore.this.putMovieEntityInCache(movieEntity);
      }

      @Override public void onError(Exception exception) {
        movieDetailsCallback.onError(exception);
      }
    });
  }

  /**
   * Saves a {@link MovieEntity} into cache.
   *
   * @param movieEntity The {@link MovieEntity} to save.
   */
  private void putMovieEntityInCache(MovieEntity movieEntity) {
    if (movieEntity != null) {
      this.movieCache.put(movieEntity);
    }
  }
}
