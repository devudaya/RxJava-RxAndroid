package com.example.rxjava_rxandroid.operators.transformation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava_rxandroid.DataSource
import com.example.rxjava_rxandroid.R
import com.example.rxjava_rxandroid.Task
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class Buffer : AppCompatActivity() {

    private val TAG = "Buffer Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buffer)

        val mapObservable = Observable.fromIterable(DataSource.getTaskList())
            .subscribeOn(Schedulers.io())
            .buffer(2)
            .observeOn(AndroidSchedulers.mainThread())

        mapObservable.subscribe(object : Observer<List<Task>>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: List<Task>) {

                Log.d(TAG, "onNext: bundle results: -------------------")
                for (task in t) {
                    Log.d(TAG, "onNext: " + task.description)
                }
            }

            override fun onError(e: Throwable) {
            }
        })
    }
}
