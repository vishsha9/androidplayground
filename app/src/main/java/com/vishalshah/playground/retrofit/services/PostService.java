package com.vishalshah.playground.retrofit.services;

import com.vishalshah.playground.models.PostModelRXRetrofit;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by vishalshah on 08/07/16.
 */
public interface PostService {
    @GET("/posts/{id}")
    public Observable<PostModelRXRetrofit> getPost(@Path("id") String id);
}
