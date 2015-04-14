/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.test.presenter;

import android.content.Context;
import android.test.AndroidTestCase;
import com.fernandocejas.android10.sample.domain.interactor.GetMovieListUseCase;
import com.fernandocejas.android10.sample.presentation.mapper.MovieModelDataMapper;
import com.fernandocejas.android10.sample.presentation.presenter.MovieListPresenter;
import com.fernandocejas.android10.sample.presentation.view.MovieListView;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class MovieListPresenterTest extends AndroidTestCase {

  private MovieListPresenter movieListPresenter;

  @Mock
  private Context mockContext;
  @Mock
  private MovieListView mockMovieListView;
  @Mock
  private GetMovieListUseCase mockGetMovieListUseCase;
  @Mock
  private MovieModelDataMapper mockMovieModelDataMapper;

  @Override protected void setUp() throws Exception {
    super.setUp();
    MockitoAnnotations.initMocks(this);
    movieListPresenter = new MovieListPresenter(mockGetMovieListUseCase,
        mockMovieModelDataMapper);
  }

  public void testMovieListPresenterInitialize() {
    doNothing().when(mockGetMovieListUseCase).execute(any(GetMovieListUseCase.Callback.class));
    given(mockMovieListView.getContext()).willReturn(mockContext);

    movieListPresenter.initialize();

    verify(mockMovieListView).hideRetry();
    verify(mockMovieListView).showLoading();
    verify(mockGetMovieListUseCase).execute(any(GetMovieListUseCase.Callback.class));
  }
}
