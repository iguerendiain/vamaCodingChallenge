package iguerendiain.vamacodingchallenge.model

import java.util.Date

data class Feed<T>(
    val title: String,
    val id: String,
    val author: Author,
    val links: List<Links>,
    val copyright: String,
    val country: String,
    val icon: String,
    val updated: Date,
    val results: List<T>
)
