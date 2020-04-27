package com.example.rxjava_rxandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava_rxandroid.R
import com.example.rxjava_rxandroid.models.PostItem
import kotlinx.android.synthetic.main.post_layout.view.*



class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val TAG = "RecyclerAdapter"
    private var posts = mutableListOf<PostItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    fun setPosts(posts: MutableList<PostItem>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    fun updatePost(post: PostItem) {
        posts[posts.indexOf(post)] = post
        notifyItemChanged(posts.indexOf(post))
    }

    fun getPosts(): MutableList<PostItem> {
        return posts
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val title = itemView.title!!
        private val progress = itemView.progress_bar!!
        private val numComments = itemView.num_comments!!

        fun bind(post: PostItem) {
            title.text = post.title
            if (post.comments == null) {
                showProgressBar(true)
                numComments.text = ""
            } else {
                showProgressBar(false)
                numComments.text = "${post.comments!!.size}"
            }
        }

        private fun showProgressBar(showProgressBar: Boolean) {

            if (showProgressBar) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        }
    }
}