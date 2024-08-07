package iguerendiain.vamacodingchallenge.ui.vm

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import iguerendiain.vamacodingchallenge.storage.APIErrorInfo
import iguerendiain.vamacodingchallenge.model.Album

data class MainState(
    val albums: List<Album> = listOf(),
    val loadingState: Int = STATE_IDLE,
    val selectedAlbum: Album? = null,
    val currentCopyrightText: String = "",

    val albumDownloadErrorEvent: StateEventWithContent<APIErrorInfo> = consumed(),
    val albumSelectedEvent: StateEventWithContent<Album> = consumed()
){
    companion object{
        const val STATE_LOADING = -1
        const val STATE_IDLE = -2
    }
}