/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.internal.di.modules;

import com.fernandocejas.android10.sample.domain.interactor.GetMovieDetailsUseCase;
import com.fernandocejas.android10.sample.domain.interactor.GetMovieDetailsUseCaseImpl;
import com.fernandocejas.android10.sample.domain.interactor.GetMovieListUseCase;
import com.fernandocejas.android10.sample.domain.interactor.GetMovieListUseCaseImpl;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides movie related collaborators.
 */
@Module
public class MovieModule {
  @Provides @PerActivity GetMovieListUseCase provideGetMovieListUseCase(GetMovieListUseCaseImpl getMovieListUseCase) {
    return getMovieListUseCase;
  }

  @Provides @PerActivity GetMovieDetailsUseCase provideGetMovieDetailsUseCase(GetMovieDetailsUseCaseImpl getMovieDetailsUseCase) {
    return getMovieDetailsUseCase;
  }
}
