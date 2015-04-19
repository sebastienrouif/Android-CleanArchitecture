/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.cache.MovieCache;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.PaginatedMoviesEntity;
import com.fernandocejas.android10.sample.data.net.RestApi;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class CloudMovieDataStoreTest extends ApplicationTestCase {

    private static final int FAKE_MOVIE_ID = 765;
    private static final int FAKE_PAGE = 1;

    private CloudMovieDataStore cloudMovieDataStore;

    @Mock
    private RestApi mockRestApi;
    @Mock
    private MovieCache mockMovieCache;
    @Mock
    private MovieDataStore.MovieListCallback mockMovieListDataStoreCallback;
    @Mock
    private MovieDataStore.MovieDetailsCallback mockMovieDetailsDataStoreCallback;
    @Captor
    private ArgumentCaptor<RestApi.MovieListCallback> restApiMovieListCallbackArgumentCaptor;
    @Captor
    private ArgumentCaptor<RestApi.MovieDetailsCallback> restApiMovieDetailsCallbackArgumentCaptor;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cloudMovieDataStore = new CloudMovieDataStore(mockRestApi, mockMovieCache);
    }

    @Test
    public void testGetMovieEntityDetailsSuccessfully() {
        MovieEntity mockMovieEntity = mock(MovieEntity.class);

        cloudMovieDataStore.getMovieEntityDetails(FAKE_MOVIE_ID, mockMovieDetailsDataStoreCallback);

        verify(mockRestApi).getMovieById(anyInt(), restApiMovieDetailsCallbackArgumentCaptor.capture());
        verifyZeroInteractions(mockMovieDetailsDataStoreCallback);
        verifyZeroInteractions(mockMovieCache);

        restApiMovieDetailsCallbackArgumentCaptor.getValue().onMovieEntityLoaded(mockMovieEntity);

        verify(mockMovieDetailsDataStoreCallback).onMovieEntityLoaded(mockMovieEntity);
        verify(mockMovieCache).putMovie(mockMovieEntity);
    }

    @Test
    public void testGetMovieEntityDetailsError() {
        cloudMovieDataStore.getMovieEntityDetails(FAKE_MOVIE_ID, mockMovieDetailsDataStoreCallback);

        verify(mockRestApi).getMovieById(anyInt(), restApiMovieDetailsCallbackArgumentCaptor.capture());
        verifyZeroInteractions(mockMovieDetailsDataStoreCallback);

        restApiMovieDetailsCallbackArgumentCaptor.getValue().onError(any(Exception.class));

        verify(mockMovieDetailsDataStoreCallback).onError(any(Exception.class));
        verifyZeroInteractions(mockMovieCache);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetMovieEntityListSuccessfully() {

        PaginatedMoviesEntity paginatedMoviesEntity = mock(PaginatedMoviesEntity.class);

        cloudMovieDataStore.getMoviesEntityList(FAKE_PAGE, mockMovieListDataStoreCallback);

        verify(mockRestApi).getMovieList(anyInt(), restApiMovieListCallbackArgumentCaptor.capture());
        verifyZeroInteractions(mockMovieListDataStoreCallback);
        verifyZeroInteractions(mockMovieCache);

        restApiMovieListCallbackArgumentCaptor.getValue().onMovieListLoaded(paginatedMoviesEntity);

        verify(mockMovieListDataStoreCallback).onMovieListLoaded(paginatedMoviesEntity);
    }

    @Test
    public void testGetMovieEntityListError() {
        cloudMovieDataStore.getMoviesEntityList(FAKE_PAGE, mockMovieListDataStoreCallback);

        verify(mockRestApi).getMovieList(anyInt(), restApiMovieListCallbackArgumentCaptor.capture());
        verifyZeroInteractions(mockMovieListDataStoreCallback);

        restApiMovieListCallbackArgumentCaptor.getValue().onError(any(Exception.class));

        verify(mockMovieListDataStoreCallback).onError(any(Exception.class));
        verifyZeroInteractions(mockMovieCache);
    }
}
