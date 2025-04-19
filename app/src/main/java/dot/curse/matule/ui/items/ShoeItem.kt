package dot.curse.matule.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dot.curse.matule.R
import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.ui.utils.Adaptive
import dot.curse.matule.ui.utils.AppLanguage.translateToSystemDefault

@Composable
fun ShoeItem(
    modifier: Modifier = Modifier,
    shoe: Shoe,
    inFavorite: Boolean,
    inCart: Boolean,
    onItemClick: () -> Unit,
    onTagClick: () -> Unit,
    onHeartClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Box(modifier = modifier
        .size(160.dp, 185.dp)
        .background(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
        )
        .clip(RoundedCornerShape(16.dp))
        .clickable(null, null) { onItemClick() }
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = shoe.image,
                contentDescription = null,
                modifier = Adaptive()
                    .adaptiveElementWidthMedium()
                    .padding(top = 18.dp)
                    .padding(horizontal = 20.dp)
            )
        }
        Row(modifier = Modifier
            .fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            Box(modifier = Modifier
                .size(34.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(null, null) { onCartClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(if (inCart) R.drawable.cart else R.drawable.plus),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier
                .size(24.dp)
                .clickable(null, null) { onHeartClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(if (inFavorite) R.drawable.heart_fill else R.drawable.heart),
                    contentDescription = null,
                    tint = if (inFavorite) Color.Unspecified else MaterialTheme.colorScheme.onBackground

                )
            }
            Spacer(Modifier.weight(1f))
            Column(modifier = Modifier
                .padding(end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = shoe.tag.value.translateToSystemDefault().uppercase(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${shoe.company} ${shoe.name}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable(null, null) { onTagClick() }
                )
                Text(
                    text = "₽${shoe.price}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun DummyShoeItem(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .size(160.dp, 185.dp)
        .background(
            color = MaterialTheme.colorScheme.outline,
            shape = RoundedCornerShape(16.dp)
        )
        .clip(RoundedCornerShape(16.dp))
    ) {
        ShimmerLoadingAnimation(Modifier.fillMaxSize())
    }
}

