package iguerendiain.vamacodingchallenge.ui.navigation

sealed class Screen(val route: String) {
    data object HomeScreen: Screen("home")
    data object AlbumDetailsScreen: Screen("albumDetails")
}