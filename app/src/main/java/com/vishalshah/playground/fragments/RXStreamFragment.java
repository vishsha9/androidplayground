package com.vishalshah.playground.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.vishalshah.playground.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

/***
 * Shows a simple stream of interacting with different UI elements using RXAndroid
 */
public class RXStreamFragment extends Fragment {

    private Context context;

    private String TAG = "RXStreamFragment";

    @BindView(R.id.button_rx_stream)
    Button button;

    @BindView(R.id.text_view_rx_stream)
    TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rxstream, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //TODO: Use retro lambda to make functions more concise
        Observable<Void> observable = RxView.clicks(button);
        observable.flatMap(new Func1<Void, Observable<?>>() {
            @Override
            public Observable<?> call(Void aVoid) {
                Log.i(TAG, "Button click on Thread: " + Thread.currentThread().getName());
                return dialog(context);
            }
        }).filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                Log.i(TAG, "Filtering clicks on Thread: " + Thread.currentThread().getName());
                if (o == null) {
                    return false;
                } else {
                    return true;
                }
            }
        }).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "Set text view on Thread: " + Thread.currentThread().getName());
                textView.setText((String)o);
            }
        });
    }

    private Observable<String> dialog (final Context context){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                final EditText editText = new EditText(context);
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle("Stream Test")
                    .setMessage("Enter a message below")
                    .setView(editText)
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            subscriber.onNext(editText.getText().toString());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            subscriber.onNext(null);
                        }
                    })
                    .show();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
