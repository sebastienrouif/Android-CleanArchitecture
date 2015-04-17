/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.net;

import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.PaginatedMoviesEntity;
import com.fernandocejas.android10.sample.domain.Movie;

import java.util.Collection;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {

    public static final String BASE_URL = "http://api.themoviedb.org/3";
    public static final String API_KEY = "247c4a94cc80cc47a3bdb84c75194645";
    public static final String LANGUAGE = "en";

    /**
     * Callback used to be notified when either a movie list has been loaded or an error happened.
     */
    interface MovieListCallback {
        void onMovieListLoaded(PaginatedMoviesEntity paginatedMoviesEntity);

        void onError(Exception exception);
    }

    /**
     * Callback to be notified when getting a movie from the network.
     */
    interface MovieDetailsCallback {
        void onMovieEntityLoaded(MovieEntity movieEntity);

        void onError(Exception exception);
    }

    /**
     * Get a collection of {@link Movie}.
     *
     * @param page
     * @param movieListCallback A {@link MovieListCallback} used for notifying clients.
     */
    void getMovieList(int page, MovieListCallback movieListCallback);

    /**
     * Retrieves a movie by id from the network.
     *
     * @param movieId              The movie id used to get movie data.
     * @param movieDetailsCallback {@link MovieDetailsCallback} to be notified when movie data has been
     *                             retrieved.
     */
    void getMovieById(final int movieId, final MovieDetailsCallback movieDetailsCallback);
}
