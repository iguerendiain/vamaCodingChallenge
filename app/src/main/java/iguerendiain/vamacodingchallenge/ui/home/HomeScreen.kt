package iguerendiain.vamacodingchallenge.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.palm.composestateevents.EventEffect
import iguerendiain.vamacodingchallenge.BuildConfig
import iguerendiain.vamacodingchallenge.R
import iguerendiain.vamacodingchallenge.ui.navigation.ARGS_FALLBACK_COVERWIDTH
import iguerendiain.vamacodingchallenge.ui.vm.MainViewModel
import iguerendiain.vamacodingchallenge.ui.navigation.Screen
import iguerendiain.vamacodingchallenge.ui.vm.MainState

@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    val mainState = viewModel.state.value
    var coverWidth by remember { mutableIntStateOf(-1) }

    EventEffect(
        event = mainState.albumSelectedEvent,
        onConsumed = viewModel::consumeSelectAlbumEvent
    ) {
        navController.navigate(
            Screen.AlbumDetailsScreen.route + "/${it.id}/$coverWidth"
        )
    }

    if (mainState.albums.isEmpty()) {
        when (mainState.loadingState) {
            MainState.STATE_IDLE -> {
                if (mainState.albumDownloadErrorEvent==null) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 24.sp,
                            lineHeight = 42.sp,
                            text = stringResource(id = R.string.home_noalbums)
                        )
                        TextButton(onClick = { viewModel.refreshAlbums() }) {
                            Text(
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 32.sp,
                                text = stringResource(id = R.string.home_noalbums_try_download)
                            )
                        }
                    }
                }
            }
            MainState.STATE_LOADING -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(120.dp))
                }
            }
        }
    } else {
        Column {
            Row(modifier = Modifier.height(56.dp)) {
                Text(
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(
                            horizontal = 20.dp,
                            vertical = 12.dp
                        )
                        .weight(1f),
                    text = stringResource(
                        id = R.string.home_title,
                        BuildConfig.ALBUM_DOWNLOAD_LIMIT,
                        BuildConfig.ALBUM_DOWNLOAD_COUNTRY
                    )
                )

                if (BuildConfig.DEBUG) {
                    IconButton(onClick = { viewModel.refreshAlbums() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(onClick = { viewModel.clearDB() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                if (mainState.loadingState == MainState.STATE_LOADING)
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(all = 4.dp),
                    )
            }
            AlbumList(
                albums = mainState.albums,
                onAlbumSelected = { album, solvedCoverWidth ->
                    coverWidth = solvedCoverWidth
                    viewModel.selectAlbum(album)
                }
            )
        }
    }

    mainState.albumDownloadErrorEvent?.let { AlbumDownloadErrorDialog(
        apiErrorInfo = it,
        onRetry = { viewModel.refreshAlbums() },
        onDismiss = { viewModel.clearDownloadError() }
    ) }
}
