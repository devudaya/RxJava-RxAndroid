package com.example.rxjava_rxandroid.operators

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava_rxandroid.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class IntervalAndTimer : AppCompatActivity() {

    private val TAG = "RangeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interval_and_timer)

        val intervalObservable = Observable
            .timer(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .takeWhile { num ->
                num < 10
            }
            .observeOn(AndroidSchedulers.mainThread())

        intervalObservable.subscribe(object : Observer<Long> {

            var time: Long = 0 // variable for demonstating how much time has passed

            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "Time: $time")
                time = System.currentTimeMillis() / 1000
                Log.d(TAG, "onSubscribeTime: $time")
            }

            override fun onNext(t: Long) {
                Log.d(TAG, "onNext: " + ((System.currentTimeMillis() / 1000) - time) + " seconds have elapsed." );
            }

            override fun onError(e: Throwable) {
            }


        })


    }
}
