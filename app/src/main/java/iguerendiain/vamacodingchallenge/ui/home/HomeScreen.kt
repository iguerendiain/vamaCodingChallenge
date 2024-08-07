package iguerendiain.vamacodingchallenge.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.palm.composestateevents.EventEffect
import iguerendiain.vamacodingchallenge.storage.APIErrorInfo
import iguerendiain.vamacodingchallenge.ui.vm.MainViewModel
import iguerendiain.vamacodingchallenge.ui.navigation.Screen

@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val mainState = viewModel.state.value
    var apiErrorInfo by remember { mutableStateOf<APIErrorInfo?>(null) }

    EventEffect(
        event = mainState.albumSelectedEvent,
        onConsumed = viewModel::consumeSelectAlbumEvent
    ) {
        navController.navigate(Screen.AlbumDetailsScreen.route + "/${it.id}")
    }

    EventEffect(
        event = mainState.albumDownloadErrorEvent,
        onConsumed = viewModel::consumeAlbumDownloadErrorEvent
    ) { apiErrorInfo = it }

    Column {
        Row{
            Text(text = "HOME PAGE")
            Button(onClick = { viewModel.refreshAlbums() }){
                Text(text = "FORCE REFRESH")
            }
            Button(onClick = { viewModel.clearDB() }){
                Text(text = "CLEAR DB")
            }
        }
        AlbumList(
            albums = viewModel.state.value.albums,
            onAlbumSelected = {viewModel.selectAlbum(it) }
        )
    }

    apiErrorInfo?.let { AlbumDownloadErrorDialog(
        apiErrorInfo = it,
        onRetry = { viewModel.refreshAlbums() },
        onDismiss = { apiErrorInfo = null}
    ) }
}
