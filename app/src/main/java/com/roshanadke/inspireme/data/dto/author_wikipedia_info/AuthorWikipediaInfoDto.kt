package com.roshanadke.inspireme.data.dto.author_wikipedia_info


import com.roshanadke.inspireme.domain.model.AuthorWikipediaInfo


data class AuthorWikipediaInfoDto(
    val description: String,
    val description_source: String,
    val dir: String,
    val displaytitle: String,
    val extract: String,
    val extract_html: String,
    val lang: String,
    val originalimage: Originalimage,
    val pageid: Int,
    val revision: String,
    val thumbnail: Thumbnail,
    val tid: String,
    val timestamp: String,
    val title: String,
    val type: String,
    val wikibase_item: String
) {
    fun toAuthorWikipediaInfo(): AuthorWikipediaInfo {
        return AuthorWikipediaInfo(
            originalImage = originalimage,
            thumbnail = thumbnail,
            displaytitle = displaytitle,
            extract = extract,
            extract_html = extract_html,
        )
    }
}