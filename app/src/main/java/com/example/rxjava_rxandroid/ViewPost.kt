package com.example.rxjava_rxandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava_rxandroid.models.PostItem
import kotlinx.android.synthetic.main.activity_view_post.*


class ViewPost : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post)
        getIncomingIntent()
    }

    private fun getIncomingIntent() {

        if (intent.hasExtra("post")) {
            val post = intent.getParcelableExtra<PostItem>("post")
            text.text = post.title
        }
    }
}
