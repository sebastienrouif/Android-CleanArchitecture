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

    private static final String JSON_MOVIE_RESPONSE = "{ " +
            "adult: false," +
            "backdrop_path: \"/c1OSRvorPXvGtFka7mgV6Jcw6jd.jpg\"," +
            "id: 168259," +
            "original_title: \"Furious 7\"," +
            "release_date: \"2015-04-03\"," +
            "poster_path: \"/dCgm7efXDmiABSdWDHBDBx2jwmn.jpg\"," +
            "popularity: 36.2784739100617," +
            "title: \"Furious 7\"," +
            "video: false," +
            "vote_average: 7.7," +
            "vote_count: 317" +
            "}";

    private JsonSerializer jsonSerializer;

    @Before
    public void setUp() {
        jsonSerializer = new JsonSerializer();
    }

    @Test
    public void testSerializeHappyCase() {
        MovieEntity movieEntityOne = jsonSerializer.deserialize(JSON_MOVIE_RESPONSE, MovieEntity.class);
        String jsonString = jsonSerializer.serialize(movieEntityOne);
        MovieEntity movieEntityTwo = jsonSerializer.deserialize(jsonString, MovieEntity.class);

        //TODO test all fields
        assertThat(movieEntityOne.getId(), is(movieEntityTwo.getId()));
        assertThat(movieEntityOne.getTitle(), is(equalTo(movieEntityTwo.getTitle())));
        assertThat(movieEntityOne.getPosterPath(), is(movieEntityTwo.getPosterPath()));
    }

    @Test
    public void testDesearializeHappyCase() {
        MovieEntity movieEntity = jsonSerializer.deserialize(JSON_MOVIE_RESPONSE, MovieEntity.class);

        assertThat(movieEntity.getId(), is(168259));
        assertThat(movieEntity.getTitle(), is("Furious 7"));
        assertThat(movieEntity.getPosterPath(), is("/dCgm7efXDmiABSdWDHBDBx2jwmn.jpg"));
    }
}
