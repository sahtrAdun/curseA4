package dot.curse.matule.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MatuleButton(
    modifier: Modifier = Modifier,
    text: String,
    active: Boolean = true,
    background: Color = MaterialTheme.colorScheme.primary,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    action: () -> Unit
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .heightIn(min = 50.dp)
        .clickable(null, null) { action() }
        .background(
            color = if (active) background else MaterialTheme.colorScheme.outline,
            shape = RoundedCornerShape(13.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (active) tint else MaterialTheme.colorScheme.outlineVariant,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(15.dp)
        )
    }
}