package com.vishalshah.playground.retrofit.services;

import com.vishalshah.playground.models.ProductModelRXRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by vishalshah on 07/07/16.
 */
public interface ProductService {
    @GET("shop/products/{id}")
    Observable<ProductModelRXRetrofit> getProduct(@Path("id") String id);
}
