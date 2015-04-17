/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository;

import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.PaginatedMoviesEntity;
import com.fernandocejas.android10.sample.data.entity.mapper.MovieEntityDataMapper;
import com.fernandocejas.android10.sample.data.exception.MovieNotFoundException;
import com.fernandocejas.android10.sample.data.exception.RepositoryErrorBundle;
import com.fernandocejas.android10.sample.data.repository.datasource.MovieDataStore;
import com.fernandocejas.android10.sample.data.repository.datasource.MovieDataStoreFactory;
import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.PaginatedMovies;
import com.fernandocejas.android10.sample.domain.repository.MovieRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link MovieRepository} for retrieving movie data.
 */
@Singleton
public class MovieDataRepository implements MovieRepository {

    private final MovieDataStoreFactory mMovieDataStoreFactory;
    private final MovieEntityDataMapper mMovieEntityDataMapper;

    /**
     * Constructs a {@link MovieRepository}.
     *
     * @param dataStoreFactory      A factory to construct different data source implementations.
     * @param movieEntityDataMapper {@link MovieEntityDataMapper}.
     */
    @Inject
    public MovieDataRepository(MovieDataStoreFactory dataStoreFactory,
                               MovieEntityDataMapper movieEntityDataMapper) {
        mMovieDataStoreFactory = dataStoreFactory;
        mMovieEntityDataMapper = movieEntityDataMapper;
    }

    /**
     * {@inheritDoc}
     *
     * @param movieListCallback A {@link MovieListCallback} used for notifying clients.
     */
    @Override
    public void getMovieList(int page, final MovieListCallback movieListCallback) {
        //we always get all movies from the cloud
        final MovieDataStore movieDataStore = mMovieDataStoreFactory.createCloudDataStore();
        movieDataStore.getMoviesEntityList(page, new MovieDataStore.MovieListCallback() {
            @Override
            public void onMovieListLoaded(PaginatedMoviesEntity paginatedMoviesEntity) {
                PaginatedMovies paginatedMovies = MovieDataRepository.this.mMovieEntityDataMapper.transform(paginatedMoviesEntity);
                movieListCallback.onMovieListLoaded(paginatedMovies);
            }

            @Override
            public void onError(Exception exception) {
                movieListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    /**
     * {@inheritDoc}
     *
     * @param movieId       The movie id used to retrieve movie data.
     * @param movieCallback A {@link com.fernandocejas.android10.sample.domain.repository.MovieRepository.MovieDetailsCallback}
     *                      used for notifying clients.
     */
    @Override
    public void getMovieById(final int movieId, final MovieDetailsCallback movieCallback) {
        MovieDataStore movieDataStore = this.mMovieDataStoreFactory.create(movieId);
        movieDataStore.getMovieEntityDetails(movieId, new MovieDataStore.MovieDetailsCallback() {
            @Override
            public void onMovieEntityLoaded(MovieEntity movieEntity) {
                Movie movie = MovieDataRepository.this.mMovieEntityDataMapper.transform(movieEntity);
                if (movie != null) {
                    movieCallback.onMovieLoaded(movie);
                } else {
                    movieCallback.onError(new RepositoryErrorBundle(new MovieNotFoundException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                movieCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}
