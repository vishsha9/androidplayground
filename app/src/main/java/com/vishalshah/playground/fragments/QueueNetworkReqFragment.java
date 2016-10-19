package com.vishalshah.playground.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.vishalshah.playground.R;
import com.vishalshah.playground.adapters.RXRetrofitFragmentPostAdapter;
import com.vishalshah.playground.db.SQLiteQueueDAO;
import com.vishalshah.playground.models.MySQLiteStringQueue;
import com.vishalshah.playground.models.PostModelRXRetrofit;
import com.vishalshah.playground.retrofit.services.PostService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;

/**
 * Created by vishalshah on 19/10/16.
 */
public class QueueNetworkReqFragment extends Fragment {

    @BindView(R.id.button_rx_queue_req)
    Button buttonQueueReq;
    @BindView(R.id.button_rx_queue_submit)
    Button buttonSubmitReq;
    @BindView(R.id.button_rx_queue_clear_content)
    Button buttonClearContents;
    @BindView(R.id.recycler_view_queue_network)
    RecyclerView recyclerView;
    @BindView(R.id.text_view_queue_length)
    TextView textViewQueueLength;

    // Reusing the existing adapter
    private RXRetrofitFragmentPostAdapter adapter;

    private PostService postService;

    private MySQLiteStringQueue queue;

    private int index = 1;

    private ReplaySubject<Integer> subjectSubmitReq = ReplaySubject.create();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://jsonplaceholder.typicode.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();

        postService = retrofit.create(PostService.class);

        adapter = new RXRetrofitFragmentPostAdapter(null, getActivity());

        queue = new MySQLiteStringQueue(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_queue_network, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        textViewQueueLength.setText("Queue Length: " + queue.getSize());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /***
         * Setting up all the observables
         */
        Observable<Void> clickClearContents = RxView.clicks(buttonClearContents);
        clickClearContents.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                adapter.clearContents();
            }
        });

        Observable<Void> clickObservableQueueReq = RxView.clicks(buttonQueueReq);
        clickObservableQueueReq
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        queue.add(index);
                        index = index + 1;
                        textViewQueueLength.setText("Queue Length: " + queue.getSize());
                    }
                });

        Observable<Void> clickObservableSubmitReq = RxView.clicks(buttonSubmitReq);
        clickObservableSubmitReq
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        int count = queue.getSize();
                        for (int i = 0; i < count; i++) {
                            subjectSubmitReq.onNext(queue.get());
                        }
                    }
                });

        subjectSubmitReq
                .observeOn(Schedulers.io())
                .flatMap(new Func1<Integer, Observable<PostModelRXRetrofit>>() {
                    @Override
                    public Observable<PostModelRXRetrofit> call(Integer integer) {
                        return postService.getPost(integer.toString());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PostModelRXRetrofit>() {
                    @Override
                    public void call(PostModelRXRetrofit postModelRXRetrofit) {
                        adapter.addPost(postModelRXRetrofit);
                        textViewQueueLength.setText("Queue Length: " + queue.getSize());
                    }
                });





    }


}
