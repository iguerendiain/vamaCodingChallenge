package iguerendiain.vamacodingchallenge.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import iguerendiain.vamacodingchallenge.R
import iguerendiain.vamacodingchallenge.ui.vm.MainViewModel
import java.text.SimpleDateFormat

@Composable
fun AlbumDetailsScreen(
    navController: NavController,
    albumId: String,
    viewModel: MainViewModel
){
    val dateFormatter = SimpleDateFormat.getDateInstance()

    val state = viewModel.state.value
    val album = viewModel.state.value.albums.find { it.id == albumId }
    val copyright = state.currentCopyrightText
    val uriHandler = LocalUriHandler.current
    var imageWidth by remember { mutableIntStateOf(0) }

    if (album!=null) Column(
        modifier = Modifier.onGloballyPositioned {
            imageWidth = it.size.width
        }
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxSize()
        ){
            SubcomposeAsyncImage(
                model = album.getArtworkURL(imageWidth),
                contentDescription = null,
                loading = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ){
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Black, Color.Transparent)
                        )
                    )
            ){
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp,
                    vertical = 12.dp
                )
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                lineHeight = 26.sp,
                fontSize = 24.sp,
                text=album.name
            )
            Text(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 20.sp,
                fontSize = 18.sp,
                text=album.artistName
            )

            Row(
                modifier = Modifier.padding(top = 12.dp)
            ){
                album.genres.map {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(22.dp)
                            .padding(
                                end = 8.dp
                            )
                            .clip(RoundedCornerShape(11.dp))
                            .background(MaterialTheme.colorScheme.onBackground)
                    ){
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.inversePrimary,
                            text = it.name
                        )
                    }
                }
            }

            album.renderReleaseDate(dateFormatter)?.let { renderedDate ->
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    text = stringResource(
                        R.string.details_releasedate,
                        renderedDate
                    )
                )
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { uriHandler.openUri(album.url) }
                ) {
                    Text(stringResource(R.string.details_openitunes))
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    text = copyright
                )
            }

        }
    }
}