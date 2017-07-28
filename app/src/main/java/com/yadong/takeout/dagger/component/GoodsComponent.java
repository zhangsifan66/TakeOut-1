package com.yadong.takeout.dagger.component;

import com.yadong.takeout.dagger.module.GoodsModule;
import com.yadong.takeout.dagger.score.FragmentScope;
import com.yadong.takeout.ui.fragment.GoodsFragment;

import dagger.Component;

/**
 *
 */
@FragmentScope
@Component(modules = GoodsModule.class,dependencies = AppComponent.class)
public interface GoodsComponent {
    void inject(GoodsFragment fragment);
}
