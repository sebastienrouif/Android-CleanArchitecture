/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MovieTest {

  private static final int FAKE_MOVIE_ID = 8;

  private Movie movie;

  @Before
  public void setUp() {
    movie = new Movie();
    movie.setId(FAKE_MOVIE_ID);
  }

  @Test
  public void testMovieConstructorHappyCase() {
    int movieId = movie.getId();

    assertThat(movieId, is(FAKE_MOVIE_ID));
  }
}
