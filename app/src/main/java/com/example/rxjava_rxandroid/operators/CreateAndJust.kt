package com.example.rxjava_rxandroid.operators

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava_rxandroid.R
import com.example.rxjava_rxandroid.models.Task
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class CreateAndJust : AppCompatActivity() {

    private val TAG = "CreateAndJustActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_and_just)

        val task =
            Task("Walk the dog", false, 4)

        val taskObservable = Observable
            .just(task)
//            .create<Task> { emitter ->
//
////            Single Object Observable
//            if (!emitter.isDisposed)
//                emitter.onNext(task)
//            emitter.onComplete()
//
//            //List of Objects Observable
//            for (task in DataSource.getTaskList()) {
//                emitter.onNext(task)
//            }
//            if (!emitter.isDisposed)
//                emitter.onComplete()
//        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


        taskObservable.subscribe(object : Observer<Task> {
            override fun onComplete() {
                Log.d(TAG, "onComplete: task completed")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe: : called")
            }

            override fun onNext(t: Task) {
                Log.d(TAG, "onNext: : " + t.description)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: : ", e)
            }
        })
    }

}
