/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.cache.serializer;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonSerializerTest extends ApplicationTestCase {

  private static final String JSON_RESPONSE = "{\n"
      + "    \"id\": 1,\n"
      + "    \"cover_url\": \"http://www.android10.org/myapi/cover_1.jpg\",\n"
      + "    \"full_name\": \"Simon Hill\",\n"
      + "    \"description\": \"Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem.\\n\\nInteger tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.\\n\\nPraesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.\",\n"
      + "    \"followers\": 7484,\n"
      + "    \"email\": \"jcooper@babbleset.edu\"\n"
      + "}";

  private JsonSerializer jsonSerializer;

  @Before
  public void setUp() {
    jsonSerializer = new JsonSerializer();
  }

  @Test
  public void testSerializeHappyCase() {
    MovieEntity movieEntityOne = jsonSerializer.deserialize(JSON_RESPONSE);
    String jsonString = jsonSerializer.serialize(movieEntityOne);
    MovieEntity movieEntityTwo = jsonSerializer.deserialize(jsonString);

    assertThat(movieEntityOne.getMovieId(), is(movieEntityTwo.getMovieId()));
    assertThat(movieEntityOne.getFullname(), is(equalTo(movieEntityTwo.getFullname())));
    assertThat(movieEntityOne.getFollowers(), is(movieEntityTwo.getFollowers()));
  }

  @Test
  public void testDesearializeHappyCase() {
    MovieEntity movieEntity = jsonSerializer.deserialize(JSON_RESPONSE);

    assertThat(movieEntity.getMovieId(), is(1));
    assertThat(movieEntity.getFullname(), is("Simon Hill"));
    assertThat(movieEntity.getFollowers(), is(7484));
  }
}
