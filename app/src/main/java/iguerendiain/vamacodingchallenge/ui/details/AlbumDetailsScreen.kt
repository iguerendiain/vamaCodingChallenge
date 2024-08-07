package iguerendiain.vamacodingchallenge.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import iguerendiain.vamacodingchallenge.ui.vm.MainViewModel

@Composable
fun AlbumDetailsScreen(
    navController: NavController,
    albumId: String,
    viewModel: MainViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val album = state.albums.find { it.id == albumId }
    val copyright = state.currentCopyrightText
    val uriHandler = LocalUriHandler.current
    var imageWidth by remember { mutableIntStateOf(0) }

    if (album!=null) Column(
        modifier = Modifier.onGloballyPositioned {
            imageWidth = it.size.width
        }
    ) {
        AsyncImage(model = album.getArtworkURL(imageWidth), contentDescription = null)
        Text(text = "${album.name} -- ${album.artistName}")
        Text(text = "[ ${album.genres.joinToString(",") { it.name }} ]")
        Text(text = "Released on: ${album.releaseDate}")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Copyright: $copyright")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { uriHandler.openUri(album.url) } ) {
            Text("Open on iTunes")
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = { navController.navigateUp() }){
            Text("Go back to album list")
        }
    }
}