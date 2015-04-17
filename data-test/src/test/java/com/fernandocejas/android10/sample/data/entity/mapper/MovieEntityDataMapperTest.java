/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.PaginatedMoviesEntity;
import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.PaginatedMovies;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class MovieEntityDataMapperTest extends ApplicationTestCase {

    private static final int FAKE_MOVIE_ID = 123;
    private static final String FAKE_TITLE = "Tony Stark";


    private static final int FAKE_PAGE = 1;

    private static final int FAKE_TOTAL_PAGES = 20;

    private static final int FAKE_TOTAL_RESULTS = 211;

    private MovieEntityDataMapper movieEntityDataMapper;

    @Before
    public void setUp() throws Exception {
        movieEntityDataMapper = new MovieEntityDataMapper();
    }

    @Test
    public void testTransformMovieEntity() {
        MovieEntity movieEntity = createFakeMovieEntity();
        Movie movie = movieEntityDataMapper.transform(movieEntity);

        assertThat(movie, is(instanceOf(Movie.class)));
        assertThat(movie.getId(), is(FAKE_MOVIE_ID));
        assertThat(movie.getTitle(), is(FAKE_TITLE));
    }

    @Test
    public void testTransformPaginatedMoviesEntity() {
        PaginatedMoviesEntity paginatedMoviesEntity = createFakePaginatedMovieEntity();
        PaginatedMovies paginatedMovies = movieEntityDataMapper.transform(paginatedMoviesEntity);

        assertThat(paginatedMovies, is(instanceOf(PaginatedMovies.class)));
        assertThat(paginatedMovies.getPage(), is(FAKE_PAGE));
        assertThat(paginatedMovies.getTotalPages(), is(FAKE_TOTAL_PAGES));
        assertThat(paginatedMovies.getTotalResults(), is(FAKE_TOTAL_RESULTS));
    }

    private MovieEntity createFakeMovieEntity() {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setTitle(FAKE_TITLE);
        movieEntity.setId(FAKE_MOVIE_ID);

        return movieEntity;
    }

    private PaginatedMoviesEntity createFakePaginatedMovieEntity() {
        PaginatedMoviesEntity paginatedMoviesEntity = new PaginatedMoviesEntity();
        paginatedMoviesEntity.setPage(FAKE_PAGE);
        paginatedMoviesEntity.setTotalPages(FAKE_TOTAL_PAGES);
        paginatedMoviesEntity.setTotalResults(FAKE_TOTAL_RESULTS);

        return paginatedMoviesEntity;
    }
}
