/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import android.content.Context;
import com.fernandocejas.android10.sample.data.cache.MovieCache;
import com.fernandocejas.android10.sample.data.net.RestApi;
import com.fernandocejas.android10.sample.data.net.RestApiImpl;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link MovieDataStore}.
 */
@Singleton
public class MovieDataStoreFactory {

  private final Context context;
  private final MovieCache movieCache;

  @Inject
  public MovieDataStoreFactory(Context context, MovieCache movieCache) {
    if (context == null || movieCache == null) {
      throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.movieCache = movieCache;
  }

  /**
   * Create {@link MovieDataStore} from a movie page.
   */
  public MovieDataStore createForPage(int page) {
    MovieDataStore movieDataStore;

    if (!movieCache.isMoviePageExpired() && movieCache.isMoviePageCached(page)) {
      movieDataStore = new DiskMovieDataStore(this.movieCache);
    } else {
      movieDataStore = createCloudDataStore();
    }

    return movieDataStore;
  }

  /**
   * Create {@link MovieDataStore} from a movie id.
   */
  public MovieDataStore create(int movieId) {
    MovieDataStore movieDataStore;

    if (!this.movieCache.isMovieExpired() && this.movieCache.isMovieCached(movieId)) {
      movieDataStore = new DiskMovieDataStore(this.movieCache);
    } else {
      movieDataStore = createCloudDataStore();
    }

    return movieDataStore;
  }

  /**
   * Create {@link MovieDataStore} to retrieve data from the Cloud.
   */
  public MovieDataStore createCloudDataStore() {
    RestApi restApi = new RestApiImpl(this.context);

    return new CloudMovieDataStore(restApi, this.movieCache);
  }
}
