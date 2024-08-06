package iguerendiain.vamacodingchallenge.ui

import iguerendiain.vamacodingchallenge.model.Album

data class MainState(
    val albums: List<Album> = listOf(),
    val selectedAlbum: Album? = null,
    val loadingAlbums: Boolean = false
)