/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.MovieRepository;

import java.util.Collection;

import javax.inject.Inject;

/**
 * This class is an implementation of {@link GetMovieListUseCase} that represents a use case for
 * retrieving a collection of all {@link Movie}.
 */
public class GetMovieListUseCaseImpl implements GetMovieListUseCase {

    private final MovieRepository movieRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;
    private int page;

    /**
     * Constructor of the class.
     *
     * @param movieRepository     A {@link MovieRepository} as a source for retrieving data.
     * @param threadExecutor      {@link ThreadExecutor} used to execute this use case in a background
     *                            thread.
     * @param postExecutionThread {@link PostExecutionThread} used to post updates when the use case
     *                            has been executed.
     */
    @Inject
    public GetMovieListUseCaseImpl(MovieRepository movieRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread) {
        this.movieRepository = movieRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int page, Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback cannot be null!!!");
        }
        this.callback = callback;
        this.threadExecutor.execute(this);
        this.page = page;
    }

    @Override
    public void run() {
        this.movieRepository.getMovieList(page, this.repositoryCallback);
    }

    private final MovieRepository.MovieListCallback repositoryCallback =
            new MovieRepository.MovieListCallback() {
                @Override
                public void onMovieListLoaded(Collection<Movie> moviesCollection) {
                    notifyGetMovieListSuccessfully(moviesCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetMovieListSuccessfully(final Collection<Movie> moviesCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onMovieListLoaded(moviesCollection);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
