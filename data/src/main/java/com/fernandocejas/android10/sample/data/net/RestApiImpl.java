/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.mapper.MovieEntityJsonMapper;
import com.fernandocejas.android10.sample.data.exception.NetworkConnectionException;
import java.util.Collection;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

  private final Context context;
  private final MovieEntityJsonMapper movieEntityJsonMapper;

  /**
   * Constructor of the class
   *
   * @param context {@link android.content.Context}.
   * @param movieEntityJsonMapper {@link MovieEntityJsonMapper}.
   */
  public RestApiImpl(Context context, MovieEntityJsonMapper movieEntityJsonMapper) {
    if (context == null || movieEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.movieEntityJsonMapper = movieEntityJsonMapper;
  }

  @Override public void getMovieList(MovieListCallback movieListCallback) {
    if (movieListCallback == null) {
      throw new IllegalArgumentException("Callback cannot be null!!!");
    }

    if (isThereInternetConnection()) {
      try {
        ApiConnection getMovieListConnection =
            ApiConnection.createGET(RestApi.API_URL_GET_USER_LIST);
        String responseMovieList = getMovieListConnection.requestSyncCall();
        Collection<MovieEntity> movieEntityList =
            this.movieEntityJsonMapper.transformMovieEntityCollection(responseMovieList);

        movieListCallback.onMovieListLoaded(movieEntityList);
      } catch (Exception e) {
        movieListCallback.onError(new NetworkConnectionException(e.getCause()));
      }
    } else {
      movieListCallback.onError(new NetworkConnectionException());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override public void getMovieById(final int movieId,
      final MovieDetailsCallback movieDetailsCallback) {
    if (movieDetailsCallback == null) {
      throw new IllegalArgumentException("Callback cannot be null!!!");
    }

    if (isThereInternetConnection()) {
      try {
        String apiUrl = RestApi.API_URL_GET_USER_DETAILS + movieId + ".json";
        ApiConnection getMovieDetailsConnection = ApiConnection.createGET(apiUrl);
        String responseMovieDetails = getMovieDetailsConnection.requestSyncCall();
        MovieEntity movieEntity = this.movieEntityJsonMapper.transformMovieEntity(responseMovieDetails);

        movieDetailsCallback.onMovieEntityLoaded(movieEntity);
      } catch (Exception e) {
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
        (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }
}
