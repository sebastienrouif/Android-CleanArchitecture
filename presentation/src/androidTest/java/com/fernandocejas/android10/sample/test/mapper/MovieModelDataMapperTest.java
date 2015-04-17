/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.test.mapper;

import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.presentation.mapper.MovieModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.MovieModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class MovieModelDataMapperTest extends TestCase {

  private static final int FAKE_MOVIE_ID = 123;
  private static final String FAKE_TITLE = "Tony Stark";

  private MovieModelDataMapper movieModelDataMapper;

  @Override protected void setUp() throws Exception {
    super.setUp();
    movieModelDataMapper = new MovieModelDataMapper();
  }

  public void testTransformMovie() {
    Movie movie = createFakeMovie();
    MovieModel movieModel = movieModelDataMapper.transform(movie);

    assertThat(movieModel, is(instanceOf(MovieModel.class)));
    assertThat(movieModel.getId(), is(FAKE_MOVIE_ID));
    assertThat(movieModel.getTitle(), is(FAKE_TITLE));
  }

  public void testTransformMovieCollection() {
    Movie mockMovieOne = mock(Movie.class);
    Movie mockMovieTwo = mock(Movie.class);

    List<Movie> movieList = new ArrayList<Movie>(5);
    movieList.add(mockMovieOne);
    movieList.add(mockMovieTwo);

    Collection<MovieModel> movieModelList = movieModelDataMapper.transform(movieList);

    assertThat(movieModelList.toArray()[0], is(instanceOf(MovieModel.class)));
    assertThat(movieModelList.toArray()[1], is(instanceOf(MovieModel.class)));
    assertThat(movieModelList.size(), is(2));
  }

  private Movie createFakeMovie() {
    Movie movie = new Movie();
    movie.setTitle(FAKE_TITLE);
    movie.setId(FAKE_MOVIE_ID);

    return movie;
  }
}
