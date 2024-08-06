package iguerendiain.vamacodingchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import iguerendiain.vamacodingchallenge.ui.details.AlbumDetailsScreen
import iguerendiain.vamacodingchallenge.ui.home.HomeScreen

const val ARGS_ALBUM_ID = "album_id"

@Composable
fun MainNavigation(startDestination: String){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.AlbumDetailsScreen.route + "/{$ARGS_ALBUM_ID}",
            arguments = listOf(
                navArgument(ARGS_ALBUM_ID) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ){
            it.arguments?.getString(ARGS_ALBUM_ID)?.let { albumId ->
                AlbumDetailsScreen(
                    navController = navController,
                    albumId = albumId
                )
            }
        }

    }
}