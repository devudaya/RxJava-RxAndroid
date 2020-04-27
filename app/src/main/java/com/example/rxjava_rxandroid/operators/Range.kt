package com.example.rxjava_rxandroid.operators

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxjava_rxandroid.R
import com.example.rxjava_rxandroid.models.Task
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Range : AppCompatActivity() {

    private val TAG = "RangeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_range)

        val testObservable = Observable.range(0, 11)
            .subscribeOn(Schedulers.io())
            .map {
                Log.d(TAG, "Thread Name is: ${Thread.currentThread().name}")
                Task(
                    description = "This is the task $it",
                    isComplete = false,
                    priority = it
                )
            }
            .takeWhile { task ->
                task.priority < 3
            }
            .repeat(3)
            .observeOn(AndroidSchedulers.mainThread())

        testObservable.subscribe(object : Observer<Task> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
            }

            override fun onNext(t: Task) {
                Log.d(TAG, "onNext: ${t.priority}")
            }
        })
    }
}
