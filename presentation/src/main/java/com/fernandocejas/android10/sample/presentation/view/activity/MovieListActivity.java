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
import com.fernandocejas.android10.sample.presentation.model.MovieModel;
import com.fernandocejas.android10.sample.presentation.view.fragment.MovieListFragment;

/**
 * Activity that shows a list of Movies.
 */
public class MovieListActivity extends BaseActivity implements HasComponent<MovieComponent>,
    MovieListFragment.MovieListListener {

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, MovieListActivity.class);
  }

  private MovieComponent movieComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_list);

    this.initializeInjector();
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

  @Override public void onMovieClicked(MovieModel movieModel) {
    this.navigator.navigateToMovieDetails(this, movieModel.getId());
  }
}
