/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.mapper.MovieEntityDataMapper;
import com.fernandocejas.android10.sample.data.exception.RepositoryErrorBundle;
import com.fernandocejas.android10.sample.data.repository.datasource.MovieDataStore;
import com.fernandocejas.android10.sample.data.repository.datasource.MovieDataStoreFactory;
import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.repository.MovieRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class MovieDataRepositoryTest extends ApplicationTestCase {

  private static final int FAKE_MOVIE_ID = 123;

  private MovieDataRepository movieDataRepository;

  @Mock
  private MovieDataStoreFactory mockMovieDataStoreFactory;
  @Mock
  private MovieEntityDataMapper mockMovieEntityDataMapper;
  @Mock
  private MovieDataStore mockMovieDataStore;
  @Mock
  private MovieEntity mockMovieEntity;
  @Mock
  private Movie mockMovie;
  @Mock
  private MovieRepository.MovieDetailsCallback mockMovieDetailsRepositoryCallback;
  @Mock
  private MovieRepository.MovieListCallback mockMovieListRepositoryCallback;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    resetSingleton(MovieDataRepository.class);
    movieDataRepository = new MovieDataRepository(mockMovieDataStoreFactory,
        mockMovieEntityDataMapper);

    given(mockMovieDataStoreFactory.create(anyInt())).willReturn(mockMovieDataStore);
    given(mockMovieDataStoreFactory.createCloudDataStore()).willReturn(mockMovieDataStore);
  }

  @Test
  public void testGetMovieDetailsSuccess() {
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((MovieDataStore.MovieDetailsCallback) invocation.getArguments()[1]).onMovieEntityLoaded(
            mockMovieEntity);
        return null;
      }
    }).when(mockMovieDataStore).getMovieEntityDetails(anyInt(),
        any(MovieDataStore.MovieDetailsCallback.class));
    given(mockMovieEntityDataMapper.transform(mockMovieEntity)).willReturn(mockMovie);

    movieDataRepository.getMovieById(FAKE_MOVIE_ID, mockMovieDetailsRepositoryCallback);

    verify(mockMovieEntityDataMapper).transform(mockMovieEntity);
    verify(mockMovieDetailsRepositoryCallback).onMovieLoaded(mockMovie);
  }

  @Test
  public void testGetMovieDetailsNullResult() {
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((MovieDataStore.MovieDetailsCallback) invocation.getArguments()[1]).onMovieEntityLoaded(
            mockMovieEntity);
        return null;
      }
    }).when(mockMovieDataStore).getMovieEntityDetails(anyInt(),
        any(MovieDataStore.MovieDetailsCallback.class));
    given(mockMovieEntityDataMapper.transform(mockMovieEntity)).willReturn(null);

    doNothing().when(mockMovieDetailsRepositoryCallback).onError(any(RepositoryErrorBundle.class));

    movieDataRepository.getMovieById(FAKE_MOVIE_ID, mockMovieDetailsRepositoryCallback);

    verify(mockMovieEntityDataMapper).transform(mockMovieEntity);
    verify(mockMovieDetailsRepositoryCallback).onError(any(RepositoryErrorBundle.class));
  }

  @Test
  public void testGetMovieByIdError() {
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((MovieDataStore.MovieDetailsCallback) invocation.getArguments()[1]).onError(
            any(Exception.class));
        return null;
      }
    }).when(mockMovieDataStore).getMovieEntityDetails(anyInt(),
        any(MovieDataStore.MovieDetailsCallback.class));

    movieDataRepository.getMovieById(FAKE_MOVIE_ID, mockMovieDetailsRepositoryCallback);

    verify(mockMovieDetailsRepositoryCallback).onError(any(RepositoryErrorBundle.class));
    verifyZeroInteractions(mockMovieEntityDataMapper);
  }
}
