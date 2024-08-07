package iguerendiain.vamacodingchallenge.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import iguerendiain.vamacodingchallenge.R
import iguerendiain.vamacodingchallenge.storage.APIErrorInfo

@Composable
fun AlbumDownloadErrorDialog(
    apiErrorInfo: APIErrorInfo,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
){
    AlertDialog(
        icon = { Icon(
            modifier = Modifier.size(100.dp),
            painter = painterResource(R.drawable.ic_error),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        ) },
        title = { Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.home_album_download_error_title)
        ) },
        text = { Column {
            when (apiErrorInfo.type){
                APIErrorInfo.UNKNOWN -> {
                    Text(
                        text= stringResource(id = R.string.common_apierror_unknown_title),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    apiErrorInfo.exception?.message?.let { Text(text = it) }

                    if (apiErrorInfo.errorBody?.isNotBlank() == true || apiErrorInfo.statusCode!=null){
                        Text(
                            text = stringResource(
                                R.string.home_album_download_error_serverresponse,
                                apiErrorInfo.statusCode?:-1,
                                apiErrorInfo.errorBody?:""
                            ),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                APIErrorInfo.NETWORK -> {
                    Text(
                        text= stringResource(id = R.string.common_apierror_network),
                        textAlign = TextAlign.Center
                    )
                }
                APIErrorInfo.PARSE -> {
                    Text(
                        text= stringResource(id = R.string.common_apierror_parse_title),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    apiErrorInfo.exception?.message?.let { Text(text = it) }
                }
                APIErrorInfo.EMPTY -> {
                    Text(
                        text= stringResource(id = R.string.common_apierror_empty),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.common_cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onRetry.invoke()
                onDismiss.invoke()
            }){
                Text(text = stringResource(R.string.common_retry))
            }
        }
    )
}