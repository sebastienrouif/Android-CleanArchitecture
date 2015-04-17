/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.cache;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.cache.serializer.JsonSerializer;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import java.io.File;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

public class MovieCacheTest extends ApplicationTestCase {

    private static final int FAKE_MOVIE_ID = 123;

    private MovieCache movieCache;

    @Mock
    private JsonSerializer mockJsonSerializer;
    @Mock
    private FileManager mockFileManager;
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private MovieCache.MovieCacheCallback mockMovieCacheCallback;
    @Mock
    private MovieEntity mockMovieEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        movieCache = new MovieCacheImpl(Robolectric.application, mockJsonSerializer,
                mockFileManager, mockThreadExecutor);
    }

    @Test
    public void testGetFromCacheHappyCase() {
        given(mockJsonSerializer.deserialize(anyString(), any(Class.class))).willReturn(mockMovieEntity);

        movieCache.get(FAKE_MOVIE_ID, mockMovieCacheCallback);

        verify(mockFileManager).readFileContent(any(File.class));
        verify(mockJsonSerializer).deserialize(anyString(), any(Class.class));
        verify(mockMovieCacheCallback).onMovieEntityLoaded(mockMovieEntity);
    }
}
