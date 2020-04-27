package com.example.rxjava_rxandroid.operators.transformation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava_rxandroid.R
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_throttle_first.*
import java.util.concurrent.TimeUnit


class ThrottleFirst : AppCompatActivity() {

    private val TAG = "ThrottleFirst Activity"
    private val compositeDisposable = CompositeDisposable()
    private var timeSinceLastRequest: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_throttle_first)

        timeSinceLastRequest = System.currentTimeMillis()

        bt.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Unit>{
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: Unit) {

                    Log.d(TAG, "onNext: time since last clicked: " + (System.currentTimeMillis() - timeSinceLastRequest))
                    someMethod()

                }

                override fun onError(e: Throwable) {
                }
            })

    }

    private fun someMethod() {
        timeSinceLastRequest = System.currentTimeMillis()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
