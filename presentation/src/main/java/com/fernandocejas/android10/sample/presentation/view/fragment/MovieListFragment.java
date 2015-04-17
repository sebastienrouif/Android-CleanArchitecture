/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.MovieComponent;
import com.fernandocejas.android10.sample.presentation.model.MovieModel;
import com.fernandocejas.android10.sample.presentation.presenter.MovieListPresenter;
import com.fernandocejas.android10.sample.presentation.view.MovieListView;
import com.fernandocejas.android10.sample.presentation.view.adapter.MoviesAdapter;
import com.fernandocejas.android10.sample.presentation.view.adapter.MoviesLayoutManager;
import com.fernandocejas.android10.sample.presentation.view.interaction.EndlessRecyclerOnScrollListener;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Fragment that shows a list of Movies.
 */
public class MovieListFragment extends BaseFragment implements MovieListView {

    /**
     * Interface for listening movie list events.
     */
    public interface MovieListListener {
        void onMovieClicked(final MovieModel movieModel);
    }

    @Inject
    MovieListPresenter movieListPresenter;

    @InjectView(R.id.rv_movies)
    RecyclerView rv_movies;
    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;
    @InjectView(R.id.rl_retry)
    RelativeLayout rl_retry;
    @InjectView(R.id.bt_retry)
    Button bt_retry;

    private MoviesAdapter moviesAdapter;
    private MoviesLayoutManager moviesLayoutManager;

    private MovieListListener movieListListener;

    public MovieListFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MovieListListener) {
            this.movieListListener = (MovieListListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_movie_list, container, true);
        ButterKnife.inject(this, fragmentView);
        setupUI();

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        loadMovieList();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.movieListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.movieListPresenter.pause();
    }

    private void initialize() {
        getComponent(MovieComponent.class).inject(this);
        movieListPresenter.setView(this);
    }

    private void setupUI() {
        moviesLayoutManager = new MoviesLayoutManager(getActivity());
        rv_movies.setLayoutManager(moviesLayoutManager);
        rv_movies.setOnScrollListener(new EndlessRecyclerOnScrollListener(moviesLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.d(TAG, "onLoadMore" + current_page);
                movieListPresenter.loadMore(current_page);
            }
        });
    }

    @Override
    public void showLoading() {
        rl_progress.setVisibility(View.VISIBLE);
        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        rl_progress.setVisibility(View.GONE);
        getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    @Override
    public void addMovieList(Collection<MovieModel> movieModelCollection) {
        if (movieModelCollection != null) {
            if (moviesAdapter == null) {
                moviesAdapter = new MoviesAdapter(getActivity(), movieModelCollection);
                moviesAdapter.setOnItemClickListener(onItemClickListener);
                rv_movies.setAdapter(moviesAdapter);
            } else {
                moviesAdapter.addMoviesCollection(movieModelCollection);
            }
        }
    }

    @Override
    public void viewMovie(MovieModel movieModel) {
        if (this.movieListListener != null) {
            this.movieListListener.onMovieClicked(movieModel);
        }
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context getContext() {
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all movies.
     */
    private void loadMovieList() {
        this.movieListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        MovieListFragment.this.loadMovieList();
    }

    private MoviesAdapter.OnItemClickListener onItemClickListener =
            new MoviesAdapter.OnItemClickListener() {
                @Override
                public void onMovieItemClicked(MovieModel movieModel) {
                    if (MovieListFragment.this.movieListPresenter != null && movieModel != null) {
                        MovieListFragment.this.movieListPresenter.onMovieClicked(movieModel);
                    }
                }
            };
}
