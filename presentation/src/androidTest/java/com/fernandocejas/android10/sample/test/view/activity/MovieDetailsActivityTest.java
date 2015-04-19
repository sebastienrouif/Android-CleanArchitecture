/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.test.view.activity;

import android.app.Fragment;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.activity.MovieDetailsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class MovieDetailsActivityTest extends ActivityInstrumentationTestCase2<MovieDetailsActivity> {

    private static final int FAKE_MOVIE_ID = 263109;

    private MovieDetailsActivity movieDetailsActivity;

    public MovieDetailsActivityTest() {
        super(MovieDetailsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.setActivityIntent(createTargetIntent());
        this.movieDetailsActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testContainsMovieDetailsFragment() {
        Fragment movieDetailsFragment =
                movieDetailsActivity.getFragmentManager().findFragmentById(R.id.fl_fragment);
        assertThat(movieDetailsFragment, is(notNullValue()));
    }

    public void testContainsProperTitle() {
        String actualTitle = this.movieDetailsActivity.getTitle().toString().trim();

        assertThat(actualTitle, is("Movie Details"));
    }

    public void testLoadMovieHappyCaseViews() {
        onView(withId(R.id.rl_retry)).check(matches(not(isDisplayed())));
        onView(withId(R.id.rl_progress)).check(matches(not(isDisplayed())));

        onView(withId(R.id.tv_fullname)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_detail_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_detail_rating)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_detail_release_date)).check(matches(isDisplayed()));
    }

    public void testLoadMovieHappyCaseData() {
        onView(withId(R.id.tv_fullname)).check(matches(withText("Shaun the Sheep Movie")));
    }

    private Intent createTargetIntent() {
        Intent intentLaunchActivity =
                MovieDetailsActivity.getCallingIntent(getInstrumentation().getTargetContext(), FAKE_MOVIE_ID);

        return intentLaunchActivity;
    }
}
