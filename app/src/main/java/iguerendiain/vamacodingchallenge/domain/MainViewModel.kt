package iguerendiain.vamacodingchallenge.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iguerendiain.vamacodingchallenge.BuildConfig
import iguerendiain.vamacodingchallenge.model.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    companion object{
        const val STATE_LOADING = -1
        const val STATE_IDLE = -2

        const val EVENT_DOWNLOAD_FAILED = -1
        const val EVENT_ALBUM_SELECTED = -2
    }

    private val _selectedAlbum = MutableStateFlow<Album?>(null)
    val selectedAlbum = _selectedAlbum.asStateFlow()

    private val _currentState = MutableStateFlow(STATE_IDLE)
    val currentState = _currentState.asStateFlow()

    private val _currentAlbums = MutableStateFlow<List<Album>>(listOf())
    val currentAlbums = _currentAlbums.asStateFlow()

    private val _eventBus = Channel<Int>()
    val eventBus = _eventBus.receiveAsFlow()

    init { refreshAlbums() }

    fun selectAlbum(album: Album){
        viewModelScope.launch {
            _selectedAlbum.value = album
            _eventBus.send(EVENT_ALBUM_SELECTED)
        }
    }

    fun refreshAlbums(){
        viewModelScope.launch(Dispatchers.IO) {
            _currentState.value = STATE_LOADING

            _currentAlbums.value = mainRepository.getLocalAlbums()

            val downloadAlbumsResult = mainRepository.downloadAlbums(
                Locale.getDefault().country,
                BuildConfig.ALBUM_DOWNLOAD_LIMIT
            )

            if (downloadAlbumsResult.isSuccess) {
                downloadAlbumsResult.getOrDefault(listOf()).let { downloadedAlbums ->
                    _currentAlbums.value = downloadedAlbums
                    mainRepository.storeAlbums(downloadedAlbums)
                }
            }else _eventBus.send(EVENT_DOWNLOAD_FAILED)

            _currentState.value = STATE_IDLE
        }
    }
}