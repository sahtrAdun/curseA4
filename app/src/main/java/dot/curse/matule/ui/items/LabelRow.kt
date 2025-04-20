package dot.curse.matule.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dot.curse.matule.R

@Composable
fun LabelRow(
    modifier: Modifier = Modifier,
    label: String,
    withAll: Boolean = false,
    onAllClick: () -> Unit = {},
    background: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier
        .background(
            color = background,
            shape = RoundedCornerShape(if (background != Color.Transparent) 12.dp else 0.dp)
        )
    ) {
        Column(modifier = Modifier
            .padding(if (background != Color.Transparent) 10.dp else 0.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                if (withAll) {
                    Text(
                        text = stringResource(R.string.main_see_all),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clickable(null, null) { onAllClick() }
                    )
                }
            }
            content()
        }

    }
}