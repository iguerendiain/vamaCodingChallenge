package iguerendiain.vamacodingchallenge.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import de.palm.composestateevents.EventEffect
import iguerendiain.vamacodingchallenge.ui.vm.MainViewModel
import iguerendiain.vamacodingchallenge.ui.navigation.Screen

@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    val mainState = viewModel.state.value

    EventEffect(
        event = mainState.albumSelectedEvent,
        onConsumed = viewModel::consumeSelectAlbumEvent
    ) {
        navController.navigate(Screen.AlbumDetailsScreen.route + "/${it.id}")
    }

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

    mainState.albumDownloadErrorEvent?.let { AlbumDownloadErrorDialog(
        apiErrorInfo = it,
        onRetry = { viewModel.refreshAlbums() },
        onDismiss = { viewModel.clearDownloadError() }
    ) }
}
