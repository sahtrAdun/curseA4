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
import dot.curse.matule.ui.theme.MatuleTheme
import kotlinx.coroutines.delay

class Launcher : ComponentActivity() {
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
                    delay(1500)
                    loading = false
                }

            }
        }
    }

}
