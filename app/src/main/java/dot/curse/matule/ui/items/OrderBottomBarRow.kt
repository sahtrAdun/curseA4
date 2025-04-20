package dot.curse.matule.ui.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderBottomBarRow(
    modifier: Modifier = Modifier,
    leftText: String,
    rightText: String,
    withPrimeColor: Boolean = false
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = leftText,
            color = if (withPrimeColor) Color.Black else Color.LightGray
        )
        Spacer(Modifier.weight(1f))
        Row {
            Text(
                text = "₽",
                color = if (withPrimeColor) MaterialTheme.colorScheme.primary else Color.Unspecified
            )
            Text(
                text = rightText,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                color = if (withPrimeColor) MaterialTheme.colorScheme.primary else Color.Unspecified
            )
        }
    }
}