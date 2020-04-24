package com.example.rxjava_rxandroid.operators.transformation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxjava_rxandroid.DataSource
import com.example.rxjava_rxandroid.R
import com.example.rxjava_rxandroid.Task
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Maps : AppCompatActivity() {

    private val TAG = "Maps Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapObservable = Observable.fromIterable(DataSource.getTaskList())
            .distinct { it.description }
            .map { task ->
//                task.description
                task.isComplete = true
                task
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        // For mapped String
//        mapObservable.subscribe(object : Observer<String>{
//            override fun onComplete() {
//            }
//
//            override fun onSubscribe(d: Disposable) {
//            }
//
//            override fun onNext(t: String) {
//                Log.d(TAG, "onNext: extracted description: $t" )
//            }
//
//            override fun onError(e: Throwable) {
//            }
//        })

        //For the Same Object
        mapObservable.subscribe(object : Observer<Task>{
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Task) {
                Log.d(TAG, "onNext: is this task complete? " + t.isComplete)
            }

            override fun onError(e: Throwable) {
            }

        })



    }
}
