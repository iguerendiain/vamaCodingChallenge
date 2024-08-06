package iguerendiain.vamacodingchallenge.ui.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import iguerendiain.vamacodingchallenge.ui.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Button(onClick = {
        navController.navigate(
            Screen.AlbumDetailsScreen.route + "/JULEPE_MILAZO"
        )
    }) {
        Text(text = "HOME PAGE")
    }
}

