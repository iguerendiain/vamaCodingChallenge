package iguerendiain.vamacodingchallenge.ui

import iguerendiain.vamacodingchallenge.model.Album

sealed class MainEvents {
    data class SelectAlbum(val album: Album): MainEvents()
    data class GoToAlbumIniTunes(val album: Album): MainEvents()
}