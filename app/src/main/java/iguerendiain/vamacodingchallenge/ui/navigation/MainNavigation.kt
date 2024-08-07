package iguerendiain.vamacodingchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import iguerendiain.vamacodingchallenge.ui.details.AlbumDetailsScreen
import iguerendiain.vamacodingchallenge.ui.home.HomeScreen
import iguerendiain.vamacodingchallenge.ui.vm.MainViewModel

const val ARGS_ALBUM_ID = "album_id"
const val ARGS_FALLBACK_COVERWIDTH = "fallback_cover_width"

@Composable
fun MainNavigation(startDestination: String){
    val navController = rememberNavController()
    val mainViewModel = hiltViewModel<MainViewModel>()

    mainViewModel.refreshAlbums()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController, viewModel = mainViewModel)
        }

        composable(
            route = Screen.AlbumDetailsScreen.route + "/{$ARGS_ALBUM_ID}/{$ARGS_FALLBACK_COVERWIDTH}",
            arguments = listOf(
                navArgument(ARGS_ALBUM_ID) {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument(ARGS_FALLBACK_COVERWIDTH){
                    type = NavType.IntType
                    nullable = false
                }
            )
        ){
            it.arguments?.getString(ARGS_ALBUM_ID)?.let { albumId ->
                AlbumDetailsScreen(
                    navController = navController,
                    albumId = albumId,
                    fallbackCoverWidth = it.arguments?.getInt(ARGS_FALLBACK_COVERWIDTH)?:-1,
                    viewModel = mainViewModel
                )
            }
        }
    }
}