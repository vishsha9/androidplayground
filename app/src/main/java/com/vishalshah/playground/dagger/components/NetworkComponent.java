package com.vishalshah.playground.dagger.components;

import com.vishalshah.playground.dagger.modules.NetworkModule;
import com.vishalshah.playground.fragments.RXRetrofitFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vishalshah on 12/07/16.
 */

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    public void inject(RXRetrofitFragment rxRetrofitFragment);
}
