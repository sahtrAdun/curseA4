package dot.curse.matule.ui.screens.notification.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dot.curse.matule.domain.model.notification.Notification
import dot.curse.matule.ui.utils.MyDateSerializer.getRelativeTimeString

@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    notification: Notification
) {
    val context = LocalContext.current

    Box(modifier = modifier
        .fillMaxWidth()
        .heightIn(min = 140.dp)
        .background(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(12.dp)
        )
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = notification.label,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = notification.description,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = notification.date.getRelativeTimeString(context),
                color = MaterialTheme.colorScheme.outlineVariant,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}