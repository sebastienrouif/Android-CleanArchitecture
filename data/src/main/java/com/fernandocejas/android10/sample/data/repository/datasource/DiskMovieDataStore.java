/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.cache.MovieCache;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.PaginatedMoviesEntity;

/**
 * {@link MovieDataStore} implementation based on file system data store.
 */
public class DiskMovieDataStore implements MovieDataStore {

    private final MovieCache movieCache;

    /**
     * Construct a {@link MovieDataStore} based file system data store.
     *
     * @param movieCache A {@link MovieCache} to cache data retrieved from the api.
     */
    public DiskMovieDataStore(MovieCache movieCache) {
        this.movieCache = movieCache;
    }

    /**
     * {@inheritDoc}
     *
     * @param movieListCallback A {@link MovieListCallback} used for notifying clients.
     */
    @Override
    public void getMoviesEntityList(int page, final MovieListCallback movieListCallback) {
        movieCache.getPaginatedMovies(page, new MovieCache.PaginatedMovieCacheCallback() {
            @Override
            public void onMovieEntityLoaded(PaginatedMoviesEntity paginatedMoviesEntity) {
                movieListCallback.onMovieListLoaded(paginatedMoviesEntity);
            }

            @Override
            public void onError(Exception exception) {
                movieListCallback.onError(exception);
            }
        });
    }

    /**
     * {@inheritDoc}
     *
     * @param id The id to retrieve movie data.
     * @param movieDetailsCallback A {@link MovieDataStore.MovieDetailsCallback} to notify the client.
     */
    @Override
    public void getMovieEntityDetails(int id,
                                      final MovieDetailsCallback movieDetailsCallback) {
        movieCache.getMovie(id, new MovieCache.MovieCacheCallback() {
            @Override
            public void onMovieEntityLoaded(MovieEntity movieEntity) {
                movieDetailsCallback.onMovieEntityLoaded(movieEntity);
            }

            @Override
            public void onError(Exception exception) {
                movieDetailsCallback.onError(exception);
            }
        });
    }
}
