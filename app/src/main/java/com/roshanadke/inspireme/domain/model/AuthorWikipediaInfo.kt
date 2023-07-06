package com.roshanadke.inspireme.domain.model

import com.google.gson.annotations.SerializedName
import com.roshanadke.inspireme.data.dto.author_wikipedia_info.Originalimage
import com.roshanadke.inspireme.data.dto.author_wikipedia_info.Thumbnail

data class AuthorWikipediaInfo(
    @SerializedName("originalimage")
    val originalImage: Originalimage,
    val thumbnail: Thumbnail,
    val displaytitle: String,
    val extract: String,
    val extract_html: String,
)