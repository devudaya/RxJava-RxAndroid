package com.example.rxjava_rxandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.rxjava_rxandroid.viewmodels.FutureActivityViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody


class FutureActivity : AppCompatActivity() {

    private lateinit var viewModel: FutureActivityViewModel
    private val TAG = "FutureActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_future)

        viewModel = ViewModelProviders.of(this).get(FutureActivityViewModel::class.java)
        viewModel.makeFutureQuery().get()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseBody> {
                override fun onComplete() {
                    Log.d(TAG, "onComplete: called.");
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: called.")
                }

                override fun onNext(t: ResponseBody) {
                    Log.d(TAG, "onNext: got the response from server!")
                    Log.d(TAG, "onNext: " + t.string())

                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ", e);
                }

            })
    }
}
