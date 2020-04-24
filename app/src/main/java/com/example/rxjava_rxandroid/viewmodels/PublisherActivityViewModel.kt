package com.example.rxjava_rxandroid.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rxjava_rxandroid.repos.FlowableActivityRepo
import okhttp3.ResponseBody

class PublisherActivityViewModel: ViewModel() {

    private val repo = FlowableActivityRepo.instance

    fun makeFlowableQuery() : LiveData<ResponseBody>{
        return repo.makeReactiveQuery()
    }
}