package dot.curse.matule.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MatuleHeader(
    modifier: Modifier = Modifier,
    label: String = "Label",
    show: Boolean,
    placeholder: Color
) {
    if (show) {
        Box(modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    } else {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .background(color = placeholder)
        )
    }
}

@Composable
fun MatuleHeaderView(
    modifier: Modifier = Modifier,
    view: HeaderViewModel
) {
    val state by view.headerState.collectAsStateWithLifecycle()
    if (state.show) {
        Box(modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.label,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    } else {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .background(color = state.placeholderColor)
        )
    }
}