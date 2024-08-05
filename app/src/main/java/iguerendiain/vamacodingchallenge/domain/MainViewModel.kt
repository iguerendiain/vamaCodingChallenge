package iguerendiain.vamacodingchallenge.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    companion object{
        const val STATE_LOADING = -1
        const val STATE_IDLE = -2
    }

    private val _currentState = MutableStateFlow(STATE_IDLE)
    val currentState = _currentState.asStateFlow()

    private val _eventBus = Channel<Int>()
    val eventBus = _eventBus.receiveAsFlow()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            _currentState.value = STATE_LOADING
            _currentState.value = STATE_IDLE
        }
    }

}