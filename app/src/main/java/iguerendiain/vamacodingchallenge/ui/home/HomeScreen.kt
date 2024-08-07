package iguerendiain.vamacodingchallenge.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import iguerendiain.vamacodingchallenge.ui.MainViewModel
import iguerendiain.vamacodingchallenge.ui.navigation.Screen

@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    Column {
        Text(text = "HOME PAGE")
        AlbumList(albums = viewModel.state.value.albums, onAlbumSelected = {
            navController.navigate(
                Screen.AlbumDetailsScreen.route + "/${it.id}"
            )
        })
    }
}
