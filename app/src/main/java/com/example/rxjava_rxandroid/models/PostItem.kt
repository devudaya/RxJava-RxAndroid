package com.example.rxjava_rxandroid.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int,
    var comments: MutableList<CommentItem>?
) : Parcelable