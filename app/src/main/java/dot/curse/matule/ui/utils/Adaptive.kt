package dot.curse.matule.ui.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

open class Adaptive {
    enum class ScreenSizeMetric {
        SMALL,
        MEDIUM,
        LARGE
    }

    data class ScreenSize (
        val screenWidth: ScreenSizeMetric,
        val screenHeight: ScreenSizeMetric
    )

    @Composable
    fun getScreenSize(): ScreenSize {
        val density = LocalDensity.current
        val configuration = LocalConfiguration.current

        val windowWidth = with(density) {
            configuration.screenWidthDp.dp
        }

        val windowHeight = with(density) {
            configuration.screenHeightDp.dp
        }

        return ScreenSize(
            screenWidth = when {
                windowWidth < 600.dp -> ScreenSizeMetric.SMALL
                windowWidth < 900.dp -> ScreenSizeMetric.MEDIUM
                else -> ScreenSizeMetric.LARGE
            },
            screenHeight = when {
                windowHeight < 850.dp -> ScreenSizeMetric.SMALL
                windowHeight < 1250.dp -> ScreenSizeMetric.MEDIUM
                else -> ScreenSizeMetric.LARGE
            }
        )
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    @Composable
    fun adaptiveElementWidthMedium(): Modifier {
        val screenSize = getScreenSize()
        return if (screenSize.screenWidth == ScreenSizeMetric.SMALL) {
            Modifier.fillMaxWidth()
        } else {
            Modifier.fillMaxWidth(0.8f)
        }
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    @Composable
    fun adaptiveElementWidthSmall(): Modifier {
        val screenSize = getScreenSize()
        return if (screenSize.screenWidth == ScreenSizeMetric.SMALL) {
            Modifier.fillMaxWidth(0.8f)
        } else {
            Modifier.fillMaxWidth(0.5f)
        }
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    @Composable
    fun adaptiveImageWidth(): Modifier {
        val screenSize = getScreenSize()
        return if (screenSize.screenWidth == ScreenSizeMetric.SMALL) {
            Modifier.fillMaxWidth()
        } else {
            Modifier.fillMaxWidth(0.65f)
        }
    }

}