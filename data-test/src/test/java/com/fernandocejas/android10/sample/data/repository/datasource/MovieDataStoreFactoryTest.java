/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.cache.MovieCache;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class MovieDataStoreFactoryTest extends ApplicationTestCase {

  private static final int FAKE_MOVIE_ID = 11;

  private MovieDataStoreFactory movieDataStoreFactory;

  @Mock
  private MovieCache mockMovieCache;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    movieDataStoreFactory =
        new MovieDataStoreFactory(Robolectric.application, mockMovieCache);
  }

  @Test
  public void testCreateDiskDataStore() {
    given(mockMovieCache.isCached(FAKE_MOVIE_ID)).willReturn(true);
    given(mockMovieCache.isExpired()).willReturn(false);

    MovieDataStore movieDataStore = movieDataStoreFactory.create(FAKE_MOVIE_ID);

    assertThat(movieDataStore, is(notNullValue()));
    assertThat(movieDataStore, is(instanceOf(DiskMovieDataStore.class)));

    verify(mockMovieCache).isCached(FAKE_MOVIE_ID);
    verify(mockMovieCache).isExpired();
  }

  @Test
  public void testCreateCloudDataStore() {
    given(mockMovieCache.isExpired()).willReturn(true);
    given(mockMovieCache.isCached(FAKE_MOVIE_ID)).willReturn(false);

    MovieDataStore movieDataStore = movieDataStoreFactory.create(FAKE_MOVIE_ID);

    assertThat(movieDataStore, is(notNullValue()));
    assertThat(movieDataStore, is(instanceOf(CloudMovieDataStore.class)));

    verify(mockMovieCache).isExpired();
  }
}
