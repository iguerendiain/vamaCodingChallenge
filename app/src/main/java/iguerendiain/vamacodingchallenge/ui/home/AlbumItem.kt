package iguerendiain.vamacodingchallenge.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import coil.compose.AsyncImage
import iguerendiain.vamacodingchallenge.model.Album

@Composable
fun AlbumItem(album: Album, onClick: () -> Unit){
    var imageWidth by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .onGloballyPositioned { imageWidth = it.size.width }
            .clickable { onClick.invoke() }
    ) {
        AsyncImage(model = album.getArtworkURL(imageWidth), contentDescription = null)
        Text(text = "${album.name} -- ${album.artistName}")
    }
}