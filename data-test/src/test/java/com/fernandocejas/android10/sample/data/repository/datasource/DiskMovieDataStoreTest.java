/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.cache.MovieCache;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.PaginatedMoviesEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DiskMovieDataStoreTest extends ApplicationTestCase {

  private static final int FAKE_MOVIE_ID = 11;
  private static final int FAKE_MOVIE_PAGE = 11;

  private DiskMovieDataStore diskMovieDataStore;

  @Mock
  private MovieCache mockMovieCache;
  @Mock
  private MovieDataStore.MovieDetailsCallback mockMovieDetailsDataStoreCallback;
  @Captor
  private ArgumentCaptor<MovieCache.MovieCacheCallback> movieCacheCallbackArgumentCaptor;
  @Mock
  private MovieDataStore.MovieListCallback mockMovieListDataStoreCallback;
  @Captor
  private ArgumentCaptor<MovieCache.PaginatedMovieCacheCallback> movieListDataStoreCallbackArgumentCaptor;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    diskMovieDataStore = new DiskMovieDataStore(mockMovieCache);
  }

  @Test
  public void testGetMovieEntityByIdSuccessfully() {
    MovieEntity mockMovieEntity = mock(MovieEntity.class);

    diskMovieDataStore.getMovieEntityDetails(FAKE_MOVIE_ID, mockMovieDetailsDataStoreCallback);

    verify(mockMovieCache).getMovie(anyInt(), movieCacheCallbackArgumentCaptor.capture());
    verifyZeroInteractions(mockMovieDetailsDataStoreCallback);

    movieCacheCallbackArgumentCaptor.getValue().onMovieEntityLoaded(mockMovieEntity);

    verify(mockMovieDetailsDataStoreCallback).onMovieEntityLoaded(mockMovieEntity);
  }

  @Test
  public void testGetMovieEntityByIdError() {
    diskMovieDataStore.getMovieEntityDetails(FAKE_MOVIE_ID, mockMovieDetailsDataStoreCallback);

    verify(mockMovieCache).getMovie(anyInt(), movieCacheCallbackArgumentCaptor.capture());
    verifyZeroInteractions(mockMovieDetailsDataStoreCallback);

    movieCacheCallbackArgumentCaptor.getValue().onError(any(Exception.class));

    verify(mockMovieDetailsDataStoreCallback).onError(any(Exception.class));
  }

  @Test
  public void testGetMovieEntityList() {
    PaginatedMoviesEntity paginatedMoviesEntity = mock(PaginatedMoviesEntity.class);

    diskMovieDataStore.getMoviesEntityList(FAKE_MOVIE_PAGE, mockMovieListDataStoreCallback);

    verify(mockMovieCache).getPaginatedMovies(anyInt(), movieListDataStoreCallbackArgumentCaptor.capture());
    verifyZeroInteractions(mockMovieDetailsDataStoreCallback);

    movieListDataStoreCallbackArgumentCaptor.getValue().onMovieEntityLoaded(paginatedMoviesEntity);

    verify(mockMovieListDataStoreCallback).onMovieListLoaded(paginatedMoviesEntity);
  }
}
