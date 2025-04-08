package dot.curse.matule

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dot.curse.matule.ui.theme.MatuleTheme

class Launcher : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MatuleTheme {

                splashScreen.setOnExitAnimationListener {
                    Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show()
                }

            }
        }
    }


}
