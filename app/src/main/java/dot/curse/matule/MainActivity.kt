package dot.curse.matule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.ui.theme.MatuleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var loading by mutableStateOf(true)
        installSplashScreen().apply {
            setKeepOnScreenCondition { loading == true }
        }
        enableEdgeToEdge()
        setContent {
            MatuleTheme {

                LaunchedEffect(Unit) {
                    val shared = SharedManager(this@MainActivity)
                    val userId = shared.getSharedUserId()
                    val firstTime = shared.getSharedFirstTime()

                    if (firstTime) {
                        // TODO Переход к Onboarding
                    } else if (userId == -1) {
                        // TODO Переход к Sign
                    } else {
                        // TODO Переход к Main
                    }

                    loading = false
                }

            }
        }
    }

}
