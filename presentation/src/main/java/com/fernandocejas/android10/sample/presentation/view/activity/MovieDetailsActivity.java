/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerMovieComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.MovieComponent;
import com.fernandocejas.android10.sample.presentation.view.fragment.MovieDetailsFragment;

/**
 * Activity that shows details of a certain movie.
 */
public class MovieDetailsActivity extends BaseActivity implements HasComponent<MovieComponent> {

  private static final String INTENT_EXTRA_PARAM_MOVIE_ID = "org.android10.INTENT_PARAM_MOVIE_ID";
  private static final String INSTANCE_STATE_PARAM_MOVIE_ID = "org.android10.STATE_PARAM_MOVIE_ID";

  private int movieId;
  private MovieComponent movieComponent;

  public static Intent getCallingIntent(Context context, int movieId) {
    Intent callingIntent = new Intent(context, MovieDetailsActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_MOVIE_ID, movieId);

    return callingIntent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_movie_details);

    this.initializeInjector();
    this.initializeActivity(savedInstanceState);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    if (outState != null) {
      outState.putInt(INSTANCE_STATE_PARAM_MOVIE_ID, this.movieId);
    }
    super.onSaveInstanceState(outState);
  }

  /**
   * Initializes this activity.
   */
  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.movieId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_MOVIE_ID, -1);
      addFragment(R.id.fl_fragment, MovieDetailsFragment.newInstance(this.movieId));
    } else {
      this.movieId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_MOVIE_ID);
    }
  }

  private void initializeInjector() {
    this.movieComponent = DaggerMovieComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override public MovieComponent getComponent() {
    return movieComponent;
  }
}
