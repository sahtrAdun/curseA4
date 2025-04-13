package dot.curse.matule.ui.items.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dot.curse.matule.ui.utils.Routes
import dot.curse.matule.R

@Composable
fun MatuleHeader(
    modifier: Modifier = Modifier,
    currentRoute: String?
) {
    val context = LocalContext.current

    var show by remember { mutableStateOf(false) }
    show = when(currentRoute) {
        Routes.SplashScreenRoute.patch,
        Routes.OnBoardingRoute.patch -> false
        else -> true
    }
    var label by remember { mutableStateOf("null") }
    label = when(currentRoute) {
        Routes.SignInRoute.patch -> context.getString(R.string.header_sign_in)
        else -> ""
    }
    var placeHolderColor by remember { mutableStateOf(Color.White) }
    placeHolderColor = when(currentRoute) {
        Routes.SplashScreenRoute.patch,
        Routes.OnBoardingRoute.patch -> MaterialTheme.colorScheme.primary

        Routes.SignInRoute.patch,
        Routes.SignUpRoute.patch -> MaterialTheme.colorScheme.surface

        else -> MaterialTheme.colorScheme.background
    }

    if (show) {
        Column(modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Spacer(Modifier.height(20.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    } else {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .background(color = placeHolderColor)
        )
    }
}
