/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.GetMovieListUseCase;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.mapper.MovieModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.MovieModel;
import com.fernandocejas.android10.sample.presentation.view.MovieListView;

import java.util.Collection;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class MovieListPresenter implements Presenter {

    private MovieListView viewListView;

    private final GetMovieListUseCase getMovieListUseCase;
    private final MovieModelDataMapper movieModelDataMapper;

    @Inject
    public MovieListPresenter(GetMovieListUseCase getMovieListMovieCase,
                              MovieModelDataMapper movieModelDataMapper) {
        this.getMovieListUseCase = getMovieListMovieCase;
        this.movieModelDataMapper = movieModelDataMapper;
    }

    public void setView(@NonNull MovieListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    /**
     * Initializes the presenter by start retrieving the movie list.
     */
    public void initialize() {
        loadMovieList();
    }

    /**
     * Loads all movies.
     */
    private void loadMovieList() {
        hideViewRetry();
        showViewLoading();
        getMovieList(0);
    }

    public void onMovieClicked(MovieModel movieModel) {
        this.viewListView.viewMovie(movieModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.getContext(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showMoviesCollectionInView(Collection<Movie> moviesCollection) {
        final Collection<MovieModel> movieModelsCollection =
                this.movieModelDataMapper.transform(moviesCollection);
        this.viewListView.renderMovieList(movieModelsCollection);
    }

    private void getMovieList(int page) {
        this.getMovieListUseCase.execute(page, movieListCallback);
    }

    private final GetMovieListUseCase.Callback movieListCallback = new GetMovieListUseCase.Callback() {
        @Override
        public void onMovieListLoaded(Collection<Movie> moviesCollection) {
            MovieListPresenter.this.showMoviesCollectionInView(moviesCollection);
            MovieListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MovieListPresenter.this.hideViewLoading();
            MovieListPresenter.this.showErrorMessage(errorBundle);
            MovieListPresenter.this.showViewRetry();
        }
    };

    public void loadMore(int current_page) {
        getMovieList(current_page);
    }
}
