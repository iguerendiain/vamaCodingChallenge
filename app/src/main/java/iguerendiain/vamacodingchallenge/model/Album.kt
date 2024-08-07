package iguerendiain.vamacodingchallenge.model

import java.util.Date

data class Album(
    val artistName: String = "",
    val id: String = "",
    val name: String = "",
    val releaseDate: Date = Date(),
    val kind: String = "",
    val artistId: String = "",
    val artistUrl: String = "",
    val artworkUrl100: String = "",
    val genres: List<Genre> = listOf(),
    val url: String = "",
){

    fun getArtworkURL(width: Int) = artworkUrl100
        .replace("/100x100bb.jpg", "/${width}x${width}bb.jpg")

}
