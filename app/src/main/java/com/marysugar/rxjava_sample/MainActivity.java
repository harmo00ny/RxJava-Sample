package com.marysugar.rxjava_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // observable
        Observable<String> animalsObservable = getAnimalObservable();

        // observer
        Observer<String> animalsObserver = getAnimalObserver();

        // observer subscribe to observable
        animalsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(s -> s.toLowerCase().startsWith("b"))
                .subscribe(animalsObserver);
    }

    private Observer<String> getAnimalObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe");

                disposable = d;
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Complete!!");
            }
        };
    }
    private Observable<String> getAnimalObservable() {
        return Observable.fromArray("Ant", "Bee");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposable.dispose();
    }
}