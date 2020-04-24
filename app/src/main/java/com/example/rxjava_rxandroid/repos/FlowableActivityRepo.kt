package com.example.rxjava_rxandroid.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.rxjava_rxandroid.api.ServiceGenerator
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class FlowableActivityRepo {

    companion object {

        val instance by lazy {
            FlowableActivityRepo()
        }
    }

    fun makeReactiveQuery(): LiveData<ResponseBody>{

        return LiveDataReactiveStreams.fromPublisher(ServiceGenerator.getApi()
            .makeFlowableQuery()
            .subscribeOn(Schedulers.io()))
    }
}