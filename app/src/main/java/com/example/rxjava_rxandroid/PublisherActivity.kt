package com.example.rxjava_rxandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rxjava_rxandroid.viewmodels.PublisherActivityViewModel
import java.io.IOException


class PublisherActivity : AppCompatActivity() {

    private lateinit var viewModel: PublisherActivityViewModel
    private val TAG = "PubliserActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publisher)

        viewModel = ViewModelProviders.of(this).get(PublisherActivityViewModel::class.java)
        viewModel.makeFlowableQuery().observe(this, (Observer {
            Log.d(TAG, "onChanged: this is a live data response!")
            try {
                Log.d(TAG, "onChanged: " + it.string())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }))
    }
}
