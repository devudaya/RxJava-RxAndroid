package com.example.rxjava_rxandroid.api

import io.reactivex.Observable
import okhttp3.ResponseBody

import retrofit2.http.GET




interface API {

    @GET("todos/1")
    fun makeObservableQuery(): Observable<ResponseBody>
}