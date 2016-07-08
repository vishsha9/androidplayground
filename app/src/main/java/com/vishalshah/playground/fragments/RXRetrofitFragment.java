package com.vishalshah.playground.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.vishalshah.playground.R;
import com.vishalshah.playground.adapters.RXRetrofitFragmentPostAdapter;
import com.vishalshah.playground.models.PostModelRXRetrofit;
import com.vishalshah.playground.retrofit.services.PostService;

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
import rx.subjects.ReplaySubject;

public class RXRetrofitFragment extends Fragment {

    private String TAG = "RXRetrofitFragment";

    @BindView(R.id.button_rx_retrofit)
    Button button;
    @BindView(R.id.text_view_rx_retrofit)
    TextView textView;
    @BindView(R.id.recycler_view_rx_retrofit)
    RecyclerView recyclerView;
    @BindView(R.id.button_rx_retrofit_clear)
    Button buttonClearContents;

    Integer index;

    ReplaySubject<PostModelRXRetrofit> subjectPost = ReplaySubject.create();

    PostService postService;

    RXRetrofitFragmentPostAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = 0;

        adapter = new RXRetrofitFragmentPostAdapter(null, getActivity());

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://jsonplaceholder.typicode.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .build();


        postService = retrofit.create(PostService.class);


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

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        subjectPost.subscribe(new Action1<PostModelRXRetrofit>() {
            @Override
            public void call(PostModelRXRetrofit postModelRXRetrofit) {
                Log.i(TAG, "subjectProduct call");
                adapter.addPost(postModelRXRetrofit);
            }
        });

        Observable<Void> clickClearContents = RxView.clicks(buttonClearContents);
        clickClearContents.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                adapter.clearContents();
            }
        });

        Observable<Void> clickObservable = RxView.clicks(button);
        clickObservable
            .observeOn(Schedulers.io())
            .flatMap(new Func1<Void, Observable<PostModelRXRetrofit>>() {
                @Override
                public Observable<PostModelRXRetrofit> call(Void aVoid) {
                    index = index + 1;
                    Log.i(TAG, "RetrofitService on Thread: " + Thread.currentThread().getName());
                    return postService.getPost(index.toString());
                }
            }).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<PostModelRXRetrofit>() {
                @Override
                public void call(PostModelRXRetrofit postModelRXRetrofit) {
                    Log.i(TAG, "Result of Service On Thread: " + Thread.currentThread().getName());
                    Log.i(TAG, postModelRXRetrofit.toString());
                    subjectPost.onNext(postModelRXRetrofit);
                }
            });

    }
}
