package com.example.rxjava_rxandroid.operators.transformation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxjava_rxandroid.R
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_buffer_with_u_i_interaction.*
import java.util.concurrent.TimeUnit

class BufferWithUIInteraction : AppCompatActivity() {

    private val TAG = "BufferWithUIInteraction"
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buffer_with_u_i_interaction)

        val uiObservable = idClick.clicks()
            .map {
                1
            }
            .buffer(4, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())

        uiObservable.subscribe(object : Observer<List<Int>>{
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onNext(t: List<Int>) {
                Log.d(TAG, "onNext: You clicked " + t.size + " times in 4 seconds!");
            }

            override fun onError(e: Throwable) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
