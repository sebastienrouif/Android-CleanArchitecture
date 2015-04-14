/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.domain.Movie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class MovieEntityDataMapperTest extends ApplicationTestCase {

  private static final int FAKE_USER_ID = 123;
  private static final String FAKE_FULLNAME = "Tony Stark";

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
    assertThat(movie.getMovieId(), is(FAKE_USER_ID));
    assertThat(movie.getFullName(), is(FAKE_FULLNAME));
  }

  @Test
  public void testTransformMovieEntityCollection() {
    MovieEntity mockMovieEntityOne = mock(MovieEntity.class);
    MovieEntity mockMovieEntityTwo = mock(MovieEntity.class);

    List<MovieEntity> movieEntityList = new ArrayList<MovieEntity>(5);
    movieEntityList.add(mockMovieEntityOne);
    movieEntityList.add(mockMovieEntityTwo);

    Collection<Movie> movieCollection = movieEntityDataMapper.transform(movieEntityList);

    assertThat(movieCollection.toArray()[0], is(instanceOf(Movie.class)));
    assertThat(movieCollection.toArray()[1], is(instanceOf(Movie.class)));
    assertThat(movieCollection.size(), is(2));
  }

  private MovieEntity createFakeMovieEntity() {
    MovieEntity movieEntity = new MovieEntity();
    movieEntity.setMovieId(FAKE_USER_ID);
    movieEntity.setFullname(FAKE_FULLNAME);

    return movieEntity;
  }
}
