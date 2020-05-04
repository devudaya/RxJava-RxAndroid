package com.example.rxjava_rxandroid.operators.transformation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxjava_rxandroid.R
import com.example.rxjava_rxandroid.ViewPost
import com.example.rxjava_rxandroid.adapters.SwitchMapPostAdapter
import com.example.rxjava_rxandroid.api.ServiceGenerator
import com.example.rxjava_rxandroid.models.PostItem
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_switch_map.*
import java.util.concurrent.TimeUnit


class SwitchMap : AppCompatActivity() {

    private val TAG = "SwitchMap"
    // vars
    private val disposables = CompositeDisposable()
    private var adapter: SwitchMapPostAdapter? = null
    private val publishSubject: PublishSubject<PostItem> =
        PublishSubject.create<PostItem>() // for selecting a post
    private val PERIOD: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_map)

        initRecyclerView()
        retrievePosts()
    }

    override fun onResume() {
        super.onResume()
        progress_bar.progress = 0
        initSwitchMapDemo()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: called.")
        disposables.clear()
        super.onPause()
    }
    private fun initSwitchMapDemo() {

        publishSubject.switchMap {post ->
            Observable
                .interval(PERIOD,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .takeWhile {
                    Log.d(TAG, "test: " + Thread.currentThread().name + ", " + it)
                    progress_bar.max = 3000 - PERIOD.toInt()
                    progress_bar.progress = ((it * PERIOD) + PERIOD).toInt()
                    Log.d(TAG, "Progress Bar val: $it" )
                    it <= (3000 / PERIOD)
                }
                .filter{
                    it <= (3000 / PERIOD)
                }
                .flatMap {
                    ServiceGenerator.getApi().getPost(
                        post.id
                    )
                }
        }
            .subscribe(object : Observer<PostItem>{
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(t: PostItem) {
                    navViewPostActivity(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ", e)
                }
            })

    }

    private fun retrievePosts() {

        ServiceGenerator.getApi().getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<MutableList<PostItem>>{
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(t: MutableList<PostItem>) {
                    adapter?.setPosts(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ", e);
                }
            })
    }

    private fun initRecyclerView() {
        adapter = SwitchMapPostAdapter { position: Int ->
            progress_bar.progress = 0
            publishSubject.onNext(adapter!!.getPosts()[position])
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }

    private fun navViewPostActivity(post: PostItem) {

        val intent = Intent(this, ViewPost::class.java)
        intent.putExtra("post", post)
        startActivity(intent)
    }

}
