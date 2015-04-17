/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view;

import com.fernandocejas.android10.sample.presentation.model.MovieModel;
import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link MovieModel}.
 */
public interface MovieListView extends LoadDataView {
  /**
   * Adds a movie list in the UI.
   *
   * @param movieModelCollection The collection of {@link MovieModel} that will be shown.
   */
  void addMovieList(Collection<MovieModel> movieModelCollection);

  /**
   * View a {@link MovieModel} profile/details.
   *
   * @param movieModel The movie that will be shown.
   */
  void viewMovie(MovieModel movieModel);
}
