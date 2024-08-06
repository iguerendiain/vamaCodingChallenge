package iguerendiain.vamacodingchallenge.ui.details

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import iguerendiain.vamacodingchallenge.model.Album

@Composable
fun AlbumDetailsScreen(
    navController: NavController,
    albumId: String
){
    Button(onClick = { navController.navigateUp() }) {
        Text(text = "ALBUM DETAILS SCREEN FOR $albumId")
    }
}