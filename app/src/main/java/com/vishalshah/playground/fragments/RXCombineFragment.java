package com.vishalshah.playground.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.vishalshah.playground.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class RXCombineFragment extends Fragment {

    @BindView(R.id.text_view)
    TextView textView;

    @BindView(R.id.edit_text1)
    EditText editText1;

    @BindView(R.id.edit_text2)
    EditText editText2;

    @BindView(R.id.button)
    Button button;

    private static String TAG = "RXCombineFragment";

    public RXCombineFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxcombine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Observable<String> observable = RxTextView.textChanges(editText1).map(new Func1<CharSequence, String>() {
            @Override
            public String call(CharSequence charSequence) {
                Log.i(TAG, "Text Changes on Thread: " + Thread.currentThread().getName());
                return charSequence.toString();
            }
        });

        Observable<String> observable2 = RxTextView.textChanges(editText2).map(new Func1<CharSequence, String>() {
            @Override
            public String call(CharSequence charSequence) {
                Log.i(TAG, "Text Changes on Thread: " + Thread.currentThread().getName());
                return charSequence.toString();
            }
        });

        Observable.combineLatest(observable, observable2, new Func2<String, String, String>() {
                @Override
                public String call(String s, String s2) {
                    Log.i(TAG, "Combining Changes on Thread: " + Thread.currentThread().getName());
                    return s + " " + s2;
                }
            }).subscribe(new Action1<String>() {
            @Override
            public void call(String o) {
                Log.i(TAG, o.toString());
                Log.i(TAG, "Setting text view on Thread: " + Thread.currentThread().getName());
                textView.setText(o);
            }
        });

        Observable<Void> observableClick = RxView.clicks(button);
        observableClick.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Log.i(TAG, "Button click on Thread: " + Thread.currentThread().getName());
                editText1.setText("");
                editText2.setText("");
            }
        });
    }
}
