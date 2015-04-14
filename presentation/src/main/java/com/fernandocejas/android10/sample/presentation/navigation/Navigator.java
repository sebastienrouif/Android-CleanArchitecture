/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import com.fernandocejas.android10.sample.presentation.view.activity.MovieDetailsActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.MovieListActivity;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

  @Inject
  public void Navigator() {
    //empty
  }

  /**
   * Goes to the movie list screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToMovieList(Context context) {
    if (context != null) {
      Intent intentToLaunch = MovieListActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the movie details screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToMovieDetails(Context context, int movieId) {
    if (context != null) {
      Intent intentToLaunch = MovieDetailsActivity.getCallingIntent(context, movieId);
      context.startActivity(intentToLaunch);
    }
  }
}
