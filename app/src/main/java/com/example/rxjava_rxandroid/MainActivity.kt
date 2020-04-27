package com.example.rxjava_rxandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava_rxandroid.models.Task
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskObservable: Observable<Task> = Observable.fromIterable(DataSource.getTaskList())
            .subscribeOn(Schedulers.io())
            .filter{task ->
                Log.d(TAG , "onNext: : " + Thread.currentThread().name)
                Thread.sleep(1000)
                task.isComplete
            }
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.subscribe(object : Observer<Task>{
            override fun onComplete() {
                Log.d(TAG , "onComplete: task completed")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG , "onSubscribe: : called")
            }

            override fun onNext(t: Task) {
                Log.d(TAG , "onNext: : " + t.description)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG , "onError: : ", e)
            }
        })
    }
}
