package com.example.rxjava_rxandroid.operators.transformation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.rxjava_rxandroid.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_debounce.*
import java.util.concurrent.TimeUnit


class Debounce : AppCompatActivity() {

    private val TAG = "Debounce Activity"
    private val compositeDisposable = CompositeDisposable()
    private var timeSinceLastRequest: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debounce)
        timeSinceLastRequest = System.currentTimeMillis()

        val debounceObservable = Observable.create<String> { emitter ->
            search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!emitter.isDisposed)
                        emitter.onNext(newText!!)
                    return false
                }
            })
        }
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())

        debounceObservable.subscribe(object : Observer<String>{
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: String) {
                Log.d(TAG, "onNext: time  since last request: " + (System.currentTimeMillis() - timeSinceLastRequest)/1000)
                Log.d(TAG, "onNext: search query: $t")
                timeSinceLastRequest = System.currentTimeMillis()

                // method for sending a request to the server
                sendRequestToServer(t)
            }

            override fun onError(e: Throwable) {
            }
        })


    }

    // Fake method for sending a request to the server
    private fun sendRequestToServer(query: String) { // do nothing
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear() // clear disposables
    }
}
