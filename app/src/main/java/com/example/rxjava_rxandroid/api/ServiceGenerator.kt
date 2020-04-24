package com.example.rxjava_rxandroid.api

import com.example.rxjava_rxandroid.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGenerator {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    fun getApi(): API{


        val okHttpClientBuilder = OkHttpClient.Builder()
//                okHttpClientBuilder.authenticator(TokenAuthenticator())
        okHttpClientBuilder.connectTimeout(30,TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60,TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = retrofitBuilder.build()

        return retrofit.create(API::class.java)
    }

}