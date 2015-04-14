/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.test.view.activity;

import android.app.Fragment;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.activity.MovieListActivity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MovieListActivityTest extends ActivityInstrumentationTestCase2<MovieListActivity> {

  private MovieListActivity movieListActivity;

  public MovieListActivityTest() {
    super(MovieListActivity.class);
  }

  @Override protected void setUp() throws Exception {
    super.setUp();
    this.setActivityIntent(createTargetIntent());
    movieListActivity = getActivity();
  }

  @Override protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testContainsMovieListFragment() {
    Fragment movieListFragment =
        movieListActivity.getFragmentManager().findFragmentById(R.id.fragmentMovieList);
    assertThat(movieListFragment, is(notNullValue()));
  }

  public void testContainsProperTitle() {
    String actualTitle = this.movieListActivity.getTitle().toString().trim();

    assertThat(actualTitle, is("Movies List"));
  }

  private Intent createTargetIntent() {
    Intent intentLaunchActivity =
        MovieListActivity.getCallingIntent(getInstrumentation().getTargetContext());

    return intentLaunchActivity;
  }
}
