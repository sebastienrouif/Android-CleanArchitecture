/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.domain.Movie;
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
      movie = new Movie(movieEntity.getMovieId());
      movie.setCoverUrl(movieEntity.getCoverUrl());
      movie.setFullName(movieEntity.getFullname());
      movie.setDescription(movieEntity.getDescription());
      movie.setFollowers(movieEntity.getFollowers());
      movie.setEmail(movieEntity.getEmail());
    }

    return movie;
  }

  /**
   * Transform a Collection of {@link MovieEntity} into a Collection of {@link Movie}.
   *
   * @param movieEntityCollection Object Collection to be transformed.
   * @return {@link Movie} if valid {@link MovieEntity} otherwise null.
   */
  public Collection<Movie> transform(Collection<MovieEntity> movieEntityCollection) {
    List<Movie> movieList = new ArrayList<Movie>(20);
    Movie movie;
    for (MovieEntity movieEntity : movieEntityCollection) {
      movie = transform(movieEntity);
      if (movie != null) {
        movieList.add(movie);
      }
    }

    return movieList;
  }
}
