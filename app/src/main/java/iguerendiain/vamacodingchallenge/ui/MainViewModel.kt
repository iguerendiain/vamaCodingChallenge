package iguerendiain.vamacodingchallenge.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iguerendiain.vamacodingchallenge.BuildConfig
import iguerendiain.vamacodingchallenge.domain.MainRepository
import iguerendiain.vamacodingchallenge.model.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    companion object{
        const val EVENT_DOWNLOAD_FAILED = -1
        const val EVENT_ALBUM_SELECTED = -2
    }

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    private val _eventBus = Channel<Int>()
    val eventBus = _eventBus.receiveAsFlow()

    init { refreshAlbums() }

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
            }else _eventBus.send(EVENT_DOWNLOAD_FAILED)

            withContext(Dispatchers.Main) {
                _state.value = state.value.copy(loadingState = MainState.STATE_IDLE)
            }
        }
    }
}