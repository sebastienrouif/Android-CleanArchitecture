/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MovieEntityJsonMapperTest extends ApplicationTestCase {

  private static final String JSON_RESPONSE_USER_DETAILS = "{\n"
      + "    \"id\": 1,\n"
      + "    \"cover_url\": \"http://www.android10.org/myapi/cover_1.jpg\",\n"
      + "    \"full_name\": \"Simon Hill\",\n"
      + "    \"description\": \"Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem.\\n\\nInteger tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.\\n\\nPraesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.\",\n"
      + "    \"followers\": 7484,\n"
      + "    \"email\": \"jcooper@babbleset.edu\"\n"
      + "}";

  private static final String JSON_RESPONSE_USER_COLLECTION = "[{\n"
      + "    \"id\": 1,\n"
      + "    \"full_name\": \"Simon Hill\",\n"
      + "    \"followers\": 7484\n"
      + "}, {\n"
      + "    \"id\": 12,\n"
      + "    \"full_name\": \"Pedro Garcia\",\n"
      + "    \"followers\": 1381\n"
      + "}]";

  private MovieEntityJsonMapper movieEntityJsonMapper;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    movieEntityJsonMapper = new MovieEntityJsonMapper();
  }

  @Test
  public void testTransformMovieEntityHappyCase() {
    MovieEntity movieEntity = movieEntityJsonMapper.transformMovieEntity(JSON_RESPONSE_USER_DETAILS);

    assertThat(movieEntity.getMovieId(), is(1));
    assertThat(movieEntity.getFullname(), is(equalTo("Simon Hill")));
    assertThat(movieEntity.getEmail(), is(equalTo("jcooper@babbleset.edu")));
  }

  @Test
  public void testTransformMovieEntityCollectionHappyCase() {
    Collection<MovieEntity> movieEntityCollection =
        movieEntityJsonMapper.transformMovieEntityCollection(
            JSON_RESPONSE_USER_COLLECTION);

    assertThat(((MovieEntity) movieEntityCollection.toArray()[0]).getMovieId(), is(1));
    assertThat(((MovieEntity) movieEntityCollection.toArray()[1]).getMovieId(), is(12));
    assertThat(movieEntityCollection.size(), is(2));
  }

  @Test
  public void testTransformMovieEntityNotValidResponse() {
    expectedException.expect(JsonSyntaxException.class);
    movieEntityJsonMapper.transformMovieEntity("ironman");
  }

  @Test
  public void testTransformMovieEntityCollectionNotValidResponse() {
    expectedException.expect(JsonSyntaxException.class);
    movieEntityJsonMapper.transformMovieEntityCollection("Tony Stark");
  }
}
