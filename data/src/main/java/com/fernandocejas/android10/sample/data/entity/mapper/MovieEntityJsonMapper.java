/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class MovieEntityJsonMapper {

  private final Gson gson;

  @Inject
  public MovieEntityJsonMapper() {
    this.gson = new Gson();
  }

  /**
   * Transform from valid json string to {@link MovieEntity}.
   *
   * @param movieJsonResponse A json representing a movie profile.
   * @return {@link MovieEntity}.
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  public MovieEntity transformMovieEntity(String movieJsonResponse) throws JsonSyntaxException {
    try {
      Type movieEntityType = new TypeToken<MovieEntity>() {}.getType();
      MovieEntity movieEntity = this.gson.fromJson(movieJsonResponse, movieEntityType);

      return movieEntity;
    } catch (JsonSyntaxException jsonException) {
      throw jsonException;
    }
  }

  /**
   * Transform from valid json string to Collection of {@link MovieEntity}.
   *
   * @param movieListJsonResponse A json representing a collection of movies.
   * @return Collection of {@link MovieEntity}.
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  public Collection<MovieEntity> transformMovieEntityCollection(String movieListJsonResponse)
      throws JsonSyntaxException {

    Collection<MovieEntity> movieEntityCollection;
    try {
      Type listOfMovieEntityType = new TypeToken<Collection<MovieEntity>>() {}.getType();
      movieEntityCollection = this.gson.fromJson(movieListJsonResponse, listOfMovieEntityType);

      return movieEntityCollection;
    } catch (JsonSyntaxException jsonException) {
      throw jsonException;
    }
  }
}
