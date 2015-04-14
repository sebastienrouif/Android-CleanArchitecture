/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.test.presenter;

import android.content.Context;
import android.test.AndroidTestCase;
import com.fernandocejas.android10.sample.domain.interactor.GetMovieDetailsUseCase;
import com.fernandocejas.android10.sample.presentation.mapper.MovieModelDataMapper;
import com.fernandocejas.android10.sample.presentation.presenter.MovieDetailsPresenter;
import com.fernandocejas.android10.sample.presentation.view.MovieDetailsView;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class MovieDetailsPresenterTest extends AndroidTestCase {

  private static final int FAKE_USER_ID = 123;

  private MovieDetailsPresenter movieDetailsPresenter;

  @Mock
  private Context mockContext;
  @Mock
  private MovieDetailsView mockMovieDetailsView;
  @Mock
  private GetMovieDetailsUseCase mockGetMovieDetailsUseCase;
  @Mock
  private MovieModelDataMapper mockMovieModelDataMapper;

  @Override protected void setUp() throws Exception {
    super.setUp();
    MockitoAnnotations.initMocks(this);
    movieDetailsPresenter = new MovieDetailsPresenter(mockGetMovieDetailsUseCase,
        mockMovieModelDataMapper);
  }

  public void testMovieDetailsPresenterInitialize() {
    doNothing().when(mockGetMovieDetailsUseCase)
        .execute(anyInt(), any(GetMovieDetailsUseCase.Callback.class));
    given(mockMovieDetailsView.getContext()).willReturn(mockContext);

    movieDetailsPresenter.initialize(FAKE_USER_ID);

    verify(mockMovieDetailsView).hideRetry();
    verify(mockMovieDetailsView).showLoading();
    verify(mockGetMovieDetailsUseCase).execute(anyInt(), any(GetMovieDetailsUseCase.Callback.class));
  }
}
