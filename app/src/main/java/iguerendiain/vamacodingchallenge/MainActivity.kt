package iguerendiain.vamacodingchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import iguerendiain.vamacodingchallenge.domain.MainViewModel
import iguerendiain.vamacodingchallenge.ui.navigation.MainNavigation
import iguerendiain.vamacodingchallenge.ui.navigation.Screen
import iguerendiain.vamacodingchallenge.ui.theme.VamaInterviewCodingChallengeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val viewModel by viewModels<MainViewModel>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.refreshAlbums()

        setContent {
            VamaInterviewCodingChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation(startDestination = Screen.HomeScreen.route)
                }
            }
        }
    }
}
