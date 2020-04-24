package com.example.rxjava_rxandroid.viewmodels

import androidx.lifecycle.ViewModel
import com.example.rxjava_rxandroid.repos.FutureActivityRepo
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.util.concurrent.Future

class FutureActivityViewModel: ViewModel() {

    private val repo = FutureActivityRepo.instance

    fun makeFutureQuery() : Future<Observable<ResponseBody>>{
       return repo.makeFutureQuery()
    }
}