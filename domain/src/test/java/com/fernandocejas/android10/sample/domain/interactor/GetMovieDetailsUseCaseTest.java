/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetMovieDetailsUseCaseTest {

  private static final int FAKE_MOVIE_ID = 123;

  private GetMovieDetailsUseCase getMovieDetailsUseCase;

  @Mock
  private ThreadExecutor mockThreadExecutor;
  @Mock
  private PostExecutionThread mockPostExecutionThread;
  @Mock
  private MovieRepository mockMovieRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    getMovieDetailsUseCase = new GetMovieDetailsUseCaseImpl(mockMovieRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetMovieDetailsUseCaseExecution() {
    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));

    GetMovieDetailsUseCase.Callback mockGetMovieDetailsCallback =
        mock(GetMovieDetailsUseCase.Callback.class);

    getMovieDetailsUseCase.execute(FAKE_MOVIE_ID, mockGetMovieDetailsCallback);

    verify(mockThreadExecutor).execute(any(Interactor.class));
    verifyNoMoreInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockMovieRepository);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test
  public void testGetMovieDetailsUseCaseInteractorRun() {
    GetMovieDetailsUseCase.Callback mockGetMovieDetailsCallback =
        mock(GetMovieDetailsUseCase.Callback.class);

    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
    doNothing().when(mockMovieRepository)
        .getMovieById(anyInt(), any(MovieRepository.MovieDetailsCallback.class));

    getMovieDetailsUseCase.execute(FAKE_MOVIE_ID, mockGetMovieDetailsCallback);
    getMovieDetailsUseCase.run();

    verify(mockMovieRepository).getMovieById(anyInt(), any(MovieRepository.MovieDetailsCallback.class));
    verify(mockThreadExecutor).execute(any(Interactor.class));
    verifyNoMoreInteractions(mockMovieRepository);
    verifyNoMoreInteractions(mockThreadExecutor);
  }

  @Test
  public void testMovieDetailsUseCaseCallbackSuccessful() {
    final GetMovieDetailsUseCase.Callback mockGetMovieDetailsCallback =
        mock(GetMovieDetailsUseCase.Callback.class);
    final Movie mockResponseMovie = mock(Movie.class);

    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((MovieRepository.MovieDetailsCallback) invocation.getArguments()[1]).onMovieLoaded(
            mockResponseMovie);
        return null;
      }
    }).when(mockMovieRepository)
        .getMovieById(anyInt(), any(MovieRepository.MovieDetailsCallback.class));

    getMovieDetailsUseCase.execute(FAKE_MOVIE_ID, mockGetMovieDetailsCallback);
    getMovieDetailsUseCase.run();

    verify(mockPostExecutionThread).post(any(Runnable.class));
    verifyNoMoreInteractions(mockGetMovieDetailsCallback);
    verifyZeroInteractions(mockResponseMovie);
  }

  @Test
  public void testMovieDetailsUseCaseCallbackError() {
    final GetMovieDetailsUseCase.Callback mockGetMovieDetailsCallback =
        mock(GetMovieDetailsUseCase.Callback.class);
    final ErrorBundle mockErrorHandler = mock(ErrorBundle.class);

    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((MovieRepository.MovieDetailsCallback) invocation.getArguments()[1]).onError(
            mockErrorHandler);
        return null;
      }
    }).when(mockMovieRepository)
        .getMovieById(anyInt(), any(MovieRepository.MovieDetailsCallback.class));

    getMovieDetailsUseCase.execute(FAKE_MOVIE_ID, mockGetMovieDetailsCallback);
    getMovieDetailsUseCase.run();

    verify(mockPostExecutionThread).post(any(Runnable.class));
    verifyNoMoreInteractions(mockGetMovieDetailsCallback);
    verifyZeroInteractions(mockErrorHandler);
  }
}
