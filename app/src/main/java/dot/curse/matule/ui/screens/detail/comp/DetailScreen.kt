package dot.curse.matule.ui.screens.detail.comp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dot.curse.matule.R
import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.ui.items.ShimmerLoadingAnimation
import dot.curse.matule.ui.screens.detail.DetailViewModel
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Adaptive()
                .adaptiveElementWidthMedium()
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier
                .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = state.shoe.company + " " + state.shoe.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
                Text(
                    text = "₽${state.shoe.price}",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            if (state.shoe == Shoe()) {
                ShimmerLoadingAnimation(
                    modifier = Adaptive()
                        .adaptiveImageWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            } else {
                AsyncImage(
                    model = state.shoe.bigImage,
                    contentDescription = null,
                    modifier = Adaptive()
                        .adaptiveImageWidth()
                        .height(200.dp)
                )
            }
            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.list) { item ->
                    if (state.shoe == Shoe()) {
                        ShimmerLoadingAnimation(
                            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp))
                        )
                    } else {
                        MiniShoe(
                            modifier = Modifier.animateItem(),
                            shoe = item,
                            current = item == state.shoe
                        ) { viewModel.apply { navController.onItemClick(item) } }
                    }
                }
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (state.shoe == Shoe()) {
                    ShimmerLoadingAnimation(
                        modifier = Modifier.height(60.dp).fillMaxWidth().clip(RoundedCornerShape(12.dp))
                    )
                } else {
                    Text(
                        text = state.shoe.description,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = if (state.expanded) Int.MAX_VALUE else 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.animateContentSize()
                    )
                    Text(
                        text = stringResource(R.string.detail_more),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth().clickable(null, null) {
                            viewModel.onDescriptionClick()
                        }
                    )
                }

            }
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 25.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Box(modifier = Modifier
                    .size(48.dp)
                    .background(
                        color  = MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    ).clickable(null, null) {
                        if (state.favorite) viewModel.deleteFromFavorite(state.shoe) else viewModel.addToFavorite(state.shoe)
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(if (state.favorite) R.drawable.heart_fill else R.drawable.heart),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                    )
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable(null, null) {
                        if (state.inCart) viewModel.deleteFromCart(state.shoe) else viewModel.addToCart(state.shoe)
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(if (!state.inCart) R.drawable.plus else R.drawable.bar_bag),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = stringResource(if (state.inCart) R.string.b_detail_remove else R.string.b_detail_add),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.width(24.dp))
                    }
                }
            }
        }
    }
}