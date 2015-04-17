/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.PaginatedMoviesEntity;
import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.PaginatedMovies;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link MovieEntity} (in the data layer) to {@link Movie} in the
 * domain layer.
 */
@Singleton
public class MovieEntityDataMapper {

  @Inject
  public MovieEntityDataMapper() {}

  /**
   * Transform a {@link MovieEntity} into an {@link Movie}.
   *
   * @param movieEntity Object to be transformed.
   * @return {@link Movie} if valid {@link MovieEntity} otherwise null.
   */
  public Movie transform(MovieEntity movieEntity) {
    Movie movie = null;
    if (movieEntity != null) {
      Gson gson = new Gson();
      movie = gson.fromJson(gson.toJson(movieEntity), Movie.class);
    }

    return movie;
  }

  /**
   * Transform a Collection of {@link MovieEntity} into a Collection of {@link Movie}.
   *
   * @param paginatedMoviesEntity
   * @return {@link Movie} if valid {@link MovieEntity} otherwise null.
   */
  public PaginatedMovies transform(PaginatedMoviesEntity paginatedMoviesEntity) {
      PaginatedMovies paginatedMovies = null;
      if (paginatedMoviesEntity != null) {
          Gson gson = new Gson();
          paginatedMovies = gson.fromJson(gson.toJson(paginatedMoviesEntity), PaginatedMovies.class);
      }

      return paginatedMovies;
  }
}
