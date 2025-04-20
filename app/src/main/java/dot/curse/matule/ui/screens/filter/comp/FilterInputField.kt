package dot.curse.matule.ui.screens.filter.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FilterInputField(
    modifier: Modifier = Modifier,
    label: String,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )
        content()
    }
}