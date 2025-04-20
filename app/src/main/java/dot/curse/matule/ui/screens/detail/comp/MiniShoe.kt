package dot.curse.matule.ui.screens.detail.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dot.curse.matule.domain.model.shoe.Shoe

@Composable
fun MiniShoe(
    modifier: Modifier = Modifier,
    shoe: Shoe,
    current: Boolean,
    onCLick: () -> Unit
) {
    Box(modifier = modifier
        .size(48.dp)
        .border(
            width = 1.dp,
            color = if (current) MaterialTheme.colorScheme.primary else Color.Transparent,
            shape = RoundedCornerShape(12.dp)
        )
        .background(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(12.dp)
        )
        .clickable(null, null) { onCLick() },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = shoe.image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize().padding(4.dp)
        )
    }
}