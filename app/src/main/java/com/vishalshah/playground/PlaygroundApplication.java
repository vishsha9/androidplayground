package com.vishalshah.playground;

import android.app.Application;

import com.vishalshah.playground.dagger.components.DaggerNetworkComponent;
import com.vishalshah.playground.dagger.components.NetworkComponent;
import com.vishalshah.playground.dagger.modules.NetworkModule;

/**
 * Created by vishalshah on 12/07/16.
 */
public class PlaygroundApplication extends Application {

    NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        networkComponent = DaggerNetworkComponent.builder().build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}
