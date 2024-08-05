package iguerendiain.vamacodingchallenge.model

import java.util.Date

data class Album(
    val artistName: String,
    val id: String,
    val name: String,
    val releaseDate: Date,
    val kind: String,
    val artistId: String,
    val artistUrl: String,
    val artworkUrl100: String,
    val genres: List<Genre>,
    val url: String
)
