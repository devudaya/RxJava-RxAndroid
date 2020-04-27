package com.example.rxjava_rxandroid.operators.transformation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxjava_rxandroid.R
import com.example.rxjava_rxandroid.adapters.PostAdapter
import com.example.rxjava_rxandroid.api.ServiceGenerator
import com.example.rxjava_rxandroid.models.PostItem
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_flat_map.*
import kotlin.random.Random


class FlatMap : AppCompatActivity() {

    private val TAG = "FlatMap Activity"
    private val compositeDisposable = CompositeDisposable()
    private var postAdapter: PostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat_map)

        initRecyclerView()

        getPostObservable()
            .subscribeOn(Schedulers.io())
            .flatMap { post ->
                getCommentObservable(post)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<PostItem> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: PostItem) {
                    updatePost(t)
                }

                override fun onError(e: Throwable) {
                }
            })

    }

    private fun getPostObservable(): Observable<PostItem> {
        return ServiceGenerator.getApi().getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                postAdapter?.setPosts(it)
                Observable.fromIterable(it)
                    .subscribeOn(Schedulers.io())
            }
    }

    private fun getCommentObservable(postItem: PostItem): Observable<PostItem> {
        return ServiceGenerator.getApi().getComments(postItem.id)
            .map { comments ->

                val delay: Int = (Random.nextInt(5) + 1) * 1000 // sleep thread for x ms
                Thread.sleep(delay.toLong())
                Log.d(
                    TAG,
                    "apply: sleeping thread " + Thread.currentThread().name + " for " + delay.toString() + "ms"
                )
                postItem.comments = comments
                postItem
            }
            .subscribeOn(Schedulers.io())
    }

    private fun updatePost(postItem: PostItem) {

        Observable.fromIterable(postAdapter?.getPosts())
            .subscribeOn(Schedulers.io())
            .filter { post ->
                postItem.id == post.id
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<PostItem> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: PostItem) {
                    Log.d(
                        TAG, "onNext: updating post: " + t.id + ", thread: "
                                + Thread.currentThread().name
                    )
                    postAdapter?.updatePost(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ", e)
                }
            })
    }

    private fun initRecyclerView() {
        postAdapter = PostAdapter()
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = postAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
