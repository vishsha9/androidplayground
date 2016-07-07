package com.vishalshah.playground.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.vishalshah.playground.R;
import com.vishalshah.playground.models.ProductModelRXRetrofit;
import com.vishalshah.playground.retrofit.services.ProductService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RXRetrofitFragment extends Fragment {

    private String TAG = "RXRetrofitFragment";

    @BindView(R.id.button_rx_retrofit)
    Button button;
    @BindView(R.id.text_view_rx_retrofit)
    TextView textView;

    Integer index;

    List<ProductModelRXRetrofit> productList;

    ProductService productService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = 0;
        productList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://api.predic8.de/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .build();


        productService = retrofit.create(ProductService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rxretrofit, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Observable<Void> clickObservable = RxView.clicks(button);
        clickObservable.subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.newThread())
            .flatMap(new Func1<Void, Observable<ProductModelRXRetrofit>>() {
                @Override
                public Observable<ProductModelRXRetrofit> call(Void aVoid) {
                    index = index + 1;
                    Log.i(TAG, "Thread: " + Thread.currentThread().getName());
                    return productService.getProduct(index.toString());
                }
            }).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<ProductModelRXRetrofit>() {
                @Override
                public void call(ProductModelRXRetrofit productModelRXRetrofit) {
                    Log.i(TAG, "Thread: " + Thread.currentThread().getName());
                    Log.i(TAG, productModelRXRetrofit.toString());
                }
            });

    }
}
