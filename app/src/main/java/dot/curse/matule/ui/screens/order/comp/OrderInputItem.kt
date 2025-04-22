package dot.curse.matule.ui.screens.order.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun OrderInputItem(
    icon: ImageVector,
    label: String,
    editable: Boolean,
    focus: FocusRequester? = null,
    inputContent: @Composable (Boolean) -> Unit
) {
    var canEdit by remember { mutableStateOf(false) }

    LaunchedEffect(canEdit) {
        if (canEdit) focus?.requestFocus()
    }

    Row(modifier = Modifier
        .fillMaxWidth() ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(modifier = Modifier
            .size(32.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(8.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Icon(modifier = Modifier
                .size(24.dp),
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black,
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                inputContent(canEdit && editable)
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
            if (editable) {
                Icon(modifier = Modifier
                    .size(16.dp)
                    .clickable(null, null) {
                        canEdit = !canEdit
                    },
                    imageVector = if (!canEdit) Icons.Default.Create else Icons.Default.Check,
                    contentDescription = null,
                    tint = if (!canEdit) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}