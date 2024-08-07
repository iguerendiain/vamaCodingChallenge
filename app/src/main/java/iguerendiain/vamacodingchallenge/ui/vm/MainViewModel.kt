package iguerendiain.vamacodingchallenge.ui.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import iguerendiain.vamacodingchallenge.BuildConfig
import iguerendiain.vamacodingchallenge.domain.MainRepository
import iguerendiain.vamacodingchallenge.storage.APIErrorInfo
import iguerendiain.vamacodingchallenge.model.Album
import iguerendiain.vamacodingchallenge.storage.APIErrorException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    init { refreshAlbums() }

    fun selectAlbum(album: Album){
        _state.value = state.value.copy(
            albumSelectedEvent = triggered(album)
        )
    }

    fun consumeSelectAlbumEvent(){
        _state.value = state.value.copy(
            albumSelectedEvent = consumed()
        )
    }

    fun consumeAlbumDownloadErrorEvent(){
        _state.value = state.value.copy(albumDownloadErrorEvent = consumed())
    }

    fun clearDB(){
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.clearLocalAlbums()
            _state.value = state.value.copy(
                loadingState = MainState.STATE_IDLE,
                albums = listOf(),
                currentCopyrightText = ""
            )
        }
    }

    fun refreshAlbums(){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                _state.value = state.value.copy(
                    loadingState = MainState.STATE_LOADING,
                    albums = mainRepository.getLocalAlbums(),
                    currentCopyrightText = mainRepository.getFeedData<Album>()?.copyright?:""
                )
            }

            val downloadAlbumsResult = mainRepository.downloadAlbums(
                Locale.getDefault().country,
                BuildConfig.ALBUM_DOWNLOAD_LIMIT
            )

            if (downloadAlbumsResult.isSuccess) {
                downloadAlbumsResult.getOrNull()?.let { downloadedFeed ->
                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(
                            loadingState = MainState.STATE_LOADING,
                            albums = downloadedFeed.results,
                            currentCopyrightText = downloadedFeed.copyright
                        )
                    }
                    mainRepository.clearLocalAlbums()
                    mainRepository.storeLocalAlbums(downloadedFeed.results)
                    mainRepository.storeFeedData(downloadedFeed)
                }
            }else{
                if (state.value.albums.isEmpty()) {
                    val apiErrorInfo = (downloadAlbumsResult.exceptionOrNull() as APIErrorException?)
                        ?.apiErrorInfo
                        ?: APIErrorInfo(type = APIErrorInfo.UNKNOWN)

                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(
                            albumDownloadErrorEvent = triggered(apiErrorInfo)
                        )
                    }
                }
            }

            withContext(Dispatchers.Main) {
                _state.value = state.value.copy(loadingState = MainState.STATE_IDLE)
            }
        }
    }
}