package iguerendiain.vamacodingchallenge.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
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
            painter = painterResource(R.drawable.ic_error),
            contentDescription = null
        ) },
        title = { Text(text = "Oops!, Something went wrong") },
        text = { Column {
            Text(text = "Type: ${apiErrorInfo.type}")
            Text(text = "Server Message: ${apiErrorInfo.errorBody}")
            Text(text = "Status code: ${apiErrorInfo.statusCode}")
            Text(text = "Exception: ${apiErrorInfo.exception?.message}")
        } },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onRetry.invoke()
                onDismiss.invoke()
            }){
                Text(text = "Retry")
            }
        }
    )
}