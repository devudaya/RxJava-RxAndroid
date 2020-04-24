package com.example.rxjava_rxandroid.operators

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava_rxandroid.DataSource
import com.example.rxjava_rxandroid.Task
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DistinctOperator: AppCompatActivity() {

    private val TAG = "Distinct Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val distinctObservable = Observable
            .fromIterable(DataSource.getTaskList())
            .distinct { it.description } // remove duplicates
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


        distinctObservable.subscribe(object : Observer<Task>{
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Task) {
                Log.d(TAG, "onNext: " + t.description)
            }

            override fun onError(e: Throwable) {
            }
        })
    }
}