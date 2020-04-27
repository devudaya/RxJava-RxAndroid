package com.example.rxjava_rxandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava_rxandroid.R
import com.example.rxjava_rxandroid.models.PostItem
import kotlinx.android.synthetic.main.switch_map_post_item_layout.view.*


class SwitchMapPostAdapter(
    private val onPostClick: ((position: Int) -> Unit)
) : RecyclerView.Adapter<SwitchMapPostAdapter.ViewHolder>() {

    private val TAG = "SwitchMapAdapter"
    private var posts = mutableListOf<PostItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.switch_map_post_item_layout, parent, false)
        return ViewHolder(view, onPostClick)
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

    inner class ViewHolder internal constructor(
        @NonNull view: View,
        onPostClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val title = view.title!!
        init {
            view.setOnClickListener { onPostClick.invoke(adapterPosition) }
        }

        fun bind(post: PostItem) {
            title.text = post.title
        }
    }
}