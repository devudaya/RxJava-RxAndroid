package com.example.rxjava_rxandroid.api

import com.example.rxjava_rxandroid.models.CommentItem
import com.example.rxjava_rxandroid.models.PostItem
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.ResponseBody

import retrofit2.http.GET
import retrofit2.http.Path


interface API {

    @GET("todos/1")
    fun makeObservableQuery(): Observable<ResponseBody>

    //for Flowables
    @GET("todos/0")
    fun makeFlowableQuery(): Flowable<ResponseBody>

    @GET("posts")
    fun getPosts(): Observable<MutableList<PostItem>>

    @GET("posts/{id}/comments")
    fun getComments(
        @Path("id") id: Int
    ): Observable<MutableList<CommentItem>>

    @GET("posts/{id}")
    fun getPost(
        @Path("id") id: Int
    ): Observable<PostItem>
}