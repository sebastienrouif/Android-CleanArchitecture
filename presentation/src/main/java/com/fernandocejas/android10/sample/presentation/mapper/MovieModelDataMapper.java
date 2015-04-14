/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.mapper;

import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.model.MovieModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Movie} (in the domain layer) to {@link MovieModel} in the
 * presentation layer.
 */
@PerActivity
public class MovieModelDataMapper {

  @Inject
  public MovieModelDataMapper() {}

  /**
   * Transform a {@link Movie} into an {@link MovieModel}.
   *
   * @param movie Object to be transformed.
   * @return {@link MovieModel}.
   */
  public MovieModel transform(Movie movie) {
    if (movie == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    MovieModel movieModel = new MovieModel(movie.getMovieId());
    movieModel.setCoverUrl(movie.getCoverUrl());
    movieModel.setFullName(movie.getFullName());
    movieModel.setEmail(movie.getEmail());
    movieModel.setDescription(movie.getDescription());
    movieModel.setFollowers(movie.getFollowers());

    return movieModel;
  }

  /**
   * Transform a Collection of {@link Movie} into a Collection of {@link MovieModel}.
   *
   * @param moviesCollection Objects to be transformed.
   * @return List of {@link MovieModel}.
   */
  public Collection<MovieModel> transform(Collection<Movie> moviesCollection) {
    Collection<MovieModel> movieModelsCollection;

    if (moviesCollection != null && !moviesCollection.isEmpty()) {
      movieModelsCollection = new ArrayList<MovieModel>();
      for (Movie movie : moviesCollection) {
        movieModelsCollection.add(transform(movie));
      }
    } else {
      movieModelsCollection = Collections.emptyList();
    }

    return movieModelsCollection;
  }
}
