package dot.curse.matule.ui.items

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import dot.curse.matule.R

@Composable
fun ShoeItem(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    onTagClick: () -> Unit,
    onHeartClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Box(modifier = modifier
        .size(160.dp, 185.dp)
        .background(color = MaterialTheme.colorScheme.surface)
        .clip(RoundedCornerShape(16.dp))
        .clickable(null, null) { onItemClick() }
    ) {
        Image(
            painter = painterResource(R.drawable.dummy_shoe),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 18.dp)
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        )
        Row(modifier = Modifier
            .fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            Box(modifier = Modifier
                .size(34.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(24.dp)
                ),
                contentAlignment = Alignment.Center
            ) {

            }
        }
        Column(modifier = Modifier
            .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier
                .size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.heart),
                    contentDescription = null
                )
            }
            Spacer(Modifier.weight(1f))

        }
    }
}