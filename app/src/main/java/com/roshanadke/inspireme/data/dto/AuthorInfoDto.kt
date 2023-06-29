package com.roshanadke.inspireme.data.dto

import com.roshanadke.inspireme.domain.model.Author

data class AuthorInfoDto(
    val _id: String,
    val bio: String,
    val dateAdded: String,
    val dateModified: String,
    val description: String,
    val link: String,
    val name: String,
    val quoteCount: Int,
    val slug: String
) {

    fun toAuthor(): Author {
        return Author(
            bio = bio,
            description = description,
            link = link,
            name = name,
            slug = slug,
        )
    }
}