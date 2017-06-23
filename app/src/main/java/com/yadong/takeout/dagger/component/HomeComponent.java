package com.yadong.takeout.dagger.component;

import com.yadong.takeout.dagger.module.HomeModule;
import com.yadong.takeout.dagger.score.FragmentScope;
import com.yadong.takeout.ui.fragment.HomeFragment;

import dagger.Component;

/**
 *
 */
@FragmentScope
@Component(modules = HomeModule.class,dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);
}
