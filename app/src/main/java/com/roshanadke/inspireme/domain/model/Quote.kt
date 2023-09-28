package com.roshanadke.inspireme.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Quote(
    val author: String,
    val authorSlug: String,
    val content: String,
    val length: Int,
    val tags: List<String>
) : Parcelable
