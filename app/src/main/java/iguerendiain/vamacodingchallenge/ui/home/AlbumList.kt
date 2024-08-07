package iguerendiain.vamacodingchallenge.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import iguerendiain.vamacodingchallenge.model.Album

@Composable
fun AlbumList(
    albums: List<Album>,
    onAlbumSelected: (album: Album) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
    ) {
        items(albums.size) {
            val album = albums[it]
            AlbumItem(album = album) { onAlbumSelected(album) }
        }
    }
}