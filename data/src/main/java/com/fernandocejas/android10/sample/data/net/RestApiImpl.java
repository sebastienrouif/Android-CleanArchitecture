/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.PaginatedMoviesEntity;
import com.fernandocejas.android10.sample.data.exception.NetworkConnectionException;

import retrofit.RestAdapter;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

    private final Context mContext;
    private CloudInterface mRestAdapter;

    /**
     * Constructor of the class
     *
     */
    public RestApiImpl(Context context) {
        mContext = context;
        buildRestAdapter();
    }

    private void buildRestAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mRestAdapter = restAdapter.create(CloudInterface.class);
    }

    @Override
    public void getMovieList(int page, MovieListCallback movieListCallback) {
        if (movieListCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        if (isThereInternetConnection()) {
            try {
                PaginatedMoviesEntity paginatedMoviesEntity = mRestAdapter.getNowPlaying(API_KEY, page, LANGUAGE);
                movieListCallback.onMovieListLoaded(paginatedMoviesEntity);
            } catch (Exception e) {
                //TODO provide better error handling
                movieListCallback.onError(new NetworkConnectionException(e.getCause()));
            }
        } else {
            movieListCallback.onError(new NetworkConnectionException());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getMovieById(final int movieId,
                             final MovieDetailsCallback movieDetailsCallback) {
        if (movieDetailsCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        if (isThereInternetConnection()) {
            try {
                MovieEntity movieDetail = mRestAdapter.getMovieDetail(API_KEY, movieId);
                movieDetailsCallback.onMovieEntityLoaded(movieDetail);
            } catch (Exception e) {
                //TODO provide better error handling
                movieDetailsCallback.onError(new NetworkConnectionException(e.getCause()));
            }
        } else {
            movieDetailsCallback.onError(new NetworkConnectionException());
        }
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
