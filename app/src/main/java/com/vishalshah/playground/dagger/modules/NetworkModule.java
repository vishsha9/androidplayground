package com.vishalshah.playground.dagger.modules;

import com.vishalshah.playground.retrofit.services.PostService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vishalshah on 12/07/16.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    PostService providesPostService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        return retrofit.create(PostService.class);
    }
}
