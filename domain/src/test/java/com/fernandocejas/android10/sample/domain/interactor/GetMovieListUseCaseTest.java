/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.PaginatedMovies;
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

import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetMovieListUseCaseTest {

    private static final int FAKE_PAGE = 1;
    private GetMovieListUseCase getMovieListUseCase;

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private MovieRepository mockMovieRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        getMovieListUseCase = new GetMovieListUseCaseImpl(mockMovieRepository, mockThreadExecutor,
                mockPostExecutionThread);
    }

    @Test
    public void testGetMovieListUseCaseExecution() {
        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));

        GetMovieListUseCase.Callback mockGetMovieListCallback = mock(GetMovieListUseCase.Callback.class);

        getMovieListUseCase.execute(FAKE_PAGE, mockGetMovieListCallback);

        verify(mockThreadExecutor).execute(any(Interactor.class));
        verifyNoMoreInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockMovieRepository);
        verifyZeroInteractions(mockPostExecutionThread);
    }

    @Test
    public void testGetMovieListUseCaseInteractorRun() {
        GetMovieListUseCase.Callback mockGetMovieListCallback = mock(GetMovieListUseCase.Callback.class);

        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doNothing().when(mockMovieRepository).getMovieList(anyInt(), any(MovieRepository.MovieListCallback.class));

        getMovieListUseCase.execute(FAKE_PAGE, mockGetMovieListCallback);
        getMovieListUseCase.run();

        verify(mockMovieRepository).getMovieList(anyInt(), any(MovieRepository.MovieListCallback.class));
        verify(mockThreadExecutor).execute(any(Interactor.class));
        verifyNoMoreInteractions(mockMovieRepository);
        verifyNoMoreInteractions(mockThreadExecutor);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testMovieListUseCaseCallbackSuccessful() {
        final GetMovieListUseCase.Callback mockGetMovieListCallback =
                mock(GetMovieListUseCase.Callback.class);
        final PaginatedMovies mockResponseMovieList =  mock(PaginatedMovies.class);

        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((MovieRepository.MovieListCallback) invocation.getArguments()[1]).onMovieListLoaded(
                        mockResponseMovieList);
                return null;
            }
        }).when(mockMovieRepository).getMovieList(anyInt(), any(MovieRepository.MovieListCallback.class));

        getMovieListUseCase.execute(FAKE_PAGE, mockGetMovieListCallback);
        getMovieListUseCase.run();

        verify(mockPostExecutionThread).post(any(Runnable.class));
        verifyNoMoreInteractions(mockGetMovieListCallback);
        verifyZeroInteractions(mockResponseMovieList);
    }

    @Test
    public void testMovieListUseCaseCallbackError() {
        final GetMovieListUseCase.Callback mockGetMovieListUseCaseCallback =
                mock(GetMovieListUseCase.Callback.class);
        final ErrorBundle mockErrorBundle = mock(ErrorBundle.class);

        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((MovieRepository.MovieListCallback) invocation.getArguments()[1]).onError(mockErrorBundle);
                return null;
            }
        }).when(mockMovieRepository).getMovieList(anyInt(), any(MovieRepository.MovieListCallback.class));

        getMovieListUseCase.execute(FAKE_PAGE, mockGetMovieListUseCaseCallback);
        getMovieListUseCase.run();

        verify(mockPostExecutionThread).post(any(Runnable.class));
        verifyNoMoreInteractions(mockGetMovieListUseCaseCallback);
        verifyZeroInteractions(mockErrorBundle);
    }
}
