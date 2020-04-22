package com.example.rxjava_rxandroid.repos

import com.example.rxjava_rxandroid.api.ServiceGenerator
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class FutureActivityRepo {

    companion object {

        val instance by lazy {
            FutureActivityRepo()
        }
    }

    fun makeFutureQuery(): Future<Observable<ResponseBody>> {

        val executor = Executors.newSingleThreadExecutor()
        val callable = Callable<Observable<ResponseBody>> {
            ServiceGenerator.getApi().makeObservableQuery()
        }

        return object : Future<Observable<ResponseBody>> {

            override fun isDone(): Boolean {
                return executor.isTerminated
            }

            override fun get(): Observable<ResponseBody> {
                return executor.submit(callable).get()
            }

            override fun get(p0: Long, p1: TimeUnit?): Observable<ResponseBody> {
                return executor.submit(callable).get(p0, p1)
            }

            override fun cancel(p0: Boolean): Boolean {
                if (p0) {
                    executor.shutdown()
                }
                return false
            }

            override fun isCancelled(): Boolean {
                return executor.isShutdown
            }
        }
    }
}