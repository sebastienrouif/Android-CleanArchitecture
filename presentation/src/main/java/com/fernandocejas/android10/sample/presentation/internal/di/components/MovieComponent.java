/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.MovieModule;
import com.fernandocejas.android10.sample.presentation.view.fragment.MovieDetailsFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.MovieListFragment;
import dagger.Component;

/**
 * A scope {@link com.fernandocejas.android10.sample.presentation.internal.di.PerActivity} component.
 * Injects movie specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MovieModule.class})
public interface MovieComponent extends ActivityComponent {
  void inject(MovieListFragment movieListFragment);
  void inject(MovieDetailsFragment movieDetailsFragment);
}
