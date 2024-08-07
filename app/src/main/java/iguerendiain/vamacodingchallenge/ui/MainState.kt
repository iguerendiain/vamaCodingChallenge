package iguerendiain.vamacodingchallenge.ui

import iguerendiain.vamacodingchallenge.model.Album

data class MainState(
    val albums: List<Album> = listOf(),
    val loadingState: Int = STATE_IDLE,
    val selectedAlbum: Album? = null,
    val currentCopyrightText: String = ""
){
    companion object{
        const val STATE_LOADING = -1
        const val STATE_IDLE = -2
    }
}